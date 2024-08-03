package com.tithe_system.tithe_management_system.utils.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tithe_system.tithe_management_system.domain.EntityStatus;
import com.tithe_system.tithe_management_system.domain.Narration;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDto {
    private Long id;
    private String accountNumber;
    private AssemblyDto assemblyDto;
    private BigDecimal debitBalance;
    private BigDecimal creditBalance;
    private BigDecimal cumulativeBalance;
    private String transactionReference;
    private BigDecimal transactionAmount;
    private String name;
    private Narration narration;
    private String currency;
    private UserAccountDto userAccountDto;
    private LocalDateTime dateCreated;
    private LocalDateTime dateLastModified;
    private EntityStatus entityStatus;

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

    public AssemblyDto getAssemblyDto() {
        return assemblyDto;
    }

    public void setAssemblyDto(AssemblyDto assemblyDto) {
        this.assemblyDto = assemblyDto;
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

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public UserAccountDto getUserAccountDto() {
        return userAccountDto;
    }

    public void setUserAccountDto(UserAccountDto userAccountDto) {
        this.userAccountDto = userAccountDto;
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
        return "AccountDto{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", assemblyDto=" + assemblyDto +
                ", debitBalance=" + debitBalance +
                ", creditBalance=" + creditBalance +
                ", cumulativeBalance=" + cumulativeBalance +
                ", transactionReference='" + transactionReference + '\'' +
                ", transactionAmount=" + transactionAmount +
                ", name='" + name + '\'' +
                ", narration=" + narration +
                ", currency='" + currency + '\'' +
                ", userAccountDto=" + userAccountDto +
                ", dateCreated=" + dateCreated +
                ", dateLastModified=" + dateLastModified +
                ", entityStatus=" + entityStatus +
                '}';
    }
}
