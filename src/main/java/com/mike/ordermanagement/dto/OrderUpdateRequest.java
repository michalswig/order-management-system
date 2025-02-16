package com.mike.ordermanagement.dto;

import com.mike.ordermanagement.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class OrderUpdateRequest {
    private Long id;
    private String name;
    private Long quantity;
    private BigDecimal price;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
