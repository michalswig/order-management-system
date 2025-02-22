package com.mike.ordermanagement.dto.order;

public class OrderCreateRequest {
    Long customerId;
    Long productId;
    Long quantity;

    public OrderCreateRequest(Long customerId, Long productId, Long quantity) {
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getQuantity() {
        return quantity;
    }
}
