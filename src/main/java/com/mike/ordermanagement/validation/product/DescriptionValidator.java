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
        String description = product.getDescription();
        validateIfEmptyDescription(description);
        validateDescriptionLength(description);
    }

    private void validateDescriptionLength(String description) {
        if (description.length() < MIN_DESCRIPTION_LENGTH || description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new ProductValidationException(
                    messageUtil.getMessage("product.description.length", MIN_DESCRIPTION_LENGTH, MAX_DESCRIPTION_LENGTH)
            );
        }
    }

    private void validateIfEmptyDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new ProductValidationException(messageUtil.getMessage("product.description.empty"));
        }
    }

}
