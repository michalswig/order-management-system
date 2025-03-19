package com.mike.ordermanagement.service;

import com.mike.ordermanagement.entity.Product;
import com.mike.ordermanagement.exceptions.ProductNotFoundException;
import com.mike.ordermanagement.repository.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + " not found."));
    }

}
