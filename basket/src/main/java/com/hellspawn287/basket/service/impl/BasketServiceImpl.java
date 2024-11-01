package com.hellspawn287.basket.service.impl;

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


    public List<Product> getProductsByCurrentUser() {
        String username = getCurrentUserEmail()
                .orElseThrow(() -> new NotFoundException("Username does not exists"));
        return basketRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Username does not exists"))
                .getProducts();
    }

    @Override
    public void addProduct(ProductDto productDto) {
        //sprawdzić  czy product istnieje w bazie. jeśli tak to czy jest to dodanie parti ilości produktu do koszyka.
        // zapytać Endpoint Product service'u o pobranie produktu o danym id.
//                interface ProductClient Feign
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
    }

    @SneakyThrows
    @Override
    public void addProductList(List<ProductDto> productList) {
        List<Callable<Pair<ProductDto, Integer>>> callableProducts = new ArrayList<>(productList.size());
        productList.forEach(productDto -> callableProducts.add(() -> Pair.of(productClient.getProductById(productDto.getId()), productDto.getQuantity())));
        List<Future<Pair<ProductDto, Integer>>> futureList = executorService.invokeAll(callableProducts);

        //pobrać wyniki zapytań wielowątkowych
        List<Pair<ProductDto, Integer>> productsFromDb = futureList.stream().map(productDtoFuture -> {
            try {
                return productDtoFuture.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        Map<UUID, Pair<ProductDto, Integer>> productMapFromDb = productsFromDb.stream()
                .collect(Collectors.toMap(pair -> pair.getLeft().getId(),
                        Function.identity()));

        Map<UUID, Product> productMapFromBasket = getProductsFromBasketOfCurrentUser().stream()
                .collect(Collectors.toMap(product -> product.getId(), Function.identity()));

        List<Product> collected = productMapFromBasket.entrySet().stream()
                .map(entry -> {
                    Pair<ProductDto, Integer> pairProductDto = productMapFromDb.remove(entry.getKey());

                    if (pairProductDto == null) {
                        return entry.getValue();
                    }
                    calculateQuantityForBasket(productMapFromBasket.get(pairProductDto.getLeft().getId())
                            .getQuantity(), entry.getValue().getQuantity(), entry.getValue());

                    return entry.getValue();
                }).collect(Collectors.toList());

        productMapFromDb.forEach((uuid, pair) -> collected
                .add(productMapper.mapProductDtoProduct(pair.getLeft())));

    }

    private List<Product> getProductsFromBasketOfCurrentUser() {
        String username = getCurrentUserEmail()
                .orElseThrow(() -> new NotFoundException("Username does not exists"));
        Optional<Basket> optionalBasket = basketRepository.findByUsername(username);

        Basket basket = optionalBasket.orElseGet(() -> new Basket(null, username, new ArrayList<>()));

        return basket.getProducts();
    }

    private Optional<Basket> getBasketOfCurrentUser() {
        String username = getCurrentUserEmail()
                .orElseThrow(() -> new NotFoundException("Username does not exists"));
        return basketRepository.findByUsername(username);
    }

    private static Integer calculateQuantityForEmptyBasket(ProductDto productDto, ProductDto productFromDb) {
        return productFromDb.getQuantity() < productDto.getQuantity() ? productFromDb.getQuantity() : productDto.getQuantity();
    }

    private static void calculateQuantityForBasket(Integer warehouseProductQuantity, Integer productDtoQuantity, Product basketProductQuantity) {
        int quantitySum = basketProductQuantity.getQuantity() + productDtoQuantity;
        if (warehouseProductQuantity < quantitySum) {
            basketProductQuantity.setQuantity(warehouseProductQuantity);
        } else {
            basketProductQuantity.setQuantity(quantitySum);
        }
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
}
