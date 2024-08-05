package com.tithe_system.tithe_management_system.repository.specification;

import com.tithe_system.tithe_management_system.domain.Assembly;
import com.tithe_system.tithe_management_system.domain.Assembly_;
import com.tithe_system.tithe_management_system.domain.EntityStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class AssemblySpecification {
    public static Specification<Assembly> deleted(EntityStatus entityStatus) {
        return (root, query, cb) -> {
            Predicate p = cb.notLike(root.get(Assembly_.entityStatus).as(String.class), "%" + entityStatus + "%");
            return p;
        };
    }

    public static Specification<Assembly> nameLike(final String name) {
        return (root, query, cb) -> {
            Predicate p = cb.like(root.get(Assembly_.name).as(String.class), name + "%");
            return p;
        };
    }

    public static Specification<Assembly> contactPhoneNumberLike(final String contactPhoneNumber) {
        return (root, query, cb) -> {
            Predicate p = cb.like(root.get(Assembly_.contactPhoneNumber).as(String.class), contactPhoneNumber + "%");
            return p;
        };
    }

    public static Specification<Assembly> contactEmailLike(final String contactEmail) {
        return (root, query, cb) -> {
            Predicate p = cb.like(root.get(Assembly_.contactEmail).as(String.class), contactEmail + "%");
            return p;
        };
    }

    public static Specification<Assembly> any(final String search) {

        return (root, query, cb) -> {

            Predicate p = cb.or(
                    cb.like(root.get(Assembly_.name), "%" + search.toUpperCase() + "%"),
                    cb.like(root.get(Assembly_.contactPhoneNumber), "%" + search.toUpperCase() + "%"),
                    cb.like(root.get(Assembly_.contactEmail), "%" + search.toUpperCase() + "%")
            );

            return p;
        };
    }
}
