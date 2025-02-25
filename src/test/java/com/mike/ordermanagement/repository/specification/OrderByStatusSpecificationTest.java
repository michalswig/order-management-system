package com.mike.ordermanagement.repository.specification;

import com.mike.ordermanagement.entity.Order;
import com.mike.ordermanagement.entity.OrderStatus;
import com.mike.ordermanagement.repository.OrderRepository;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderByStatusSpecificationTest {
    @Mock
    OrderRepository orderRepository;
    @InjectMocks
    OrderByStatusSpecification specification;

    @Test
    void givenStatus_whenFindOrders_thenReturnGivenOrders() {
        //given
        Order order1 = new Order();
        order1.setStatus(OrderStatus.PENDING);
        Order order2 = new Order();
        order2.setStatus(OrderStatus.PENDING);
        List<Order> mockOrders = Arrays.asList(order1, order2);
        //Mock repository
        when(orderRepository.findAll(specification)).thenReturn(mockOrders);
        //when
        List<Order> orders = orderRepository.findAll(specification);
        //then
        AssertionsForInterfaceTypes.assertThat(orders).hasSize(2);
    }


}