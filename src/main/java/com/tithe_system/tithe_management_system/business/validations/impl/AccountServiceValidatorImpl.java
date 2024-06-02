package com.tithe_system.tithe_management_system.business.validations.impl;

import com.tithe_system.tithe_management_system.business.validations.api.AccountServiceValidator;
import com.tithe_system.tithe_management_system.utils.requests.CreateAccountRequest;
import com.tithe_system.tithe_management_system.utils.requests.UpdateAccountRequest;

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

        if (createAccountRequest.getAssemblyId() == null || createAccountRequest.getUserAccountId() == null ||
        createAccountRequest.getAssemblyId() < 1 || createAccountRequest.getUserAccountId() < 1) {
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
}
