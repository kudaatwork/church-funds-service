package com.tithe_system.tithe_management_system.utils.requests;

import java.math.BigDecimal;

public class ReversePaymentRequest {
    private String narration;
    private String transactionReference;
    private BigDecimal amount;

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "ReversePaymentRequest{" +
                "narration='" + narration + '\'' +
                ", transactionReference='" + transactionReference + '\'' +
                ", amount=" + amount +
                '}';
    }
}
