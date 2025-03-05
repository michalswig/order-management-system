package com.mike.ordermanagement.validation.product;

import com.mike.ordermanagement.entity.Product;
import com.mike.ordermanagement.exceptions.ProductValidationException;
import com.mike.ordermanagement.util.MessageUtil;
import org.springframework.stereotype.Component;

import static com.mike.ordermanagement.constants.ProductMessages.*;

@Component
public class NameValidator implements Validator<Product> {

    private final MessageUtil messageUtil;

    public NameValidator(MessageUtil messageUtil) {
        this.messageUtil = messageUtil;
    }

    @Override
    public void validate(Product product) {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new ProductValidationException(messageUtil.getMessage("product.name.empty"));
        }
        if (product.getName().length() < MIN_NAME_LENGTH || product.getName().length() > MAX_NAME_LENGTH) {
            throw new ProductValidationException(
                    messageUtil.getMessage("product.name.length", MIN_NAME_LENGTH, MAX_NAME_LENGTH));
        }
        if (!product.getName().matches(NAME_PATTERN)) {
            throw new ProductValidationException(messageUtil.getMessage("product.name.invalid"));
        }
    }
}
