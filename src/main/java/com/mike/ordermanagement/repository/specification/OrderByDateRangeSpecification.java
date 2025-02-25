package com.mike.ordermanagement.repository.specification;

import com.mike.ordermanagement.entity.Order;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class OrderByDateRangeSpecification implements Specification<Order> {
    private final String fieldName;
    private final LocalDateTime from;
    private final LocalDateTime to;

    public OrderByDateRangeSpecification(String fieldName, LocalDateTime from, LocalDateTime to) {
        this.fieldName = fieldName;
        this.from = from;
        this.to = to;
    }

    @Override
    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (from != null && to != null) {
            return criteriaBuilder.between(root.get(fieldName), from, to);
        } else if (from != null) {
            return criteriaBuilder.greaterThanOrEqualTo(root.get(fieldName), from);
        } else if (to !=null) {
            return criteriaBuilder.lessThanOrEqualTo(root.get(fieldName), to);
        }
        return criteriaBuilder.conjunction();
    }

}
