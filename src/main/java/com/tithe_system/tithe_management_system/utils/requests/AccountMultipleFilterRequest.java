package com.tithe_system.tithe_management_system.utils.requests;

import java.util.List;

public class AccountMultipleFilterRequest extends DataTableRequest{
    private String accountNumber;
    private String transactionReference;
    private String name;
    private List<String> narration;
    private List<String> currency;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getNarration() {
        return narration;
    }

    public void setNarration(List<String> narration) {
        this.narration = narration;
    }

    public List<String> getCurrency() {
        return currency;
    }

    public void setCurrency(List<String> currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "AccountMultipleFilterRequest{" +
                "accountNumber='" + accountNumber + '\'' +
                ", transactionReference='" + transactionReference + '\'' +
                ", name='" + name + '\'' +
                ", narration=" + narration +
                ", currency=" + currency +
                '}';
    }
}
