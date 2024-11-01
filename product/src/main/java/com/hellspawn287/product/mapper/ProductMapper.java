package com.hellspawn287.product.mapper;

import com.hellspawn287.dtos.ProductDto;
import com.hellspawn287.product.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto mapProductToDto(Product product);
    Product mapProductDtoToProduct(ProductDto product);

}
