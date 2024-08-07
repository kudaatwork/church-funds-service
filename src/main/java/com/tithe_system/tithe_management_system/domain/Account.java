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
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountNumber;
    private String transactionReference;
    private BigDecimal debitBalance;
    private BigDecimal creditBalance;
    private BigDecimal cumulativeBalance;
    private BigDecimal transactionAmount;
    private String name;
    @Enumerated(value = EnumType.STRING)
    private Narration narration;
    @Enumerated(value = EnumType.STRING)
    private Currency currency;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "assembly_id", referencedColumnName = "id")
    private Assembly assembly;
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

    public BigDecimal getDebitBalance() {
        return debitBalance;
    }

    public void setDebitBalance(BigDecimal debitBalance) {
        this.debitBalance = debitBalance;
    }

    public BigDecimal getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(BigDecimal creditBalance) {
        this.creditBalance = creditBalance;
    }

    public BigDecimal getCumulativeBalance() {
        return cumulativeBalance;
    }

    public void setCumulativeBalance(BigDecimal cumulativeBalance) {
        this.cumulativeBalance = cumulativeBalance;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Narration getNarration() {
        return narration;
    }

    public void setNarration(Narration narration) {
        this.narration = narration;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Assembly getAssembly() {
        return assembly;
    }

    public void setAssembly(Assembly assembly) {
        this.assembly = assembly;
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
        return "Account{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", transactionReference='" + transactionReference + '\'' +
                ", debitBalance=" + debitBalance +
                ", creditBalance=" + creditBalance +
                ", cumulativeBalance=" + cumulativeBalance +
                ", transactionAmount=" + transactionAmount +
                ", name='" + name + '\'' +
                ", narration='" + narration + '\'' +
                ", currency=" + currency +
                ", assembly=" + assembly +
                ", dateCreated=" + dateCreated +
                ", dateLastModified=" + dateLastModified +
                ", entityStatus=" + entityStatus +
                '}';
    }
}
