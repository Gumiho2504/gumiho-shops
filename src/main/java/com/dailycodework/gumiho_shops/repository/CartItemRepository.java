package com.dailycodework.gumiho_shops.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dailycodework.gumiho_shops.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    void deleteAllByCartId(Long id);

}
