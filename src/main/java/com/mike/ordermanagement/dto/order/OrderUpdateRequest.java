package com.mike.ordermanagement.dto.order;

public class OrderUpdateRequest {

    private Long id;
    private Long customerId;
    private String status;
    private String orderDate;
    private String deliveryDate;
    private String canceledDate;

    public OrderUpdateRequest(Long id, Long customerId, String status, String orderDate, String deliveryDate, String canceledDate) {
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

    public String getStatus() {
        return status;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getCanceledDate() {
        return canceledDate;
    }

}

