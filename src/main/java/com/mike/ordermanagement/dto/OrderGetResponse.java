package com.mike.ordermanagement.dto;

import com.mike.ordermanagement.entity.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public record OrderGetResponse(
        Long id,
        String name,
        Long quantity,
        BigDecimal price,
        OrderStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt
) { }
