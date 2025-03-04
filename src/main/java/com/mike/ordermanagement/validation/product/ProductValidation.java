package com.mike.ordermanagement.validation.product;

import com.mike.ordermanagement.entity.Product;
import com.mike.ordermanagement.exceptions.ProductValidationException;
import com.mike.ordermanagement.util.MessageUtil;

import java.math.BigDecimal;

public class ProductValidation {
    private static final int MIN_NAME_LENGTH = 3;
    private static final int MAX_NAME_LENGTH = 50;
    private static final int MIN_DESCRIPTION_LENGTH = 10;
    private static final int MAX_DESCRIPTION_LENGTH = 255;
    private static final String NAME_PATTERN = "^[a-zA-Z0-9\\s]+$";

    private final MessageUtil messageUtil;

    public ProductValidation(MessageUtil messageUtil) {
        this.messageUtil = messageUtil;
    }

    public void validate(Product product) {
        if (product == null) {
            throw new ProductValidationException(messageUtil.getMessage("product.null"));
        }
        validateName(product.getName());
        validateDescription(product.getDescription());
        validatePrice(product.getPrice());
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ProductValidationException(messageUtil.getMessage("product.name.empty"));
        }
        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
            throw new ProductValidationException(
                    messageUtil.getMessage("product.name.length", MIN_NAME_LENGTH, MAX_NAME_LENGTH));
        }
        if (!name.matches(NAME_PATTERN)) {
            throw new ProductValidationException(messageUtil.getMessage("product.name.invalid"));
        }
    }

    private void validateDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new ProductValidationException(messageUtil.getMessage("product.description.empty"));
        }
        if (description.length() < MIN_DESCRIPTION_LENGTH || description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new ProductValidationException(
                    messageUtil.getMessage("product.description.length", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH)
            );
        }
    }

    private void validatePrice(BigDecimal price) {
        if (price == null) {
            throw new ProductValidationException(messageUtil.getMessage("product.price.null"));
        }
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ProductValidationException(messageUtil.getMessage("product.price.negative"));
        }
    }
}
