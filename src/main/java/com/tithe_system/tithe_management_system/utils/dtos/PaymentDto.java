package com.tithe_system.tithe_management_system.utils.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tithe_system.tithe_management_system.domain.Currency;
import com.tithe_system.tithe_management_system.domain.EntityStatus;
import com.tithe_system.tithe_management_system.domain.Narration;
import com.tithe_system.tithe_management_system.domain.PaymentChannel;
import com.tithe_system.tithe_management_system.domain.PaymentMethod;
import com.tithe_system.tithe_management_system.domain.PaymentStatus;
import com.tithe_system.tithe_management_system.domain.PaymentType;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentDto {
    private Long id;
    private String amount;
    private String accountNumber;
    private String transactionReference;
    private Currency currency;
    private Narration narration;
    private PaymentMethod paymentMethod;
    private PaymentChannel paymentChannel;
    private PaymentType paymentType;
    private PaymentStatus paymentStatus;
    private AssemblyDto assemblyDto;
    private LocalDateTime dateCreated;
    private LocalDateTime dateLastModified;
    private EntityStatus entityStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Narration getNarration() {
        return narration;
    }

    public void setNarration(Narration narration) {
        this.narration = narration;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
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

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public AssemblyDto getAssemblyDto() {
        return assemblyDto;
    }

    public void setAssemblyDto(AssemblyDto assemblyDto) {
        this.assemblyDto = assemblyDto;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getDateLastModified() {
        return dateLastModified;
    }

    public void setDateLastModified(LocalDateTime dateLastModified) {
        this.dateLastModified = dateLastModified;
    }

    public EntityStatus getEntityStatus() {
        return entityStatus;
    }

    public void setEntityStatus(EntityStatus entityStatus) {
        this.entityStatus = entityStatus;
    }

    @Override
    public String toString() {
        return "PaymentDto{" +
                "id=" + id +
                ", amount='" + amount + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", transactionReference='" + transactionReference + '\'' +
                ", currency=" + currency +
                ", narration=" + narration +
                ", paymentMethod=" + paymentMethod +
                ", paymentChannel=" + paymentChannel +
                ", paymentType=" + paymentType +
                ", paymentStatus=" + paymentStatus +
                ", assemblyDto=" + assemblyDto +
                ", dateCreated=" + dateCreated +
                ", dateLastModified=" + dateLastModified +
                ", entityStatus=" + entityStatus +
                '}';
    }
}
