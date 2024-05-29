package com.tithe_system.tithe_management_system.repository;

import com.tithe_system.tithe_management_system.domain.EntityStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.tithe_system.tithe_management_system.domain.District;
import java.util.List;
import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District, Long>, JpaSpecificationExecutor<District> {
    Optional<District> findByNameAndEntityStatusNot(String name, EntityStatus entityStatus);
    Optional<District> findByIdAndEntityStatusNot(Long id, EntityStatus entityStatus);
    List<District> findByEntityStatusNot(EntityStatus entityStatus);
    Page<District> findByEntityStatusNot(EntityStatus entityStatus, Pageable pageable);
    List<District> findByProvinceIdAndEntityStatusNot(Long provinceId, EntityStatus entityStatus);
    List<District> findByRegionIdAndEntityStatusNot(Long regionId, EntityStatus entityStatus);
}
