package com.mike.ordermanagement.validation.product;

import com.mike.ordermanagement.entity.Product;

import java.math.BigDecimal;

public class ProductValidation {

    private static final int MIN_NAME_LENGTH = 3;
    private static final int MAX_NAME_LENGTH = 50;
    private static final int MIN_DESCRIPTION_LENGTH = 3;
    private static final int MAX_DESCRIPTION_LENGTH = 255;
    private static final String NAME_PATTERN = "^[a-zA-Z0-9\\s]+$";

    public static void validate(Product product) {
        if(product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        validateName(product.getName());
        validateDescription(product.getDescription());
        validatePrice(product.getPrice());
    }

    private static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("Product name length must be between " + MIN_NAME_LENGTH + " and " + MAX_NAME_LENGTH);
        }
        if(!name.matches(NAME_PATTERN)) {
            throw new IllegalArgumentException("Product name can only contains letters numbers and spaces");
        }
    }
    private static void validateDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Product description cannot be null or empty");
        }
        if (description.length() < MIN_DESCRIPTION_LENGTH || description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException("Product description must be between " + MIN_DESCRIPTION_LENGTH + " and " + MAX_DESCRIPTION_LENGTH + " characters");
        }
    }

    private static void validatePrice(BigDecimal price) {
        if (price == null) {
            throw new IllegalArgumentException("Product price cannot be null");
        }
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Product price must be greater than zero");
        }
    }

}
