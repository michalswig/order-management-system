package com.mike.ordermanagement.dto.order;

import com.mike.ordermanagement.entity.OrderStatus;

import java.time.LocalDateTime;

public class OrderGetResponse {
    Long id;
    Long customerId;
    OrderStatus status;
    LocalDateTime orderDate;
    LocalDateTime deliveryDate;
    LocalDateTime canceledDate;

    public OrderGetResponse(Long id, Long customerId, OrderStatus status, LocalDateTime orderDate, LocalDateTime deliveryDate, LocalDateTime canceledDate) {
        this.id = id;
        this.customerId = customerId;
        this.status = status;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.canceledDate = canceledDate;
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public LocalDateTime getCanceledDate() {
        return canceledDate;
    }
}
