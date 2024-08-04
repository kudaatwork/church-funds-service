package com.tithe_system.tithe_management_system.repository;

import com.tithe_system.tithe_management_system.domain.EntityStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.tithe_system.tithe_management_system.domain.Province;
import java.util.List;
import java.util.Optional;

public interface ProvinceRepository extends JpaRepository<Province, Long>, JpaSpecificationExecutor<Province> {
    Optional<Province> findByNameAndEntityStatusNot(String name, EntityStatus entityStatus);
    Optional<Province> findByIdAndEntityStatusNot(Long id, EntityStatus entityStatus);
    List<Province> findByEntityStatusNot(EntityStatus entityStatus);
    Page<Province> findByEntityStatusNot(EntityStatus entityStatus, Pageable pageable);
    List<Province> findByRegionIdAndEntityStatusNot(Long regionId, EntityStatus entityStatus);
    Optional<Province> findByRegionIdAndEntityStatus(Long regionId, EntityStatus entityStatus);
}
