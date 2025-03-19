package com.mike.ordermanagement.dto.order;

import com.mike.ordermanagement.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderFilter {
    private Long customerId;
    private OrderStatus status;
    private LocalDateTime orderDateFrom;
    private LocalDateTime orderDateTo;
    private LocalDateTime deliveryDateFrom;
    private LocalDateTime deliveryDateTo;
    private LocalDateTime canceledDateFrom;
    private LocalDateTime canceledDateTo;
}
