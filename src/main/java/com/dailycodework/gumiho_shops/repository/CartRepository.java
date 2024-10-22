package com.dailycodework.gumiho_shops.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dailycodework.gumiho_shops.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
