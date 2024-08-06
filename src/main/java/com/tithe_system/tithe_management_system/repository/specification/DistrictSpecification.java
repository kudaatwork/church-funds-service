package com.tithe_system.tithe_management_system.repository.specification;

import com.tithe_system.tithe_management_system.domain.District;
import com.tithe_system.tithe_management_system.domain.District_;
import com.tithe_system.tithe_management_system.domain.EntityStatus;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class DistrictSpecification {

    public static Specification<District> deleted(EntityStatus entityStatus) {
        return (root, query, cb) -> {
            Predicate p = cb.notLike(root.get(District_.entityStatus).as(String.class), "%" + entityStatus + "%");
            return p;
        };
    }

    public static Specification<District> any(final String search) {

        return (root, query, cb) -> {

            Predicate p = cb.or(cb.like(root.get(District_.name), "%" + search.toUpperCase() + "%"));

            return p;
        };
    }
}
