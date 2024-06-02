package com.tithe_system.tithe_management_system.business.validations.api;

import com.tithe_system.tithe_management_system.utils.requests.CreateAccountRequest;
import com.tithe_system.tithe_management_system.utils.requests.UpdateAccountRequest;

public interface AccountServiceValidator {
    boolean isUpdateRequestValid(UpdateAccountRequest updateAccountRequest);
    boolean isCreateRequestValid(CreateAccountRequest createAccountRequest);
    boolean isIdValid(Long id);
}
