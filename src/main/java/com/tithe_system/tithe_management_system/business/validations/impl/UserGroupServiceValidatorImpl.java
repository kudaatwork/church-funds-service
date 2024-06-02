package com.tithe_system.tithe_management_system.business.validations.impl;

import com.tithe_system.tithe_management_system.business.validations.api.UserGroupServiceValidator;
import com.tithe_system.tithe_management_system.utils.requests.CreateUserGroupRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditUserGroupRequest;

public class UserGroupServiceValidatorImpl implements UserGroupServiceValidator {
    @Override
    public boolean isRequestValidForCreation(CreateUserGroupRequest createUserGroupRequest) {

        if (createUserGroupRequest == null) {
            return false;
        }

        if (createUserGroupRequest.getName() == null || createUserGroupRequest.getName().isEmpty() ||
        createUserGroupRequest.getDescription() == null || createUserGroupRequest.getDescription().isEmpty()) {
            return false;
        }

        if (ProvinceServiceValidatorImpl.checkUsingIsDigitMethod(createUserGroupRequest.getName())) {
            return false;
        }

        return true;
    }

    @Override
    public boolean isRequestValidForEditing(EditUserGroupRequest editUserGroupRequest) {

        if (editUserGroupRequest == null) {
            return false;
        }

        if (editUserGroupRequest.getName().isEmpty() || editUserGroupRequest.getDescription().isEmpty()) {
            return false;
        }

        if (ProvinceServiceValidatorImpl.checkUsingIsDigitMethod(editUserGroupRequest.getName())) {
            return false;
        }

        return true;
    }

    @Override
    public boolean isIdValid(Long id) {
        return id != null && id > 1;
    }
}
