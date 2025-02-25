package com.mike.ordermanagement.repository.specification;

import com.mike.ordermanagement.entity.Customer;
import com.mike.ordermanagement.entity.Order;
import com.mike.ordermanagement.repository.OrderRepository;
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
        Customer customer = new Customer();
        customer.setId(1L);
        Order order1 = new Order();
        order1.setCustomer(customer);
        Order order2 = new Order();
        order2.setCustomer(customer);
        List<Order> mockOrders = Arrays.asList(order1, order2);
        //Mock repository
        when(orderRepository.findAll(specification)).thenReturn(mockOrders);
        //when
        List<Order> orders = orderRepository.findAll(specification);
        //then
        assertThat(orders).hasSize(2);

    }


}