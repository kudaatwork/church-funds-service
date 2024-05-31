package com.tithe_system.tithe_management_system.business.validations.api;

import com.tithe_system.tithe_management_system.utils.requests.CreatePaymentRequest;

public interface PaymentServiceValidator {
    boolean isRequestValidForCreation(CreatePaymentRequest createPaymentRequest);
}
