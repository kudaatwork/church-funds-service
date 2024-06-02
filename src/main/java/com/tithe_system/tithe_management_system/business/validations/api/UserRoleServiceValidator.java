package com.tithe_system.tithe_management_system.business.validations.api;

import com.tithe_system.tithe_management_system.utils.requests.CreateUserRoleRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditUserRoleRequest;

public interface UserRoleServiceValidator {
    boolean isRequestValidForCreation(CreateUserRoleRequest createUserRoleRequest);
    boolean isRequestValidForEditing(EditUserRoleRequest editUserRoleRequest);
    boolean isIdValid(Long id);
}
