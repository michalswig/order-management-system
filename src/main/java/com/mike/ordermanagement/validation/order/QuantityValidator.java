package com.mike.ordermanagement.validation.order;

import com.mike.ordermanagement.dto.order.OrderCreateRequest;
import com.mike.ordermanagement.exceptions.ProductValidationException;
import com.mike.ordermanagement.util.MessageUtil;
import com.mike.ordermanagement.validation.Validator;
import org.springframework.stereotype.Component;

@Component
public class QuantityValidator implements Validator<OrderCreateRequest> {
    private final MessageUtil messageUtil;

    public QuantityValidator(MessageUtil messageUtil) {
        this.messageUtil = messageUtil;
    }

    @Override
    public void validate(OrderCreateRequest product) {
        validateIfQuantityNegative(product);
    }

    private void validateIfQuantityNegative(OrderCreateRequest product) {
        if (product.getQuantity() <= 0) {
            throw new ProductValidationException(messageUtil.getMessage("order.quantity.negative"));
        }
    }
}
