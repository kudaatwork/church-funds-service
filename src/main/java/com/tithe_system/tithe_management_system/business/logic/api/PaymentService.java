package com.tithe_system.tithe_management_system.business.logic.api;

import com.tithe_system.tithe_management_system.utils.requests.ChangePaymentStatusRequest;
import com.tithe_system.tithe_management_system.utils.requests.CreatePaymentRequest;
import com.tithe_system.tithe_management_system.utils.requests.ReversePaymentRequest;
import com.tithe_system.tithe_management_system.utils.responses.PaymentResponse;
import java.util.Locale;

public interface PaymentService {
    PaymentResponse create(CreatePaymentRequest createPaymentRequest, String username, Locale locale);
    PaymentResponse reverse(ReversePaymentRequest reversePaymentRequest, String username, Locale locale);
    PaymentResponse findById(Long id, Locale locale);
    PaymentResponse findPaymentsAsPages(int page, int size, Locale locale, String username);
    PaymentResponse changePaymentStatus(ChangePaymentStatusRequest changePaymentStatusRequest, String username,
                                        Locale locale);
}
