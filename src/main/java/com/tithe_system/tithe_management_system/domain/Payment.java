package com.tithe_system.tithe_management_system.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String amount;
    private String popUrl;
    private String transactionReference;
    private String accountNumber;
    private PaymentChannel paymentChannel;
    @Enumerated(value = EnumType.STRING)
    private PaymentType paymentType;
    @Enumerated(value = EnumType.STRING)
    private Currency currency;
    private PaymentMethod paymentMethod;
    private PaymentStatus paymentStatus;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "assembly_id", referencedColumnName = "id")
    private Assembly assembly;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_account_id", referencedColumnName = "id")
    private UserAccount userAccount;
    private LocalDateTime dateCreated;
    private LocalDateTime dateLastModified;
    @Column(name = "entity_status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private EntityStatus entityStatus;

    @PreUpdate
    public void update(){
        dateLastModified = LocalDateTime.now();
    }

    @PrePersist
    public void create(){
        dateCreated = LocalDateTime.now();
        entityStatus = EntityStatus.ACTIVE;
    }

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

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Assembly getAssembly() {
        return assembly;
    }

    public void setAssembly(Assembly assembly) {
        this.assembly = assembly;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
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
        return "Payment{" +
                "id=" + id +
                ", amount='" + amount + '\'' +
                ", popUrl='" + popUrl + '\'' +
                ", transactionReference='" + transactionReference + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", paymentChannel=" + paymentChannel +
                ", paymentType=" + paymentType +
                ", currency=" + currency +
                ", paymentMethod=" + paymentMethod +
                ", paymentStatus=" + paymentStatus +
                ", assembly=" + assembly +
                ", userAccount=" + userAccount +
                ", dateCreated=" + dateCreated +
                ", dateLastModified=" + dateLastModified +
                ", entityStatus=" + entityStatus +
                '}';
    }
}
