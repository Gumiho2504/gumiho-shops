package com.dailycodework.gumiho_shops.service.cart;

import com.dailycodework.gumiho_shops.model.Cart;
import java.math.BigDecimal;

public interface ICartService {
    Cart getCartById(Long id);

    void cleanCart(Long id);

    BigDecimal getTotalPrice(Long id);

    Long initializeNewCart();
}
