package com.tithe_system.tithe_management_system.domain;

public enum PaymentType {

    TITHE("TITHE"), OFFERING("OFFERING"), FIRST_FRUIT("FIRST_FRUIT"), SEED("SEED");
    private final String paymentType;

    PaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentType() {
        return paymentType;
    }
}
