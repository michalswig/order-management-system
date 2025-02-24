package com.mike.ordermanagement.repository.specification;

import com.mike.ordermanagement.dto.order.OrderFilter;
import com.mike.ordermanagement.entity.Order;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderSpecification {

    private OrderSpecification() {
    }

    public static Specification<Order> build(OrderFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            addIfPresent(predicates, cb, root.get("customer").get("id"), filter.getCustomerId());
            addIfPresent(predicates, cb, root.get("status"), filter.getStatus());

            addDateRangePredicate(predicates, cb, root.get("orderDate"),
                    filter.getOrderDateFrom(), filter.getOrderDateTo()
            );
            addDateRangePredicate(predicates, cb, root.get("deliveryDate"),
                    filter.getDeliveryDateFrom(), filter.getDeliveryDateTo()
            );
            addDateRangePredicate(predicates, cb, root.get("canceledDate"),
                    filter.getCanceledDateFrom(), filter.getCanceledDateTo()
            );

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static <T> void addIfPresent(List<Predicate> predicates, CriteriaBuilder cb,
                                         Path<LocalDateTime> path, T value) {
        if(value != null) {
            predicates.add(cb.equal(path, value));
        }
    }

    private static void addDateRangePredicate(List<Predicate> predicates, CriteriaBuilder cb,
                                              Path<LocalDateTime> path, LocalDateTime from, LocalDateTime to) {
        if(from != null && to != null) {
            predicates.add(cb.between(path, from, to));
        } else if(from != null) {
            predicates.add(cb.greaterThanOrEqualTo(path, from));
        } else if(to != null) {
            predicates.add(cb.lessThanOrEqualTo(path, to));
        }
    }

}
