package com.tithe_system.tithe_management_system.domain;

public enum PaymentStatus {
    COMPLETED("COMPLETED"), INITIATED("INITIATED"), REVERSED("REVERSED");
    private final String paymentStatus;

    PaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }
}
