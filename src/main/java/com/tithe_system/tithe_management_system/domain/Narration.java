package com.tithe_system.tithe_management_system.domain;

public enum Narration {
    PAYMENT("PAYMENT"), REVERSAL("REVERSAL"), ACCOUNT_CREATION("ACCOUNT_CREATION"),
    ACCOUNT_IN_USE("ACCOUNT_IN_USE");
    private final String accountNarration;

    Narration(String accountNarration) {
        this.accountNarration = accountNarration;
    }

    public String getAccountNarration() {
        return accountNarration;
    }
}
