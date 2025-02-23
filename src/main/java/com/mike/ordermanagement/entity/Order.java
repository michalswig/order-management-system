package com.mike.ordermanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_date")
    private LocalDateTime orderDate;
    @Column(name = "canceled_date")
    private LocalDateTime canceledDate;
    @Column(name = "delivered_date")
    private LocalDateTime deliveryDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    public void addOrderProduct(OrderProduct orderProduct) {
        orderProducts.add(orderProduct);
        orderProduct.setOrder(this);
    }

    public Order() {
    }

    public Order(Long id, Customer customer, OrderStatus status, LocalDateTime orderDate, LocalDateTime canceledDate, LocalDateTime deliveryDate) {
        this.id = id;
        this.customer = customer;
        this.status = status;
        this.orderDate = orderDate;
        this.canceledDate = canceledDate;
        this.deliveryDate = deliveryDate;
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

    public LocalDateTime getCanceledDate() {
        return canceledDate;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void setCanceledDate(LocalDateTime canceledDate) {
        this.canceledDate = canceledDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(getId(), order.getId()) && Objects.equals(getCustomer(), order.getCustomer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCustomer());
    }


}
