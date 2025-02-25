package com.mike.ordermanagement.repository.specification;

import com.mike.ordermanagement.entity.Order;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class OrderByCustomerIdSpecification implements Specification<Order> {
    private final Long customerId;

    public OrderByCustomerIdSpecification(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (customerId == null) {
            return criteriaBuilder.conjunction();
        }
        return criteriaBuilder.equal(root.get("customer").get("id"), customerId);
    }

}
