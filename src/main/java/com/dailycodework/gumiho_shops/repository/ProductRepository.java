package com.dailycodework.gumiho_shops.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dailycodework.gumiho_shops.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(Long categoryId);

    List<Product> findByCategoryName(String category);

    List<Product> findByBrandName(String brand);

    List<Product> findByCategoryAndBrandName(String category, String brand);

    List<Product> findByName(String name);

    List<Product> findByBrandAndName(String brand, String name);

    Long countProductsByBrandAndName(String brand, String name);

}
