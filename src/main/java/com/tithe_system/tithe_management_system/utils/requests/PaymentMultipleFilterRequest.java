package com.tithe_system.tithe_management_system.utils.requests;

import java.util.List;

public class PaymentMultipleFilterRequest extends DataTableRequest{
    private String transactionReference;
    private String accountNumber;
    private List<String> narration;
    private List<String> paymentChannel;
    private List<String> paymentType;
    private List<String> currency;
    private List<String> paymentMethod;
    private List<String> paymentStatus;

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public List<String> getNarration() {
        return narration;
    }

    public void setNarration(List<String> narration) {
        this.narration = narration;
    }

    public List<String> getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(List<String> paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public List<String> getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(List<String> paymentType) {
        this.paymentType = paymentType;
    }

    public List<String> getCurrency() {
        return currency;
    }

    public void setCurrency(List<String> currency) {
        this.currency = currency;
    }

    public List<String> getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(List<String> paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<String> getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(List<String> paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return "PaymentMultipleFilterRequest{" +
                "transactionReference='" + transactionReference + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", narration=" + narration +
                ", paymentChannel=" + paymentChannel +
                ", paymentType=" + paymentType +
                ", currency=" + currency +
                ", paymentMethod=" + paymentMethod +
                ", paymentStatus=" + paymentStatus +
                '}';
    }
}
