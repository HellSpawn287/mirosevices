package com.hellspawn287.basket.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Data
@Document(indexName = "basket")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Basket {
    private String id;
    private String username;
    private List<Product> products;
}
