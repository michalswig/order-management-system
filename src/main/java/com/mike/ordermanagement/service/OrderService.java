package com.mike.ordermanagement.service;

import com.mike.ordermanagement.dto.order.OrderCreateRequest;
import com.mike.ordermanagement.dto.order.OrderFilter;
import com.mike.ordermanagement.dto.order.OrderResponse;
import com.mike.ordermanagement.entity.*;
import com.mike.ordermanagement.exceptions.CustomerNotFoundException;
import com.mike.ordermanagement.exceptions.NoOrdersFoundException;
import com.mike.ordermanagement.exceptions.ProductNotFoundException;
import com.mike.ordermanagement.mapper.OrderConverter;
import com.mike.ordermanagement.repository.CustomerRepository;
import com.mike.ordermanagement.repository.OrderRepository;
import com.mike.ordermanagement.repository.ProductRepository;
import com.mike.ordermanagement.repository.specification.OrderSpecificationBuilder;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final Clock clock;

    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository,
                        CustomerRepository customerRepository,
                        Clock clock) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.clock = clock;
    }

    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request) {
        validateQuantity(request);
        Order order = buildOrderWithProduct(request.getCustomerId(), request.getProductId(), request.getQuantity());
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

    private void validateQuantity(OrderCreateRequest request) {
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }
    }

    private Order buildOrderWithProduct(Long customerId, Long productId, Long quantity) {
        Customer customer = getCustomerById(customerId);
        Product product = getProductById(productId);

        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now(clock));

        OrderProduct orderProduct = new OrderProduct(order, product, quantity);
        order.addOrderProduct(orderProduct);
        product.addOrderProduct(orderProduct);

        return order;
    }

    private Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with ID " + customerId + " not found."));
    }

    private Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + " not found."));
    }
}
