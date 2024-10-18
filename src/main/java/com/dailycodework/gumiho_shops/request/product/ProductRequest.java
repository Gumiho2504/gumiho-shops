package com.dailycodework.gumiho_shops.request.product;

import java.math.BigDecimal;

import com.dailycodework.gumiho_shops.model.Category;

import lombok.Data;

@Data
public class ProductRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
