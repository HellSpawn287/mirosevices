package com.hellspawn287.basket.clients;

import com.hellspawn287.dtos.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "product")
public interface ProductClient {

    @GetMapping("/api/v1/products/{productId}")
    ProductDto getProductById(@PathVariable("productId") UUID productId);
}
