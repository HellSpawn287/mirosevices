package com.hellspawn287.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private UUID id;
    @NotBlank(message = "Name is blank")
    private String name;
    @NotBlank(message = "Price is blank")
    private BigDecimal price;
    @NotBlank(message = "Description is blank")
    private String description;
    @NotBlank(message = "Quantity is blank")
    @Pattern(regexp = "^[1-9]\\d*$", message = "Quantity is not a number")
    @Min(value = 1, message ="Quantity can be below 1")
    private Integer quantity;
}