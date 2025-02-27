package com.mike.ordermanagement.repository.specification;

import com.mike.ordermanagement.entity.Order;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class OrderByStatusSpecification implements Specification<Order> {
    private final String status;

    public OrderByStatusSpecification(String status) {
        this.status = status;
    }

    @Override
    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (status == null) {
            return criteriaBuilder.conjunction();
        }
        return criteriaBuilder.equal(root.get(status), status);
    }

}
