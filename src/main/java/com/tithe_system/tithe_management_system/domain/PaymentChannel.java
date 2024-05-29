package com.tithe_system.tithe_management_system.domain;

public enum PaymentChannel {
    ECO_CASH("ECO_CASH"), BANK("BANK"), CASH("CASH"), WORLD_REMIT("WORLD_REMIT"), MUKURU("MUKURU");
    private final String paymentChannel;

    PaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public String getPaymentChannel() {
        return paymentChannel;
    }
}
