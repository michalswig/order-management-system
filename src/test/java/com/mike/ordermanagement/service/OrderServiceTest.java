package com.mike.ordermanagement.service;

import com.mike.ordermanagement.dto.order.OrderCreateRequest;
import com.mike.ordermanagement.dto.order.OrderResponse;
import com.mike.ordermanagement.entity.Customer;
import com.mike.ordermanagement.entity.Order;
import com.mike.ordermanagement.entity.OrderStatus;
import com.mike.ordermanagement.factory.OrderFactory;
import com.mike.ordermanagement.repository.OrderRepository;
import com.mike.ordermanagement.validation.order.OrderValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderFactory orderFactory;

    @Mock
    private OrderValidator orderValidator;

    @InjectMocks
    private OrderService orderService;

    private OrderCreateRequest orderCreateRequest;
    private Order mockOrder;
    private Order savedOrder;

    @BeforeEach
    void setUp() {
        orderCreateRequest = new OrderCreateRequest(1L, 2L, 3L);

        mockOrder = new Order();
        mockOrder.setId(100L);

        Customer customer = new Customer();
        customer.setId(1L);

        savedOrder = new Order();
        savedOrder.setId(200L);
        savedOrder.setCustomer(customer);
        savedOrder.setStatus(OrderStatus.PENDING);
        savedOrder.setOrderDate(LocalDateTime.now());
    }

    @Test
    void shouldCreateOrderSuccessfullyWhenValidRequest() {
        //Given
        doNothing().when(orderValidator).validate(orderCreateRequest);
        when(orderFactory.buildOrderEntity(orderCreateRequest)).thenReturn(mockOrder);
        when(orderRepository.save(mockOrder)).thenReturn(savedOrder);

        //When
        OrderResponse order = orderService.createOrder(orderCreateRequest);
        //Then
        verify(orderValidator, times(1)).validate(orderCreateRequest);
        verify(orderFactory, times(1)).buildOrderEntity(orderCreateRequest);
        verify(orderRepository, times(1)).save(mockOrder);
        assertNotNull(order);
    }

}

