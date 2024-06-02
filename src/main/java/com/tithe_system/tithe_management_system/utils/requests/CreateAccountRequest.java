package com.tithe_system.tithe_management_system.utils.requests;

public class CreateAccountRequest {
    private String accountNumber;
    private String name;
    private String currency;
    private Long assemblyId;
    private Long userAccountId;

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

    public Long getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(Long userAccountId) {
        this.userAccountId = userAccountId;
    }

    @Override
    public String toString() {
        return "CreateAccountRequest{" +
                "accountNumber='" + accountNumber + '\'' +
                ", name='" + name + '\'' +
                ", currency='" + currency + '\'' +
                ", assemblyId=" + assemblyId +
                ", userAccountId=" + userAccountId +
                '}';
    }
}
