package com.tithe_system.tithe_management_system.domain;

public enum PaymentStatus {
    APPROVED("APPROVED"), INITIATED("INITIATED"), REVERSED("REVERSED"), REJECTED("REJECTED"),
    REVERSAL("REVERSAL");
    private final String paymentStatus;

    PaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }
}
