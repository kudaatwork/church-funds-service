package com.tithe_system.tithe_management_system.utils.requests;

import com.tithe_system.tithe_management_system.domain.PaymentChannel;
import com.tithe_system.tithe_management_system.domain.PaymentType;

public class CreatePaymentRequest {
    private String amount;
    private String popUrl;
    private String transactionReference;
    private PaymentChannel paymentChannel;
    private PaymentType paymentType;
    private Long assemblyId;
    private Long userAccountId;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPopUrl() {
        return popUrl;
    }

    public void setPopUrl(String popUrl) {
        this.popUrl = popUrl;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public PaymentChannel getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(PaymentChannel paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Long getAssemblyId() {
        return assemblyId;
    }

    public void setAssemblyId(Long assemblyId) {
        this.assemblyId = assemblyId;
    }

    public Long getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(Long userAccountId) {
        this.userAccountId = userAccountId;
    }

    @Override
    public String toString() {
        return "CreatePaymentRequest{" +
                "amount='" + amount + '\'' +
                ", popUrl='" + popUrl + '\'' +
                ", transactionReference='" + transactionReference + '\'' +
                ", paymentChannel=" + paymentChannel +
                ", paymentType=" + paymentType +
                ", assemblyId=" + assemblyId +
                ", userAccountId=" + userAccountId +
                '}';
    }
}
