package com.tithe_system.tithe_management_system.business.validations.api;

import com.tithe_system.tithe_management_system.utils.requests.ChangePaymentStatusRequest;
import com.tithe_system.tithe_management_system.utils.requests.CreatePaymentRequest;
import com.tithe_system.tithe_management_system.utils.requests.PaymentMultipleFilterRequest;
import com.tithe_system.tithe_management_system.utils.requests.ReversePaymentRequest;

import java.util.List;

public interface PaymentServiceValidator {
    boolean isRequestValidForCreation(CreatePaymentRequest createPaymentRequest);
    boolean isRequestValidForReversal(ReversePaymentRequest reversePaymentRequest);
    boolean isIdValid(Long id);
    boolean isRequestValidForChangingPaymentStatus(ChangePaymentStatusRequest changePaymentStatusRequest);
    boolean isRequestValidToRetrievePaymentsByMultipleFilters(PaymentMultipleFilterRequest paymentMultipleFilterRequest);
    boolean isStringValid(String stringValue);
    boolean isListValid(List<String> listValue);
}
