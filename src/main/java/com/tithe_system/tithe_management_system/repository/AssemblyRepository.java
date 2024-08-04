package com.tithe_system.tithe_management_system.repository;

import com.tithe_system.tithe_management_system.domain.EntityStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.tithe_system.tithe_management_system.domain.Assembly;

import java.util.List;
import java.util.Optional;

public interface AssemblyRepository extends JpaRepository<Assembly, Long>, JpaSpecificationExecutor
        <Assembly> {
    Optional<Assembly> findByNameAndEntityStatusNot(String name, EntityStatus entityStatus);
    Optional<Assembly> findByIdAndEntityStatusNot(Long id, EntityStatus entityStatus);
    List<Assembly> findByEntityStatusNot(EntityStatus entityStatus);
    Page<Assembly> findByEntityStatusNot(EntityStatus entityStatus, Pageable pageable);
    List<Assembly> findByProvinceIdAndEntityStatusNot(Long provinceId, EntityStatus entityStatus);
    List<Assembly> findByRegionIdAndEntityStatusNot(Long regionId, EntityStatus entityStatus);
    List<Assembly> findByDistrictIdAndEntityStatusNot(Long id, EntityStatus entityStatus);
    Page<Assembly> findByDistrictIdAndEntityStatusNot(Long id, EntityStatus entityStatus, Pageable pageable);
}
