package com.tithe_system.tithe_management_system.utils.requests;

import java.math.BigDecimal;

public class UpdateAccountRequest {
    private Long id;
    private String accountNumber;
    private BigDecimal amount;
    private String currency;
    private String narration;
    private String transactionReference;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    @Override
    public String toString() {
        return "UpdateAccountRequest{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", narration='" + narration + '\'' +
                ", transactionReference='" + transactionReference + '\'' +
                '}';
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

}
