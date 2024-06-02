package com.tithe_system.tithe_management_system.business.validations.api;

import com.tithe_system.tithe_management_system.utils.requests.CreateUserAccountRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditUserAccountRequest;

public interface UserAccountServiceValidator {
    boolean isRequestValidForCreation(CreateUserAccountRequest createUserAccountRequest);
    boolean isRequestValidForEditing(EditUserAccountRequest editUserAccountRequest);
    boolean isIdValid(Long id);
}
