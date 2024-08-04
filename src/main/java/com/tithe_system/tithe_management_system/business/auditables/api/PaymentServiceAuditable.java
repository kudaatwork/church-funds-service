package com.tithe_system.tithe_management_system.business.auditables.api;

import com.tithe_system.tithe_management_system.domain.Payment;
import java.util.Locale;

public interface PaymentServiceAuditable {
    Payment create(Payment payment, Locale locale, String username);
    Payment update(Payment payment, Locale locale, String username);
    Payment reverse(Payment payment, Locale locale, String username);
}
