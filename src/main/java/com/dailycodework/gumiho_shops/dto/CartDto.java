package com.dailycodework.gumiho_shops.dto;

import java.math.BigDecimal;
import java.util.Set;

import lombok.Data;

@Data
public class CartDto {
    private Long id;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private Set<CartItemDto> items;
}
