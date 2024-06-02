package com.tithe_system.tithe_management_system.repository;

import com.tithe_system.tithe_management_system.domain.EntityStatus;
import com.tithe_system.tithe_management_system.domain.UserGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long>, JpaSpecificationExecutor<UserGroup> {
    Optional<UserGroup> findByIdAndEntityStatusNot(Long userGroupId, EntityStatus entityStatus);
    Optional<UserGroup> findByNameAndEntityStatusNot(String name, EntityStatus entityStatus);
    List<UserGroup> findByEntityStatusNot(EntityStatus entityStatus);
    Page<UserGroup> findByEntityStatusNot(EntityStatus entityStatus, Pageable pageable);
}
