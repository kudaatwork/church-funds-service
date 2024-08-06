package com.tithe_system.tithe_management_system.repository.specification;

import com.tithe_system.tithe_management_system.domain.EntityStatus;
import com.tithe_system.tithe_management_system.domain.UserRole;
import com.tithe_system.tithe_management_system.domain.UserRole_;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class UserRoleSpecification {

    public static Specification<UserRole> deleted(EntityStatus entityStatus) {
        return (root, query, cb) -> {
            Predicate p = cb.notLike(root.get(UserRole_.entityStatus).as(String.class), "%" + entityStatus + "%");
            return p;
        };
    }

    public static Specification<UserRole> any(final String search) {

        return (root, query, cb) -> {

            Predicate p = cb.or(cb.like(root.get(UserRole_.name), "%" + search.toUpperCase() + "%"));

            return p;
        };
    }
}
