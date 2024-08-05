package com.tithe_system.tithe_management_system.repository.specification;

import com.tithe_system.tithe_management_system.domain.Account;
import com.tithe_system.tithe_management_system.domain.Account_;
import com.tithe_system.tithe_management_system.domain.Assembly;
import com.tithe_system.tithe_management_system.domain.Assembly_;
import com.tithe_system.tithe_management_system.domain.EntityStatus;
import com.tithe_system.tithe_management_system.domain.Region;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class RegionSpecification {
    public static Specification<Region> deleted(EntityStatus entityStatus) {
        return (root, query, cb) -> {
            Predicate p = cb.notLike(root.get(Account_.entityStatus).as(String.class), "%" + entityStatus + "%");
            return p;
        };
    }

    public static Specification<Account> nameLike(final String name) {
        return (root, query, cb) -> {
            Predicate p = cb.like(root.get(Account_.name).as(String.class), name + "%");
            return p;
        };
    }

    public static Specification<Assembly> any(final String search) {

        return (root, query, cb) -> {

            Predicate p = cb.or(cb.like(root.get(Assembly_.name), "%" + search.toUpperCase() + "%"));

            return p;
        };
    }
}
