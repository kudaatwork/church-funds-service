package com.tithe_system.tithe_management_system.repository;

import com.tithe_system.tithe_management_system.domain.AuditTrail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository extends JpaRepository<AuditTrail,Long> {
    public AuditTrail save(AuditTrail auditTrail);
}

