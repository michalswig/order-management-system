package com.mike.ordermanagement.service;

import com.mike.ordermanagement.dto.order.OrderCreateRequest;
import com.mike.ordermanagement.dto.order.OrderGetResponse;
import com.mike.ordermanagement.entity.*;
import com.mike.ordermanagement.repository.CustomerRepository;
import com.mike.ordermanagement.repository.OrderRepository;
import com.mike.ordermanagement.repository.ProductRepository;
import com.mike.ordermanagement.util.CustomerDataGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    private Clock fixedClock;
    private OrderCreateRequest request;

    @BeforeEach
    void setUp() {
        fixedClock = Clock.fixed(Instant.parse("2023-01-01T10:00:00Z"), ZoneId.of("UTC"));
        Long customerId = 1L;
        Long quantity = 2L;
        Long productId = 1L;
        request = new OrderCreateRequest(customerId, productId, quantity);
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
        OrderGetResponse orderGetResponse = orderService.createOrder(request);
        //Then
        assertThat(orderGetResponse.getStatus()).isEqualTo(status);
        assertThat(orderGetResponse.getCustomerId()).isEqualTo(request.getCustomerId());
        assertNotNull(orderGetResponse, "Expected order to be created");
        assertEquals(LocalDateTime.of(2023, 1, 1, 10, 0), orderGetResponse.getOrderDate());
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
        when(productRepository.findById(request.getProductId())).thenReturn(Optional.empty());
        //Then
        assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(request));
        verify(productRepository, times(1)).findById(request.getProductId());
        verify(orderRepository, never()).save(any(Order.class));
        verifyNoInteractions(customerRepository);
    }

    @Test
    void whenPassingInvalidOrderCreateRequestWithNonExistingCustomer_thenThrowException() {
        //Given
        when(productRepository.findById(1L)).thenReturn(Optional.of(new Product(request.getProductId())));
        //When
        when(customerRepository.findById(request.getCustomerId())).thenReturn(Optional.empty());
        //Then
        assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(request));
        verify(customerRepository, times(1)).findById(request.getCustomerId());
        verify(orderRepository, never()).save(any(Order.class));
    }

}
