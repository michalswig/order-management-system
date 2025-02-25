package com.mike.ordermanagement.repository.specification;

import com.mike.ordermanagement.entity.Order;
import com.mike.ordermanagement.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderByDateRangeSpecificationTest {

    @Mock
    OrderRepository orderRepository;

    @Test
    void givenDateRange_whenFindOrders_thenReturnMatchingOrders() {
        // Given
        Order order1 = new Order();
        order1.setOrderDate(LocalDateTime.of(2024, 1, 1, 0, 0));

        Order order2 = new Order();
        order2.setOrderDate(LocalDateTime.of(2024, 1, 31, 23, 59));

        Specification<Order> specification = new OrderByDateRangeSpecification(
                "orderDate",
                LocalDateTime.of(2024, 1, 1, 0, 0),
                LocalDateTime.of(2024, 1, 31, 23, 59)
        );

        when(orderRepository.findAll(specification)).thenReturn(List.of(order1));

        // When
        List<Order> orders = orderRepository.findAll(specification);

        // Then
        assertThat(orders).hasSize(1);
        assertThat(orders.get(0).getOrderDate()).isEqualTo(LocalDateTime.of(2024, 1, 1, 0, 0));

    }
}
