package com.hellspawn287.basket.controller;

import com.hellspawn287.basket.mapper.ProductMapper;
import com.hellspawn287.basket.service.BasketService;
import com.hellspawn287.dtos.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/baskets")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;
    private final ProductMapper productMapper;

    @PostMapping()
    public void addProduct(@RequestBody ProductDto productDto) {
        basketService.addProduct(productDto);
    }

    public void addProductList(@RequestBody List<ProductDto> productList) {
        basketService.addProductList(productList);
    }

    @GetMapping
    public List<ProductDto> getBasketProductsByCurrentsUser() {

        return productMapper.mapProductListToProductDtoList(basketService.getProductsByCurrentUser());
    }

    @DeleteMapping
    public void cleanBasket() {
        basketService.cleanBasket();
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeProduct(@PathVariable("productId") UUID productId) {
        basketService.removeProduct(productId);
    }
}
