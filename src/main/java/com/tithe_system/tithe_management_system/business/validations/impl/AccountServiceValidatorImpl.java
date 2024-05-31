package com.tithe_system.tithe_management_system.business.validations.impl;

import com.tithe_system.tithe_management_system.business.validations.api.AccountServiceValidator;
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
        updateAccountRequest.getCurrency() == null || updateAccountRequest.getCurrency().isEmpty()) {
            return false;
        }

        return true;
    }
}
