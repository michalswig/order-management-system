package com.mike.ordermanagement.dto.order;

import com.mike.ordermanagement.entity.OrderStatus;

import java.time.LocalDateTime;

public class OrderFilter {
    private Long customerId;
    private OrderStatus status;
    private LocalDateTime orderDateFrom;
    private LocalDateTime orderDateTo;
    private LocalDateTime deliveryDateFrom;
    private LocalDateTime deliveryDateTo;
    private LocalDateTime canceledDateFrom;
    private LocalDateTime canceledDateTo;

    public OrderFilter() {
    }

    public OrderFilter(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDateFrom() {
        return orderDateFrom;
    }

    public LocalDateTime getOrderDateTo() {
        return orderDateTo;
    }

    public LocalDateTime getDeliveryDateFrom() {
        return deliveryDateFrom;
    }

    public LocalDateTime getDeliveryDateTo() {
        return deliveryDateTo;
    }

    public LocalDateTime getCanceledDateFrom() {
        return canceledDateFrom;
    }

    public LocalDateTime getCanceledDateTo() {
        return canceledDateTo;
    }
}
