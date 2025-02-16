package com.mike.ordermanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "{NotNull.order.name}")
    @Size(min = 3, max = 100, message = "{Size.order.name}")
    private String name;

    @Min(value = 1, message = "{Min.order.quantity}")
    private Long quantity;

    @NotNull(message = "{NotNull.order.price}")
    @DecimalMin(value = "0.01", message = "{DecimalMin.order.price}")
    @Digits(integer = 10, fraction = 2, message = "{Digits.order.price}")
    private BigDecimal price;

    @NotNull(message = "{NotNull.order.status}")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @PastOrPresent(message = "{PastOrPresent.order.createdAt}")
    private LocalDateTime createdAt;

    @PastOrPresent(message = "{PastOrPresent.order.updatedAt}")
    private LocalDateTime updatedAt;

    @FutureOrPresent(message = "{FutureOrPresent.order.deletedAt}")
    private LocalDateTime deletedAt;

}
