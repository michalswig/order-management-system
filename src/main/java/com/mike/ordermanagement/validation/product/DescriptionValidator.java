package com.mike.ordermanagement.validation.product;

import com.mike.ordermanagement.entity.Product;
import com.mike.ordermanagement.exceptions.ProductValidationException;
import com.mike.ordermanagement.util.MessageUtil;
import org.springframework.stereotype.Component;

import static com.mike.ordermanagement.constants.ProductMessages.MAX_DESCRIPTION_LENGTH;
import static com.mike.ordermanagement.constants.ProductMessages.MIN_DESCRIPTION_LENGTH;

@Component
public class DescriptionValidator implements Validator<Product> {

    private final MessageUtil messageUtil;

    public DescriptionValidator(MessageUtil messageUtil) {
        this.messageUtil = messageUtil;
    }

    @Override
    public void validate(Product product) {
        if (product.getDescription() == null || product.getDescription().trim().isEmpty()) {
            throw new ProductValidationException(messageUtil.getMessage("product.description.empty"));
        }
        if (product.getDescription().length() < MIN_DESCRIPTION_LENGTH || product.getDescription().length() > MAX_DESCRIPTION_LENGTH) {
            throw new ProductValidationException(
                    messageUtil.getMessage("product.description.length", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH)
            );
        }
    }

}
