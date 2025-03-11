package com.mike.ordermanagement.service;

import com.mike.ordermanagement.dto.order.OrderCreateRequest;
import com.mike.ordermanagement.dto.order.OrderFilter;
import com.mike.ordermanagement.dto.order.OrderResponse;
import com.mike.ordermanagement.entity.*;
import com.mike.ordermanagement.exceptions.CustomerNotFoundException;
import com.mike.ordermanagement.exceptions.NoOrdersFoundException;
import com.mike.ordermanagement.exceptions.ProductNotFoundException;
import com.mike.ordermanagement.repository.CustomerRepository;
import com.mike.ordermanagement.repository.OrderRepository;
import com.mike.ordermanagement.repository.ProductRepository;
import com.mike.ordermanagement.util.CustomerDataGenerator;
import com.mike.ordermanagement.util.OrderDataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CustomerRepository customerRepository;

    private Clock fixedClock;
    private OrderService orderService;

    private OrderCreateRequest request;

    @BeforeEach
    void setUp() {
        fixedClock = Clock.fixed(Instant.parse("2023-01-01T10:00:00Z"), ZoneId.systemDefault());

        orderService = new OrderService(
                orderRepository,
                productRepository,
                customerRepository,
                fixedClock
        );

        request = new OrderCreateRequest(1L, 1L, 2L);
    }

    @Test
    void whenPassingValidOrderCreateRequest_thenOrderIsCreated() {
        //Given
        Long orderId = 1L;
        Product product = CustomerDataGenerator.generateProductPassingProductId(request.getProductId());
        Customer customer = CustomerDataGenerator.generateCustomerPassingCustomerId(request.getCustomerId());
        OrderStatus status = OrderStatus.PENDING;
        Order order = new Order(orderId, customer, status, LocalDateTime.now(fixedClock), null, null);
        OrderProduct orderProduct = new OrderProduct(order, product, request.getQuantity());
        product.setOrderProducts(new ArrayList<>());
        order.addOrderProduct(orderProduct);
        when(customerRepository.findById(request.getCustomerId())).thenReturn(Optional.of(customer));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        //When
        OrderResponse orderGetResponse = orderService.createOrder(request);
        //Then
        assertThat(orderGetResponse.getStatus()).isEqualTo(status);
        assertThat(orderGetResponse.getCustomerId()).isEqualTo(request.getCustomerId());
        assertNotNull(orderGetResponse, "Expected order to be created");
        assertEquals(LocalDateTime.of(2023, 1, 1, 11, 0), orderGetResponse.getOrderDate());
        verify(productRepository, times(1)).findById(1L);
        verify(customerRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void whenPassingInvalidOrderCreateRequestWithNegativeQuantity_thenThrowException() {
        //Given
        request.setQuantity(-1L);
        //When & Then
        assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(request));
    }

    @Test
    void whenPassingInvalidOrderCreateRequestWithNonExistingProduct_thenThrowException() {
        //When
        Customer customer = CustomerDataGenerator.generateCustomerPassingCustomerId(request.getCustomerId());
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(productRepository.findById(request.getProductId())).thenReturn(Optional.empty());
        //Then
        assertThrows(ProductNotFoundException.class, () -> orderService.createOrder(request));
        verify(productRepository, times(1)).findById(request.getProductId());
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void whenPassingInvalidOrderCreateRequestWithNonExistingCustomer_thenThrowException() {
        //Given&When
        when(customerRepository.findById(request.getCustomerId())).thenReturn(Optional.empty());
        //Then
        assertThrows(CustomerNotFoundException.class, () -> orderService.createOrder(request));
        verify(customerRepository, times(1)).findById(request.getCustomerId());
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void whenPassingValidOrderId_thenReturnOrder() {
        //Given
        Long orderId = 1L;
        Order order = OrderDataGenerator.orderGenerator();
        order.setId(orderId);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        //When & Then
        OrderResponse orderById = orderService.getOrderById(orderId);
        assertThat(orderId).isEqualTo(orderById.getId());
        verify(orderRepository, times(1)).findById(orderId);

    }

    @Test
    void whenOrderIdNotFound_thenThrowNoOrdersFoundException() {
        // Given
        Long orderId = 999L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NoOrdersFoundException.class, () -> orderService.getOrderById(orderId));
    }

    @Test
    void whenPassingValidFilterAndValidPageable_thenOrdersListIsFound() {
        //given
        OrderFilter filter = new OrderFilter();
        Pageable pageable = PageRequest.of(0, 10);
        Order order1 = OrderDataGenerator.orderGenerator();
        Order order2 = OrderDataGenerator.orderGenerator();
        List<Order> orders = Arrays.asList(order1, order2);
        Page<Order> orderPage = new PageImpl<>(orders,pageable,orders.size());
        when(orderRepository.findAll(any(Specification.class),eq(pageable))).thenReturn(orderPage);
        //when
        List<OrderResponse> filteredOrders = orderService.getFilteredPagedOrders(filter, pageable);
        //then
        assertThat(filteredOrders.size()).isEqualTo(2);
        verify(orderRepository, times(1)).findAll(any(Specification.class),eq(pageable));
    }


}

