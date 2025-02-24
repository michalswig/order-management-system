package com.mike.ordermanagement.controller;

import com.mike.ordermanagement.dto.order.OrderCreateRequest;
import com.mike.ordermanagement.dto.order.OrderFilter;
import com.mike.ordermanagement.dto.order.OrderGetResponse;
import com.mike.ordermanagement.service.OrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderGetResponse> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping
    public ResponseEntity<List<OrderGetResponse>> getOrders(@ModelAttribute OrderFilter filter, Pageable pageable) {
        return ResponseEntity.ok(orderService.getFilteredOrders(filter, pageable));
    }

    @PostMapping
    public ResponseEntity<OrderGetResponse> createOrder(@RequestBody OrderCreateRequest request) {
        OrderGetResponse response = orderService.createOrder(request.getCustomerId(), request.getProductId(), request.getQuantity());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
