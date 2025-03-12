package com.mike.ordermanagement.validation.product;

import com.mike.ordermanagement.entity.Product;
import com.mike.ordermanagement.exceptions.ProductValidationException;
import com.mike.ordermanagement.util.MessageUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompositeValidator implements Validator<Product> {

    private final List<Validator<Product>> validators;
    private final MessageUtil messageUtil;

    public CompositeValidator(List<Validator<Product>> validators, MessageUtil messageUtil) {
        this.validators = validators;
        this.messageUtil = messageUtil;
    }

    @Override
    public void validate(Product product) {
        if (product == null) {
            throw new ProductValidationException(messageUtil.getMessage("product.null"));
        }
        for (Validator<Product> validator : validators) {
            validator.validate(product);
        }
    }
}
