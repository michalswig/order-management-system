package com.mike.ordermanagement.dto.order;

import com.mike.ordermanagement.entity.Customer;
import com.mike.ordermanagement.entity.OrderStatus;

import java.time.LocalDateTime;

public class OrderGetResponse {
    Long id;
    Customer customer;
    OrderStatus status;
    LocalDateTime orderDate;
    LocalDateTime deliveryDate;
    LocalDateTime canceledDate;

    public OrderGetResponse(Long id, Customer customer, OrderStatus status, LocalDateTime orderDate, LocalDateTime deliveryDate, LocalDateTime canceledDate) {
        this.id = id;
        this.customer = customer;
        this.status = status;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.canceledDate = canceledDate;
    }

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
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
