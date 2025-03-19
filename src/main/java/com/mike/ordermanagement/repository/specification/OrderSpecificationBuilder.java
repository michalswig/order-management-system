package com.mike.ordermanagement.repository.specification;

import com.mike.ordermanagement.dto.order.OrderFilter;
import com.mike.ordermanagement.entity.Order;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

public class OrderSpecificationBuilder {

    public static Specification<Order> build(OrderFilter filter) {
        Specification<Order> spec = Specification.where(null);

        if (Objects.nonNull(filter.getCustomerId())) {
            spec = spec.and(new OrderByCustomerIdSpecification(filter.getCustomerId()));
        }
        if (Objects.nonNull(filter.getStatus())) {
            spec = spec.and(new OrderByStatusSpecification(filter.getStatus()));
        }
        if (Objects.nonNull(filter.getOrderDateFrom()) || Objects.nonNull(filter.getOrderDateTo())) {
            spec = spec.and(new OrderByDateRangeSpecification("orderDate", filter.getOrderDateFrom(), filter.getOrderDateTo()));
        }
        if (Objects.nonNull(filter.getDeliveryDateFrom()) || Objects.nonNull(filter.getDeliveryDateTo())) {
            spec = spec.and(new OrderByDateRangeSpecification("deliveryDate", filter.getDeliveryDateFrom(), filter.getDeliveryDateTo()));
        }
        if (Objects.nonNull(filter.getCanceledDateFrom()) || Objects.nonNull(filter.getCanceledDateTo())) {
            spec = spec.and(new OrderByDateRangeSpecification("canceledDate", filter.getCanceledDateFrom(), filter.getCanceledDateTo()));
        }

        return spec;
    }
}
