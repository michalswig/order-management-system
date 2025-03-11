package com.mike.ordermanagement.service;

import com.mike.ordermanagement.dto.order.OrderCreateRequest;
import com.mike.ordermanagement.dto.order.OrderFilter;
import com.mike.ordermanagement.dto.order.OrderResponse;
import com.mike.ordermanagement.entity.*;
import com.mike.ordermanagement.exceptions.NoOrdersFoundException;
import com.mike.ordermanagement.mapper.OrderConverter;
import com.mike.ordermanagement.repository.CustomerRepository;
import com.mike.ordermanagement.repository.OrderRepository;
import com.mike.ordermanagement.repository.ProductRepository;
import com.mike.ordermanagement.repository.specification.OrderSpecificationBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final Clock clock = Clock.systemDefaultZone();

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    public OrderResponse createOrder(OrderCreateRequest request) {
        validateQuantity(request);
        Order order = getOrder(request.getCustomerId(), request.getProductId(), request.getQuantity());
        Order savedOrder = orderRepository.save(order);
        return getOrderGetResponse(savedOrder);
    }

    public OrderResponse getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(OrderConverter::toOrderResponse)
                .orElseThrow(() -> new NoOrdersFoundException("Order with ID " + id + " not found."));
    }

    public List<OrderResponse> getFilteredPagedOrders(OrderFilter filter, Pageable pageable) {
        Specification<Order> specification = getOrderSpecification(filter);
        Page<Order> orderPage = getOrders(pageable, specification);
        validateOrderPage(orderPage);
        return getOrdersGetResponse(orderPage);
    }

    private List<OrderResponse> getOrdersGetResponse(Page<Order> orderPage) {
        return orderPage.stream()
                .map(OrderConverter::toOrderResponse)
                .toList();
    }

    private void validateOrderPage(Page<Order> orderPage) {
        if (orderPage.isEmpty()) {
            throw new NoOrdersFoundException("No orders found matching the filter criteria.");
        }
    }

    private Page<Order> getOrders(Pageable pageable, Specification<Order> specification) {
        return orderRepository.findAll(specification, pageable);
    }

    private Specification<Order> getOrderSpecification(OrderFilter filter) {
        return OrderSpecificationBuilder.build(filter);
    }

    private void validateQuantity(OrderCreateRequest request) {
        if (request.getQuantity() < 0) {
            throw new IllegalArgumentException();
        }
    }

    private OrderResponse getOrderGetResponse(Order savedOrder) {
        return OrderConverter.toOrderResponse(savedOrder);
    }

    private Order getOrder(Long customerId, Long productId, Long quantity) {
        Product product = getProduct(productId);
        Order order = getOrder(getCustomer(customerId));
        OrderProduct orderProduct = new OrderProduct(order, product, quantity);
        order.addOrderProduct(orderProduct);
        product.addOrderProduct(orderProduct);
        return order;
    }

    private Order getOrder(Customer customer) {
        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now(clock));
        return order;
    }

    private Product getProduct(Long productId) {
        return productRepository
                .findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + productId + " not found."));
    }

    private Customer getCustomer(Long customerId) {
        return customerRepository
                .findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer with ID " + customerId + " not found."));
    }


}
