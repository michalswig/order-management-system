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
        BigDecimal price = product.getPrice();
        validateIfPriceNull(price);
        validateIfPriceIsNegative(price);
    }

    private void validateIfPriceIsNegative(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ProductValidationException(messageUtil.getMessage("product.price.negative"));
        }
    }

    private void validateIfPriceNull(BigDecimal price) {
        if (price == null) {
            throw new ProductValidationException(messageUtil.getMessage("product.price.null"));
        }
    }

}
