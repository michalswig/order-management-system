package com.mike.ordermanagement.service;

import com.mike.ordermanagement.dto.order.OrderCreateRequest;
import com.mike.ordermanagement.dto.order.OrderFilter;
import com.mike.ordermanagement.dto.order.OrderResponse;
import com.mike.ordermanagement.entity.Order;
import com.mike.ordermanagement.exceptions.NoOrdersFoundException;
import com.mike.ordermanagement.factory.OrderFactory;
import com.mike.ordermanagement.mapper.OrderConverter;
import com.mike.ordermanagement.repository.OrderRepository;
import com.mike.ordermanagement.repository.specification.OrderSpecificationBuilder;
import com.mike.ordermanagement.validation.order.OrderValidator;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderFactory orderFactory;
    private final OrderValidator orderValidator;

    public OrderService(OrderRepository orderRepository,
                        OrderFactory orderFactory,
                        OrderValidator orderValidator) {
        this.orderRepository = orderRepository;
        this.orderFactory = orderFactory;
        this.orderValidator = orderValidator;
    }

    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request) {
        orderValidator.validate(request);
        Order order = orderFactory.buildOrderEntity(request);
        Order savedOrder = orderRepository.save(order);
        log.info("Order created with ID: {}", savedOrder.getId());
        return OrderConverter.toOrderResponse(savedOrder);
    }

    public OrderResponse getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(OrderConverter::toOrderResponse)
                .orElseThrow(() -> new NoOrdersFoundException("Order with ID " + id + " not found."));
    }

    public List<OrderResponse> getFilteredPagedOrders(OrderFilter filter, Pageable pageable) {
        Specification<Order> specification = OrderSpecificationBuilder.build(filter);
        Page<Order> orderPage = orderRepository.findAll(specification, pageable);
        if (orderPage.isEmpty()) {
            throw new NoOrdersFoundException("No orders found matching the filter criteria.");
        }
        return orderPage.stream()
                .map(OrderConverter::toOrderResponse)
                .toList();
    }

}
