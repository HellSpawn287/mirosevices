package com.hellspawn287.basket.service;

import com.hellspawn287.basket.model.Product;
import com.hellspawn287.dtos.ProductDto;

import java.util.List;
import java.util.UUID;

public interface BasketService {

    List<Product> getProductsByCurrentUser();
    void addProduct(ProductDto productDto);
    void cleanBasket();

    void removeProduct(UUID id);

    void addProductList(List<ProductDto> productDto);
}
