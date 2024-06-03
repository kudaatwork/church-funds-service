package com.tithe_system.tithe_management_system.utils.requests;

public class CreateAccountRequest {
    private String accountNumber;
    private String name;
    private String currency;
    private Long assemblyId;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getAssemblyId() {
        return assemblyId;
    }

    public void setAssemblyId(Long assemblyId) {
        this.assemblyId = assemblyId;
    }

    @Override
    public String toString() {
        return "CreateAccountRequest{" +
                "accountNumber='" + accountNumber + '\'' +
                ", name='" + name + '\'' +
                ", currency='" + currency + '\'' +
                ", assemblyId=" + assemblyId +
                '}';
    }
}
