package com.dailycodework.gumiho_shops.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class OrderItemDto {

    private Long id;
    private String productName;
    private int quantity;
    private BigDecimal price;

}
