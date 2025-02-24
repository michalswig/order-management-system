package com.mike.ordermanagement.mapper;

import com.mike.ordermanagement.dto.order.OrderGetResponse;
import com.mike.ordermanagement.entity.Order;

public class OrderConverter {

    private OrderConverter() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static OrderGetResponse toOrderGetResponse(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        return new OrderGetResponse(
                order.getId(),
                order.getCustomer().getId(),
                order.getStatus(),
                order.getOrderDate(),
                order.getCanceledDate(),
                order.getDeliveryDate()
        );
    }

}
