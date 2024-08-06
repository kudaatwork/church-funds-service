package com.tithe_system.tithe_management_system.repository.specification;

import com.tithe_system.tithe_management_system.domain.EntityStatus;
import com.tithe_system.tithe_management_system.domain.UserGroup;
import com.tithe_system.tithe_management_system.domain.UserGroup_;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class UserGroupSpecification {

    public static Specification<UserGroup> deleted(EntityStatus entityStatus) {
        return (root, query, cb) -> {
            Predicate p = cb.notLike(root.get(UserGroup_.entityStatus).as(String.class), "%" + entityStatus + "%");
            return p;
        };
    }

    public static Specification<UserGroup> any(final String search) {

        return (root, query, cb) -> {

            Predicate p = cb.or(cb.like(root.get(UserGroup_.name), "%" + search.toUpperCase() + "%"));

            return p;
        };
    }
}
