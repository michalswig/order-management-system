package com.mike.ordermanagement.service;

import com.mike.ordermanagement.dto.OrderCreateRequest;
import com.mike.ordermanagement.dto.OrderFilter;
import com.mike.ordermanagement.dto.OrderGetResponse;
import com.mike.ordermanagement.entity.Order;
import com.mike.ordermanagement.exceptions.NoOrdersFoundException;
import com.mike.ordermanagement.mapper.OrderMapper;
import com.mike.ordermanagement.repository.OrderRepository;
import com.mike.ordermanagement.repository.specification.OrderSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Transactional
    public OrderGetResponse createOrder(OrderCreateRequest request) {

        if (request == null) {
            throw new IllegalArgumentException("OrderCreateRequest cannot be null");
        }

        Order order = orderMapper.toEntity(request);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }

    public OrderGetResponse getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(orderMapper::toDto)
                .orElseThrow(() -> new NoOrdersFoundException("Order with ID " + id + " not found."));
    }

    public List<OrderGetResponse> getOrders(OrderFilter filter) {
        List<OrderGetResponse> orders = orderRepository.findAll(OrderSpecification.build(filter), Sort.by("createdAt").descending())
                .stream()
                .map(orderMapper::toDto)
                .toList();

        if (orders.isEmpty()) {
            throw new NoOrdersFoundException("No orders found matching the filter criteria.");
        }
        return orders;
    }



}
