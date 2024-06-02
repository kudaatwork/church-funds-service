package com.tithe_system.tithe_management_system.domain;

public enum AccountNarration {
    PAYMENT("PAYMENT"), REVERSAL("REVERSAL");
    private final String accountNarration;

    AccountNarration(String accountNarration) {
        this.accountNarration = accountNarration;
    }

    public String getAccountNarration() {
        return accountNarration;
    }
}
