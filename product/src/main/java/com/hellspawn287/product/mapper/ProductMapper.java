package com.hellspawn287.product.mapper;

import com.hellspawn287.dtos.ProductDto;
import com.hellspawn287.product.model.Product;
import org.mapstruct.Mapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto mapProductToDto(Product product);

    Product mapProductDtoToProduct(ProductDto product);

    default Pageable toPageRequest(int page, int size) {
        return PageRequest.of(page, size);
    }
}
