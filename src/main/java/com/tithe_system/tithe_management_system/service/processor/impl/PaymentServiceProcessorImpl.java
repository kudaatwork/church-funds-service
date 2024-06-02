package com.tithe_system.tithe_management_system.service.processor.impl;

import com.tithe_system.tithe_management_system.business.logic.api.PaymentService;
import com.tithe_system.tithe_management_system.service.processor.api.PaymentServiceProcessor;
import com.tithe_system.tithe_management_system.utils.requests.CreatePaymentRequest;
import com.tithe_system.tithe_management_system.utils.requests.ReversePaymentRequest;
import com.tithe_system.tithe_management_system.utils.responses.PaymentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Locale;

public class PaymentServiceProcessorImpl implements PaymentServiceProcessor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PaymentService paymentService;

    public PaymentServiceProcessorImpl(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public PaymentResponse create(CreatePaymentRequest createPaymentRequest, String username, Locale locale) {

        logger.info("Incoming request to create a payment : {}", createPaymentRequest);
        PaymentResponse paymentResponse = paymentService.create(createPaymentRequest, username, locale);
        logger.info("Outgoing response after creating a payment : {}", paymentResponse);

        return paymentResponse;
    }

    @Override
    public PaymentResponse reverse(ReversePaymentRequest reversePaymentRequest, String username, Locale locale) {

        logger.info("Incoming request to reverse a payment : {}", reversePaymentRequest);
        PaymentResponse paymentResponse = paymentService.reverse(reversePaymentRequest, username, locale);
        logger.info("Outgoing response after reversing a payment : {}", paymentResponse);

        return paymentResponse;
    }

    @Override
    public PaymentResponse findById(Long id, Locale locale) {

        logger.info("Incoming request to find a payment by id : {}", id);
        PaymentResponse paymentResponse = paymentService.findById(id, locale);
        logger.info("Outgoing response after finding a payment by id : {}", paymentResponse);

        return paymentResponse;
    }

    @Override
    public PaymentResponse findPaymentsAsPages(int page, int size, Locale locale, String username) {

        logger.info("Incoming request to find payments as pages");
        PaymentResponse paymentResponse = paymentService.findPaymentsAsPages(page, size, locale, username);
        logger.info("Outgoing response after finding payments as pages: {}", paymentResponse.getMessage());

        return paymentResponse;
    }
}
