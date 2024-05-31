package com.tithe_system.tithe_management_system.utils.requests;

import java.math.BigDecimal;

public class ReversePaymentRequest {
    private Long paymentId;
    private String transactionReference;
    private BigDecimal amount;

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
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
                "paymentId=" + paymentId +
                ", transactionReference='" + transactionReference + '\'' +
                ", amount=" + amount +
                '}';
    }
}
