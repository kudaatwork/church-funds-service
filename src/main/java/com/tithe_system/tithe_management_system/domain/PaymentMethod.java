package com.tithe_system.tithe_management_system.domain;

public enum PaymentMethod {
    MOBILE_MONEY("MOBILE_MONEY"), CASH("CASH"), BANK_TRANSFER("BANK_TRANSFER"), REMITTANCE("REMMITANCE");
    private final String paymentMethod;

    PaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
}
