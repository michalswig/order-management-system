package com.mike.ordermanagement.factory;

import com.mike.ordermanagement.dto.order.OrderCreateRequest;
import com.mike.ordermanagement.entity.*;
import com.mike.ordermanagement.service.CustomerService;
import com.mike.ordermanagement.service.ProductService;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;

@Component
public class OrderFactory {
    private final Clock clock;
    private final ProductService productService;
    private final CustomerService customerService;

    public OrderFactory(Clock clock, ProductService productService, CustomerService customerService) {
        this.clock = clock;
        this.productService = productService;
        this.customerService = customerService;
    }

    public Order buildOrderEntity(OrderCreateRequest request) {
        Product product = productService.getProduct(request.getProductId());
        Customer customer = customerService.getCustomerById(request.getCustomerId());

        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDateTime.now(clock));

        OrderProduct orderProduct = new OrderProduct(order, product, request.getQuantity());
        order.addOrderProduct(orderProduct);
        product.addOrderProduct(orderProduct);

        return order;
    }
}
