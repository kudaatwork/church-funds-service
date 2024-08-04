package com.tithe_system.tithe_management_system.repository;

import com.tithe_system.tithe_management_system.domain.EntityStatus;
import com.tithe_system.tithe_management_system.domain.Payment;
import com.tithe_system.tithe_management_system.domain.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long>, JpaSpecificationExecutor<Payment> {
    Optional<Payment> findByTransactionReferenceAndEntityStatusNot(String transactionReference, EntityStatus entityStatus);
    Optional<Payment> findByIdAndEntityStatusNot(Long id, EntityStatus entityStatus);
    Page<Payment> findByEntityStatusNot(EntityStatus entityStatus, Pageable pageable);
    Optional<Payment> findByTransactionReferenceAndPaymentStatusNotAndEntityStatusNot(String transactionReference,
                                                                                   PaymentStatus paymentStatus, EntityStatus entityStatus);
}
