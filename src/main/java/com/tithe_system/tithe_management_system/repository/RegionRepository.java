package com.tithe_system.tithe_management_system.repository;

import com.tithe_system.tithe_management_system.domain.EntityStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.tithe_system.tithe_management_system.domain.Region;

import java.util.List;
import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long>, JpaSpecificationExecutor<Region> {
    Optional<Region> findByIdAndEntityStatusNot(Long regionId, EntityStatus entityStatus);
    Optional<Region> findByNameAndEntityStatusNot(String name, EntityStatus entityStatus);
    List<Region> findByEntityStatusNot(EntityStatus entityStatus);
    Page<Region> findByEntityStatusNot(EntityStatus entityStatus, Pageable pageable);
}
