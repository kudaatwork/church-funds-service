package com.tithe_system.tithe_management_system.repository.specification;

import com.tithe_system.tithe_management_system.domain.EntityStatus;
import com.tithe_system.tithe_management_system.domain.Region;
import com.tithe_system.tithe_management_system.domain.Region_;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class RegionSpecification {
    public static Specification<Region> deleted(EntityStatus entityStatus) {
        return (root, query, cb) -> {
            Predicate p = cb.notLike(root.get(Region_.entityStatus).as(String.class), "%" + entityStatus + "%");
            return p;
        };
    }

    public static Specification<Region> any(final String search) {

        return (root, query, cb) -> {

            Predicate p = cb.or(cb.like(root.get(Region_.name), "%" + search.toUpperCase() + "%"));

            return p;
        };
    }
}
