package com.dailycodework.gumiho_shops.service.product;

import java.util.List;

import com.dailycodework.gumiho_shops.model.Product;
import com.dailycodework.gumiho_shops.request.product.ProductRequest;

public interface IProductService {

    Product addProduct(ProductRequest product);

    Product getProductById(Long id);

    void deleteProductById(Long id);

    Product updateProduct(ProductRequest product, Long productId);

    List<Product> getAllProducts();

    List<Product> getProductsByCategory(String category);

    List<Product> getProductsByBrand(String brand);

    List<Product> getProductsByCategoryAndBrand(String category, String brand);

    List<Product> getProductsByName(String name);

    List<Product> getProductsByBrandAndName(String brand, String name);

    Long countProductsByBrandAndName(String brand, String name);

}
