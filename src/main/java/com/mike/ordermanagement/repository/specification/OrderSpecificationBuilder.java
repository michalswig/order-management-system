package com.mike.ordermanagement.repository.specification;

import com.mike.ordermanagement.dto.order.OrderFilter;
import com.mike.ordermanagement.entity.Order;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecificationBuilder {

    private OrderSpecificationBuilder() {
        throw new UnsupportedOperationException("you can not initiate it.");
    }

    public static Specification<Order> build(OrderFilter filter) {
        Specification<Order> spec = Specification.where(null);

        if (filter.getCustomerId() != null) {
            spec = spec.and(new OrderByCustomerIdSpecification(filter.getCustomerId()));
        }
        if (filter.getStatus() != null) {
            spec = spec.and(new OrderByStatusSpecification(filter.getStatus().toString()));
        }
        if (filter.getOrderDateFrom() != null || filter.getOrderDateTo() != null) {
            spec = spec.and(new OrderByDateRangeSpecification("orderDate", filter.getOrderDateFrom(), filter.getOrderDateTo()));
        }
        if (filter.getDeliveryDateFrom() != null || filter.getDeliveryDateTo() != null) {
            spec = spec.and(new OrderByDateRangeSpecification("deliveryDate", filter.getDeliveryDateFrom(), filter.getDeliveryDateTo()));
        }
        if (filter.getCanceledDateFrom() != null || filter.getCanceledDateTo() != null) {
            spec = spec.and(new OrderByDateRangeSpecification("canceledDate", filter.getCanceledDateFrom(), filter.getCanceledDateTo()));
        }

        return spec;
    }
}
