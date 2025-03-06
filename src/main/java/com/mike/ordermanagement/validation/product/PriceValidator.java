package com.mike.ordermanagement.validation.product;

import com.mike.ordermanagement.entity.Product;
import com.mike.ordermanagement.exceptions.ProductValidationException;
import com.mike.ordermanagement.util.MessageUtil;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PriceValidator implements Validator<Product> {

    private final MessageUtil messageUtil;

    public PriceValidator(MessageUtil messageUtil) {
        this.messageUtil = messageUtil;
    }

    @Override
    public void validate(Product product) {
        if (product.getPrice() == null) {
            throw new ProductValidationException(messageUtil.getMessage("product.price.null"));
        }
        if (product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ProductValidationException(messageUtil.getMessage("product.price.negative"));
        }
    }

}
