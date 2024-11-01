package com.hellspawn287.product.service;

import com.hellspawn287.dtos.ProductDto;
import com.hellspawn287.product.repository.ProductRepository;
import com.hellspawn287.product.model.Product;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;


    public Product getById(UUID productId) {
        return repository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id %s".formatted(productId)));
    }

    public void update(ProductDto product) {
        Product productDb = getById(product.getId());
        productDb.setName(product.getName());
        productDb.setQuantity(product.getQuantity());
        productDb.setPrice(product.getPrice());
        repository.save(productDb);
    }

    public Product save(Product product){
      return repository.save(product);
    }

    public void deleteById(UUID productId) {
        repository.deleteById(productId);
    }
}
