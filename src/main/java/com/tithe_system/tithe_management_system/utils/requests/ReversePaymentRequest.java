package com.tithe_system.tithe_management_system.utils.requests;

public class ReversePaymentRequest {
    private String narration;
    private String transactionReference;

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

    @Override
    public String toString() {
        return "ReversePaymentRequest{" +
                "narration='" + narration + '\'' +
                ", transactionReference='" + transactionReference + '\'' +
                '}';
    }
}
