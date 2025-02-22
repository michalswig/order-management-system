package com.mike.ordermanagement.repository.specification;

import com.mike.ordermanagement.dto.order.OrderFilter;
import com.mike.ordermanagement.entity.Order;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class OrderSpecification {

    private OrderSpecification() {
    }

    public static Specification<Order> build(OrderFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
//            if (filter.getId() != null) {
//                predicates.add(cb.equal(root.get("id"), filter.getId()));
//            }
//            if (filter.getName() != null) {
//                predicates.add(cb.like(root.get("name"), "%" + filter.getName() + "%"));
//            }
//            if (filter.getQuantity() != null) {
//                predicates.add(cb.equal(root.get("quantity"), filter.getQuantity()));
//            }
//            if (filter.getPrice() != null) {
//                predicates.add(cb.equal(root.get("price"), filter.getPrice()));
//            }
//            if (filter.getStatus() != null) {
//                predicates.add(cb.equal(root.get("status"), filter.getStatus()));
//            }
//            if (filter.getCreatedAt() != null) {
//                predicates.add(cb.equal(root.get("createdAt"), filter.getCreatedAt()));
//            }
//            if (filter.getUpdatedAt() != null) {
//                predicates.add(cb.equal(root.get("updatedAt"), filter.getUpdatedAt()));
//            }
//            if (filter.getDeletedAt() != null) {
//                predicates.add(cb.equal(root.get("deletedAt"), filter.getDeletedAt()));
//            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}
