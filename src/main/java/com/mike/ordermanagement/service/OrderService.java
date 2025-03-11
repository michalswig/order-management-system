package com.mike.ordermanagement.service;

import com.mike.ordermanagement.dto.order.OrderFilter;
import com.mike.ordermanagement.dto.order.OrderResponse;
import com.mike.ordermanagement.entity.*;
import com.mike.ordermanagement.exceptions.NoOrdersFoundException;
import com.mike.ordermanagement.mapper.OrderConverter;
import com.mike.ordermanagement.repository.CustomerRepository;
import com.mike.ordermanagement.repository.OrderRepository;
import com.mike.ordermanagement.repository.ProductRepository;
import com.mike.ordermanagement.repository.specification.OrderSpecificationBuilder;
import com.mike.ordermanagement.validation.product.CompositeValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final CompositeValidator compositeValidator;


    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, CustomerRepository customerRepository, CompositeValidator compositeValidator) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.compositeValidator = compositeValidator;
    }

    public OrderResponse createOrder(Long customerId, Long productId, Long quantity) {
        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer with ID " + customerId + " not found."));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + productId + " not found."));
        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());

        OrderProduct orderProduct = new OrderProduct(order, product, quantity);

        order.addOrderProduct(orderProduct);
        product.addOrderProduct(orderProduct);

        compositeValidator.validate(product);

        Order savedOrder = orderRepository.save(order);

        return OrderConverter.toOrderGetResponse(savedOrder);
    }

    public OrderResponse getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(OrderConverter::toOrderGetResponse)
                .orElseThrow(() -> new NoOrdersFoundException("Order with ID " + id + " not found."));
    }

    public List<OrderResponse> getFilteredPagedOrders(OrderFilter filter, Pageable pageable) {
        Specification<Order> specification = OrderSpecificationBuilder.build(filter);

        Page<Order> orderPage = orderRepository.findAll(specification, pageable);

        if (orderPage.isEmpty()) {
            throw new NoOrdersFoundException("No orders found matching the filter criteria.");
        }
        return orderPage.stream()
                .map(OrderConverter::toOrderGetResponse)
                .toList();
    }


}
