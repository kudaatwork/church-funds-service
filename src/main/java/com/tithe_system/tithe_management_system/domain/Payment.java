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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    @Lob
    @Column(name = "pop_Url", columnDefinition = "longblob")
    private byte[] popUrl;
    @Column(name = "pop_Name")
    private String popName;
    private String transactionReference;
    private String accountNumber;
    private String narration;
    @Enumerated(value = EnumType.STRING)
    private PaymentChannel paymentChannel;
    @Enumerated(value = EnumType.STRING)
    private PaymentType paymentType;
    @Enumerated(value = EnumType.STRING)
    private Currency currency;
    @Enumerated(value = EnumType.STRING)
    private PaymentMethod paymentMethod;
    @Enumerated(value = EnumType.STRING)
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public byte[] getPopUrl() {
        return popUrl;
    }

    public void setPopUrl(byte[] popUrl) {
        this.popUrl = popUrl;
    }

    public String getPopName() {
        return popName;
    }

    public void setPopName(String popName) {
        this.popName = popName;
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

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
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
                ", amount=" + amount +
                ", popName='" + popName + '\'' +
                ", transactionReference='" + transactionReference + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", narration='" + narration + '\'' +
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
