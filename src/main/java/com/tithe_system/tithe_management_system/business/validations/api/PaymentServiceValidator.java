package com.tithe_system.tithe_management_system.business.validations.api;

import com.tithe_system.tithe_management_system.utils.requests.CreatePaymentRequest;
import com.tithe_system.tithe_management_system.utils.requests.ReversePaymentRequest;

public interface PaymentServiceValidator {
    boolean isRequestValidForCreation(CreatePaymentRequest createPaymentRequest);
    boolean isRequestValidForReversal(ReversePaymentRequest reversePaymentRequest);
    boolean isIdValid(Long id);
}
