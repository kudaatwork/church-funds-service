package com.tithe_system.tithe_management_system.business.validations.impl;

import com.tithe_system.tithe_management_system.business.validations.api.AccountServiceValidator;
import com.tithe_system.tithe_management_system.utils.requests.AccountMultipleFilterRequest;
import com.tithe_system.tithe_management_system.utils.requests.CreateAccountRequest;
import com.tithe_system.tithe_management_system.utils.requests.UpdateAccountRequest;

import java.util.List;

public class AccountServiceValidatorImpl implements AccountServiceValidator {
    @Override
    public boolean isUpdateRequestValid(UpdateAccountRequest updateAccountRequest) {

        if (updateAccountRequest == null) {
            return false;
        }

        if (updateAccountRequest.getId() == null || updateAccountRequest.getId() < 1) {
            return false;
        }

        if (updateAccountRequest.getAccountNumber() == null || updateAccountRequest.getAccountNumber().isEmpty() ||
        updateAccountRequest.getCurrency() == null || updateAccountRequest.getCurrency().isEmpty() ||
        updateAccountRequest.getTransactionReference() == null || updateAccountRequest.getTransactionReference().isEmpty()) {
            return false;
        }

        return true;
    }

    @Override
    public boolean isCreateRequestValid(CreateAccountRequest createAccountRequest) {

        if (createAccountRequest == null) {
            return false;
        }

        if (createAccountRequest.getAssemblyId() == null ||
        createAccountRequest.getAssemblyId() < 1) {
            return false;
        }

        if (createAccountRequest.getName() == null || createAccountRequest.getName().isEmpty() ||
                    createAccountRequest.getCurrency() == null || createAccountRequest.getCurrency().isEmpty()) {
            return false;
        }

        return true;
    }

    @Override
    public boolean isIdValid(Long id) {

        return id != null && id >= 1;
    }

    @Override
    public boolean isRequestValidToRetrievePaymentsByMultipleFilters(AccountMultipleFilterRequest accountMultipleFilterRequest) {

        if (accountMultipleFilterRequest == null){
            return false;
        }

        if (accountMultipleFilterRequest.getPage() < 0) {
            return false;
        }

        return accountMultipleFilterRequest.getSize() > 0;
    }

    @Override
    public boolean isStringValid(String stringValue) {
        return stringValue != null && !stringValue.isEmpty();
    }

    @Override
    public boolean isListValid(List<String> listValue) {

        if (listValue == null) {
            return false;
        }

        return true;
    }
}
