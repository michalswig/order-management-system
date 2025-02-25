package com.mike.ordermanagement.repository.specification;

import com.mike.ordermanagement.dto.order.OrderFilter;
import com.mike.ordermanagement.entity.Order;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderSpecificationTest {
    private Root<Order> root;
    private CriteriaQuery<?> query;
    private CriteriaBuilder cb;
    private OrderFilter filter;

    @BeforeEach
    void setUp() {
        root = mock(Root.class);
        query = mock(CriteriaQuery.class);
        cb = mock(CriteriaBuilder.class);
        filter = new OrderFilter();
    }

    @Test
    void shouldReturnEmptyPredicateWhenFilterIsEmpty() {
        when(root.get("customer")).thenReturn(mock(Path.class));
        when(root.get("status")).thenReturn(mock(Path.class));
        when(root.get("orderDate")).thenReturn(mock(Path.class));

        Predicate predicate = OrderSpecification.build(filter).toPredicate(root, query, cb);
        assertThat(predicate).isNull();
    }



}