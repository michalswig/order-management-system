package com.mike.ordermanagement.service;

import com.mike.ordermanagement.dto.OrderCreateRequest;
import com.mike.ordermanagement.dto.OrderGetResponse;
import com.mike.ordermanagement.entity.Order;
import com.mike.ordermanagement.mapper.OrderMapper;
import com.mike.ordermanagement.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
