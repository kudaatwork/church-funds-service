package com.tithe_system.tithe_management_system.business.auditables.impl;

import com.tithe_system.tithe_management_system.business.auditables.api.PaymentServiceAuditable;
import com.tithe_system.tithe_management_system.domain.Payment;
import com.tithe_system.tithe_management_system.repository.PaymentRepository;
import com.tithe_system.tithe_management_system.utils.trackers.AuditCreateEvent;
import com.tithe_system.tithe_management_system.utils.trackers.AuditEditEvent;

import java.util.Locale;

public class PaymentServiceAuditableImpl implements PaymentServiceAuditable {

    private final PaymentRepository paymentRepository;

    public PaymentServiceAuditableImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

   // @AuditCreateEvent
    @Override
    public Payment create(Payment payment, Locale locale, String username) {
        return paymentRepository.save(payment);
    }

    @AuditEditEvent
    @Override
    public Payment update(Payment payment, Locale locale, String username) {
        return paymentRepository.save(payment);
    }

    @AuditCreateEvent
    @Override
    public Payment reverse(Payment payment, Locale locale, String username) {
        return paymentRepository.save(payment);
    }
}
