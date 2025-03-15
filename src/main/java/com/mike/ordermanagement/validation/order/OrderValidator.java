package com.mike.ordermanagement.validation.order;

import com.mike.ordermanagement.dto.order.OrderCreateRequest;
import com.mike.ordermanagement.exceptions.NoOrdersFoundException;
import com.mike.ordermanagement.util.MessageUtil;
import com.mike.ordermanagement.validation.Validator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderValidator implements Validator<OrderCreateRequest> {
    private final List<Validator<OrderCreateRequest>> validators;
    private final MessageUtil messageUtil;

    public OrderValidator(List<Validator<OrderCreateRequest>> validators, MessageUtil messageUtil) {
        this.validators = validators;
        this.messageUtil = messageUtil;
    }

    @Override
    public void validate(OrderCreateRequest orderCreateRequest) {
        if(orderCreateRequest == null){
            throw new NoOrdersFoundException(messageUtil.getMessage("order.null"));
        }
        for (Validator<OrderCreateRequest> validator : validators) {
            validator.validate(orderCreateRequest);
        }

    }
}
