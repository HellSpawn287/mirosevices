package com.hellspawn287.product.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "products",  schema = "products")
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor()
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID", columnDefinition = "UUID")
    private UUID id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "PRICE")
    private BigDecimal price;
    @Column(name = "QUANTITY")
    private Integer quantity;
    @Column(name = "CREATION_DATE")
    private LocalDateTime creationDate;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    @LastModifiedBy
    private String lastModifiedBy;
}
