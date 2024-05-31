package com.tithe_system.tithe_management_system.business.logic.api;

import com.tithe_system.tithe_management_system.utils.requests.CreatePaymentRequest;
import com.tithe_system.tithe_management_system.utils.requests.ReversePaymentRequest;
import com.tithe_system.tithe_management_system.utils.responses.PaymentResponse;
import java.util.Locale;

public interface PaymentService {
    PaymentResponse createPayment(CreatePaymentRequest createPaymentRequest, String username, Locale locale);
    PaymentResponse reversePayment(ReversePaymentRequest reversePaymentRequest, String username, Locale locale);
    PaymentResponse findByTransactionReference(String transactionReference, String username, Locale locale);
    PaymentResponse findById(String transactionReference, Locale locale);
    PaymentResponse findByPaymentStatus(String paymentStatus, String username, Locale locale);
    PaymentResponse findPaymentsAsPages(int page, int size, Locale locale, String username);
}
