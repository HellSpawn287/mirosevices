package com.hellspawn287.basket.mapper;

import com.hellspawn287.basket.model.Product;
import com.hellspawn287.dtos.ProductDto;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    List<ProductDto> mapProductListToProductDtoList(List<Product> productList);

    Product mapProductDtoToProduct(ProductDto dto);

    ProductDto mapProductToProductDto(Product product);
}