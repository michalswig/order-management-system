package com.mike.ordermanagement.repository.specification;

import com.mike.ordermanagement.entity.Order;
import com.mike.ordermanagement.repository.OrderRepository;
import com.mike.ordermanagement.util.OrderDataGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderByCustomerIdSpecificationTest {

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderByCustomerIdSpecification specification;

    @Test
    void givenCustomerId_whenFindOrders_thenReturnOrders() {
        //given
        Order order1 = OrderDataGenerator.orderGenerator();
        Order order2 = OrderDataGenerator.orderGenerator();
        List<Order> mockOrders = Arrays.asList(order1, order2);
        //Mock repository
        when(orderRepository.findAll(specification)).thenReturn(mockOrders);
        //when
        List<Order> orders = orderRepository.findAll(specification);
        //then
        assertThat(orders).hasSize(2);

    }


}