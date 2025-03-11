package com.mike.ordermanagement.mapper;

import com.mike.ordermanagement.dto.order.OrderResponse;
import com.mike.ordermanagement.entity.Order;

public class OrderConverter {

    private OrderConverter() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static OrderResponse toOrderGetResponse(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        return new OrderResponse(
                order.getId(),
                order.getCustomer().getId(),
                order.getStatus(),
                order.getOrderDate(),
                order.getCanceledDate(),
                order.getDeliveryDate()
        );
    }

}
