package com.mike.ordermanagement.validation.product;

import com.mike.ordermanagement.entity.Product;
import com.mike.ordermanagement.exceptions.ProductValidationException;
import com.mike.ordermanagement.util.MessageUtil;
import com.mike.ordermanagement.validation.Validator;
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
        String name = product.getName();
        validateIfNameEmpty(name);
        validateNameLength(name);
        validateIfMatchesPattern(name);
    }

    private void validateIfMatchesPattern(String name) {
        if (!name.matches(NAME_PATTERN)) {
            throw new ProductValidationException(messageUtil.getMessage("product.name.invalid"));
        }
    }

    private void validateNameLength(String name) {
        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
            throw new ProductValidationException(
                    messageUtil.getMessage("product.name.length", MIN_NAME_LENGTH, MAX_NAME_LENGTH));
        }
    }

    private void validateIfNameEmpty(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ProductValidationException(messageUtil.getMessage("product.name.empty"));
        }
    }

}
