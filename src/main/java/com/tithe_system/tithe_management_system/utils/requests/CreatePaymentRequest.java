package com.tithe_system.tithe_management_system.utils.requests;

import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;

public class CreatePaymentRequest {
    private BigDecimal amount;
    private MultipartFile proofOfPayment;
    private String currency;
    private String transactionReference;
    private String paymentChannel;
    private String  paymentType;
    private String paymentStatus;
    private Long assemblyId;
    private Long userAccountId;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public MultipartFile getProofOfPayment() {
        return proofOfPayment;
    }

    public void setProofOfPayment(MultipartFile proofOfPayment) {
        this.proofOfPayment = proofOfPayment;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public String getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(String paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
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
                "amount=" + amount +
                ", proofOfPayment=" + proofOfPayment +
                ", currency='" + currency + '\'' +
                ", transactionReference='" + transactionReference + '\'' +
                ", paymentChannel='" + paymentChannel + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", assemblyId=" + assemblyId +
                ", userAccountId=" + userAccountId +
                '}';
    }
}
