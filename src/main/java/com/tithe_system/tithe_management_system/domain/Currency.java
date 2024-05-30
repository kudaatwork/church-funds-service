package com.tithe_system.tithe_management_system.domain;

public enum Currency {
    USD("USD"), ZIG("ZIG"), RAND("RAND"), EUR("EUR");
    private final String currency;

    Currency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }
}
