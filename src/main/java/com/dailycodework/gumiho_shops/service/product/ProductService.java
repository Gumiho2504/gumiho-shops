package com.dailycodework.gumiho_shops.service.product;

import java.util.List;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.dailycodework.gumiho_shops.dto.ImageDto;
import com.dailycodework.gumiho_shops.dto.ProductDto;
import com.dailycodework.gumiho_shops.exception.ProductNotFoundException;
import com.dailycodework.gumiho_shops.model.Category;
import com.dailycodework.gumiho_shops.model.Image;
import com.dailycodework.gumiho_shops.model.Product;
import com.dailycodework.gumiho_shops.repository.CategoryRepository;
import com.dailycodework.gumiho_shops.repository.ImageRepository;
import com.dailycodework.gumiho_shops.repository.ProductRepository;
import com.dailycodework.gumiho_shops.request.product.ProductRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Override
    public Product addProduct(ProductRequest product) {
        // check if the category is found in the DB
        // if Yes , set it as the new product category
        // if No , then save it as new category
        // then set as the new product category

        Category category = Optional.ofNullable(
                categoryRepository.findByName(product.getCategory().getName()))
                .orElseGet(
                        () -> {
                            Category newCategory = new Category(product.getCategory().getName());
                            return categoryRepository.save(newCategory);
                        });
        product.setCategory(category);
        return productRepository.save(createProduct(product, category));
    }

    private Product createProduct(ProductRequest request, Category category) {

        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found!"));
    }

    @Override
    public void deleteProductById(Long id) {

        productRepository.findById(id).ifPresentOrElse(productRepository::delete, () -> {
            throw new ProductNotFoundException("Product not found!");
        });
    }

    @Override
    public Product updateProduct(ProductRequest product, Long productId) {

        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, product))
                .map(productRepository::save)
                .orElseThrow(() -> new ProductNotFoundException("Product not found!"));
    }

    private Product updateExistingProduct(Product existingProduct, ProductRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);

        return existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countProductsByBrandAndName(brand, name);
    }

    @Override
    public List<ProductDto> getConvertedProducts(List<Product> products) {
        return products.stream().map(this::convertToDto).toList();
    }

    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream().map(image -> modelMapper.map(image, ImageDto.class)).toList();
        productDto.setImages(imageDtos);
        return productDto;
    }

}