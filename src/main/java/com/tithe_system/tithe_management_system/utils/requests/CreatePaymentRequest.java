package com.tithe_system.tithe_management_system.utils.requests;

import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;

public class CreatePaymentRequest {
    private BigDecimal amount;
    private MultipartFile proofOfPayment;
    private String currency;
    private String paymentChannel;
    private String  paymentType;
    private String narration;
    private Long assemblyId;
    private Long userAccountId;
    private Long accountId;

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

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
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

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "CreatePaymentRequest{" +
                "amount=" + amount +
                ", proofOfPayment=" + proofOfPayment +
                ", currency='" + currency + '\'' +
                ", paymentChannel='" + paymentChannel + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", narration='" + narration + '\'' +
                ", assemblyId=" + assemblyId +
                ", userAccountId=" + userAccountId +
                ", accountId=" + accountId +
                '}';
    }
}
