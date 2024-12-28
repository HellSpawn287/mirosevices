package com.hellspawn287.basket.service.impl;

import com.hellspawn287.avro.ProductBasket;
import com.hellspawn287.basket.clients.ProductClient;
import com.hellspawn287.basket.mapper.ProductMapper;
import com.hellspawn287.basket.model.Basket;
import com.hellspawn287.basket.model.Product;
import com.hellspawn287.basket.repository.BasketRepository;
import com.hellspawn287.basket.service.BasketService;
import com.hellspawn287.dtos.ProductDto;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.hellspawn287.basket.utils.SecurityUtils.getCurrentUserEmail;

@RequiredArgsConstructor
@Service
public class BasketServiceImpl implements BasketService {

    private final ProductClient productClient;
    private final BasketRepository basketRepository;
    private final ExecutorService executorService;
    private final ProductMapper productMapper;
    private final KafkaTemplate<String, ProductBasket> kafkaTemplate;

    public List<Product> getProductsByCurrentUser() {
        String username = getCurrentUserEmail()
                .orElseThrow(() -> new NotFoundException("Username does not exists"));
        return basketRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Username does not exists"))
                .getProducts();
    }

    @Override
    public void addProduct(ProductDto productDto) {
        ProductDto productFromDb = productClient.getProductById(productDto.getId());
        String username = getCurrentUserEmail()
                .orElseThrow(() -> new NotFoundException("Username does not exists"));
        Optional<Basket> optionalBasket = basketRepository.findByUsername(username);

        Basket basket = optionalBasket.orElseGet(() -> new Basket(null, username, new ArrayList<>()));
        List<Product> products = basket.getProducts();

        products.stream()
                .filter(product -> product.getId().equals(productFromDb.getId()))
                .findFirst()
                .ifPresentOrElse(product -> calculateQuantityForBasket(productFromDb.getQuantity(), productDto.getQuantity(), product),
                        () -> products.add(Product.builder()
                                .id(productDto.getId())
                                .name(productFromDb.getName())
                                .price(productFromDb.getPrice())
                                .quantity(calculateQuantityForEmptyBasket(productDto, productFromDb))
                                .build())
                );

        basketRepository.save(basket);

        ProductBasket productBasket = getProductBasketBasedOnProductDto(productDto, productFromDb, username);

        kafkaTemplate.send("products-basket", productBasket);
    }

    private static ProductBasket getProductBasketBasedOnProductDto(ProductDto productDto, ProductDto productFromDb, String username) {
        return ProductBasket.newBuilder()
                .setProductQuantity(calculateQuantityForEmptyBasket(productDto, productFromDb))
                .setOwner(username)
                .setProductID(productFromDb.getId().toString())
                .build();
    }

    @SneakyThrows
    @Override
    public void addProductList(List<ProductDto> productList) {
        List<Callable<Pair<ProductDto, Integer>>> callableProducts = new ArrayList<>(productList.size());
        productList.forEach(productDto -> callableProducts
                .add(() -> Pair
                        .of(productClient.getProductById(productDto.getId()), productDto.getQuantity())));
        List<Future<Pair<ProductDto, Integer>>> futureList = executorService.invokeAll(callableProducts);

        //pobranie wyników zapytań wielowątkowych
        List<Pair<ProductDto, Integer>> pairOfproductDbAndAddedQuantity = futureList.stream().map(productDtoFuture -> {
            try {
                return productDtoFuture.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        Map<UUID, Pair<ProductDto, Integer>> pairByUUIDMap = pairOfproductDbAndAddedQuantity.stream()
                .collect(Collectors.toMap(pair -> pair.getLeft().getId(),
                        Function.identity()));

        Basket basket = getProductsFromBasketOfCurrentUser();
        Map<UUID, Product> productMapFromBasket = basket.getProducts().stream()
                .collect(Collectors.toMap(product -> product.getId(), Function.identity()));

        List<Product> collected = productMapFromBasket.entrySet().stream()
                .map(entryFromBasket -> {
                    Pair<ProductDto, Integer> pairProductDbAndAddedQuantity = pairByUUIDMap.remove(entryFromBasket.getKey());

                    if (pairProductDbAndAddedQuantity == null) {
//                        TODO: Add logging
                        return entryFromBasket.getValue();
                    }

                    Integer quantityFromWarehouse = pairProductDbAndAddedQuantity.getLeft().getQuantity();
                    Integer quantityOfAddedProduct = pairProductDbAndAddedQuantity.getRight();
                    calculateQuantityForBasket(quantityFromWarehouse, quantityOfAddedProduct, entryFromBasket.getValue());

                    return entryFromBasket.getValue();
                }).collect(Collectors.toList());

        pairByUUIDMap.forEach((uuid, pair) -> collected
                .add(productMapper.mapProductDtoToProduct(pair.getLeft())));

        basket.setProducts(collected);

        basketRepository.save(basket);

        sendToKafkaPerProductDto(productList, productMapFromBasket);

    }

    @Override
    public void cleanBasket() {
        String username = getCurrentUserEmail()
                .orElseThrow(() -> new NotFoundException("Username does not exists"));
        Optional<Basket> optionalBasket = basketRepository.findByUsername(username);

        optionalBasket.ifPresent(basket -> {
            basketRepository.deleteById(basket.getId());
        });
    }

    @Override
    public void removeProduct(UUID productId) {
        String username = getCurrentUserEmail()
                .orElseThrow(() -> new NotFoundException("Username does not exists"));
        Optional<Basket> optionalBasket = basketRepository.findByUsername(username);
        optionalBasket.ifPresent(basket -> {
            basket.getProducts().removeIf(product -> product.getId().equals(productId));
            basketRepository.save(basket);
        });
    }

    private void sendToKafkaPerProductDto(List<ProductDto> productList, Map<UUID, Product> productMapFromBasket) {
        productList.forEach(productDto -> mapProductDtoToProductBasketAndSendToKafka(productDto, productMapFromBasket));
    }

    private void mapProductDtoToProductBasketAndSendToKafka(ProductDto productDto, Map<UUID, Product> productMapFromBasket) {
        Product product = productMapFromBasket.get(productDto.getId());
        String username = getCurrentUserEmail()
                .orElseThrow(() -> new NotFoundException("Username does not exists"));
        ProductBasket productBasket = getProductBasketBasedOnProductDto(productDto, productMapper.mapProductToProductDto(product), username);
        kafkaTemplate.send("products-basket", productBasket);
    }

    private Basket getProductsFromBasketOfCurrentUser() {
        String username = getCurrentUserEmail()
                .orElseThrow(() -> new NotFoundException("Username does not exists"));
        Optional<Basket> optionalBasket = basketRepository.findByUsername(username);

        Basket basket = optionalBasket.orElseGet(() -> new Basket(null, username, new ArrayList<>()));

        return basket;
    }

    private Optional<Basket> getBasketOfCurrentUser() {
        String username = getCurrentUserEmail()
                .orElseThrow(() -> new NotFoundException("Username does not exists"));
        return basketRepository.findByUsername(username);
    }

    private static Integer calculateQuantityForEmptyBasket(ProductDto productDto, ProductDto productFromDb) {
        return productFromDb.getQuantity() < productDto.getQuantity() ? productFromDb.getQuantity() : productDto.getQuantity();
    }

    private static void calculateQuantityForBasket(Integer quantityInWarehouse, Integer quantityOfAddedProduct, Product productFromBasket) {
        int quantitySum = productFromBasket.getQuantity() + quantityOfAddedProduct;
        if (quantityInWarehouse < quantitySum) {
            productFromBasket.setQuantity(quantityInWarehouse);
        } else {
            productFromBasket.setQuantity(quantitySum);
        }
    }
}
