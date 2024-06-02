package com.tithe_system.tithe_management_system.repository;

import com.tithe_system.tithe_management_system.domain.Account;
import com.tithe_system.tithe_management_system.domain.EntityStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {
    Optional<Account> findByIdAndEntityStatusNot(Long id, EntityStatus entityStatus);
    Optional<Account> findByAccountNumberAndEntityStatusNot(String accountNumber, EntityStatus entityStatus);
    Optional<Account> findByTransactionReferenceAndEntityStatusNot(String transactionReference, EntityStatus entityStatus);
    Page<Account> findByEntityStatusNot(EntityStatus entityStatus, Pageable pageable);
}
