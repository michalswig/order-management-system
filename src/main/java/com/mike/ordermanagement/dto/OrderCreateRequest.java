package com.mike.ordermanagement.dto;

import jakarta.validation.constraints.*;
import com.mike.ordermanagement.entity.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderCreateRequest(
        Long id,

        @NotBlank(message = "{NotNull.order.name}")
        @Size(min = 3, max = 100, message = "{Size.order.name}")
        String name,

        @Min(value = 1, message = "{Min.order.quantity}")
        Long quantity,

        @NotNull(message = "{NotNull.order.price}")
        @DecimalMin(value = "0.01", message = "{DecimalMin.order.price}")
        @Digits(integer = 10, fraction = 2, message = "{Digits.order.price}")
        BigDecimal price,

        @NotNull(message = "{NotNull.order.status}")
        OrderStatus status,

        @PastOrPresent(message = "{PastOrPresent.order.createdAt}")
        LocalDateTime createdAt,

        @PastOrPresent(message = "{PastOrPresent.order.updatedAt}")
        LocalDateTime updatedAt,

        @FutureOrPresent(message = "{FutureOrPresent.order.deletedAt}")
        LocalDateTime deletedAt
) {}
