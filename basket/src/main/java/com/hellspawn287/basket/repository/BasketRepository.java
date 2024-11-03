package com.hellspawn287.basket.repository;

import com.hellspawn287.basket.model.Basket;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface BasketRepository extends ElasticsearchRepository<Basket, String> {

    Optional<Basket> findByUsername(String username);

}
