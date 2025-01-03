package com.hellspawn287.product.controller;


import com.hellspawn287.dtos.ProductDto;
import com.hellspawn287.product.mapper.ProductMapper;
import com.hellspawn287.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping("/{productId}")
    public ProductDto getProductById(@PathVariable UUID productId) {
        return productMapper.mapProductToDto(productService.getById(productId));
    }

    // TODO: changeProductQuantityById

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@RequestBody ProductDto product) {
        productService.update(product);
    }

    @PostMapping()
    public ProductDto save(@RequestBody ProductDto product) {
        return productMapper.mapProductToDto(productService.save(productMapper.mapProductDtoToProduct(product)));
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable UUID productId) {
        productService.deleteById(productId);
    }

    @GetMapping
    public Page<ProductDto> getAllProductsInWarehouse(@RequestParam Integer page, @RequestParam Integer size) {
        return productService.getPageProducts(mapToPageRequest(page, size))
                .map(productMapper::mapProductToDto);
    }

    private Pageable mapToPageRequest(Integer page, Integer size) {
        final int pageSize = ObjectUtils.min(ObjectUtils.firstNonNull(size, 100), 1000);
        final int pageNumber = ObjectUtils.min(ObjectUtils.firstNonNull(page, 100), 1000);

        return productMapper.toPageRequest(pageNumber, pageSize);
    }

}
