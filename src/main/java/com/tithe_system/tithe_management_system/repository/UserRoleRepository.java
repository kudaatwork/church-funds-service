package com.tithe_system.tithe_management_system.repository;

import com.tithe_system.tithe_management_system.domain.EntityStatus;
import com.tithe_system.tithe_management_system.domain.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRoleRepository extends JpaRepository<UserRole, Long>, JpaSpecificationExecutor<UserRole> {
    Optional<UserRole> findByNameAndEntityStatusNot(String name, EntityStatus entityStatus);
    Optional<UserRole> findByIdAndEntityStatusNot(Long id, EntityStatus entityStatus);
    List<UserRole> findByEntityStatusNot(EntityStatus entityStatus);
    Page<UserRole> findByEntityStatusNot(EntityStatus entityStatus, Pageable pageable);
    Set<UserRole> findByIdInAndEntityStatusNot(List<Long> userIds, EntityStatus entityStatus);
}
