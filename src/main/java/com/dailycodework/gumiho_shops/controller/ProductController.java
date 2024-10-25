package com.dailycodework.gumiho_shops.controller;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dailycodework.gumiho_shops.dto.ProductDto;
import com.dailycodework.gumiho_shops.exception.AlreadyExistingException;
import com.dailycodework.gumiho_shops.exception.ResourceNotFoundException;
import com.dailycodework.gumiho_shops.model.Product;
import com.dailycodework.gumiho_shops.request.product.ProductRequest;
import com.dailycodework.gumiho_shops.response.ApiResponse;
import com.dailycodework.gumiho_shops.service.product.IProductService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class ProductController {

    private final IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProduct() {
        List<Product> products = productService.getAllProducts();
        List<ProductDto> productDtos = productService.getConvertedProducts(products);
        return ResponseEntity.ok(new ApiResponse("success", productDtos));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            ProductDto productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("success", productDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductRequest product) {
        System.out.println("add");
        try {
            Product addProduct = productService.addProduct(product);
            ProductDto productDto = productService.convertToDto(addProduct);
            return ResponseEntity.ok(new ApiResponse("add product success!", productDto));
        } catch (AlreadyExistingException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/product/{id}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductRequest request, @PathVariable Long id) {
        try {
            Product product = productService.updateProduct(request, id);
            ProductDto productDto = productService.convertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Update product success!", productDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/product/{id}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProductById(id);
            return ResponseEntity.ok(new ApiResponse("Delete product success!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product/by/brand-{brand}/name-{name}")
    public ResponseEntity<ApiResponse> getProductsByBrandAndName(@PathVariable String brand,
            @PathVariable String name) {

        try {
            List<Product> products = productService.getProductsByBrandAndName(brand, name);

            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found!", null));
            }
            List<ProductDto> productDtos = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("success", productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("error", null));
        }

    }

    @GetMapping("/product/by/category-{category}/name-{name}")
    public ResponseEntity<ApiResponse> getProductsByCategoryAndName(@PathVariable String category,
            @PathVariable String name) {
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(category, name);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found!", null));
            }
            List<ProductDto> productDtos = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("success", productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("error", null));
        }
    }

    @GetMapping("/product/by/name-{name}")
    public ResponseEntity<ApiResponse> getProductsByName(@PathVariable String name) {
        try {
            List<Product> products = productService.getProductsByName(name);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found!", null));
            }
            List<ProductDto> productDtos = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("success", productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("error", null));
        }
    }

    @GetMapping("product/by/brand-{brand}")
    public ResponseEntity<ApiResponse> getProductsByBrand(@PathVariable String brand) {
        try {
            List<Product> products = productService.getProductsByBrand(brand);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found!", null));
            }
            List<ProductDto> productDtos = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("success", productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("error", null));
        }
    }

    @GetMapping("product/by/category-{category}")
    public ResponseEntity<ApiResponse> getProductsByCategory(@PathVariable String category) {
        try {
            List<Product> products = productService.getProductsByCategory(category);
            if (products.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found!", null));
            }
            List<ProductDto> productDtos = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("success", productDtos));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
