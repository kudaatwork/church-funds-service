package com.tithe_system.tithe_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.tithe_system.tithe_management_system.domain.Payment;

public interface TitheRepository extends JpaRepository<Payment, Long>, JpaSpecificationExecutor<Payment> {
}
