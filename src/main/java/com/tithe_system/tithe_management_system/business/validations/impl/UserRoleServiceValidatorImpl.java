package com.tithe_system.tithe_management_system.business.validations.impl;

import com.tithe_system.tithe_management_system.business.validations.api.UserRoleServiceValidator;
import com.tithe_system.tithe_management_system.utils.requests.CreateUserRoleRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditUserRoleRequest;
import com.tithe_system.tithe_management_system.utils.requests.UserRoleMultipleFiltersRequest;

public class UserRoleServiceValidatorImpl implements UserRoleServiceValidator {
    @Override
    public boolean isRequestValidForCreation(CreateUserRoleRequest createUserRoleRequest) {

        if (createUserRoleRequest == null) {
            return false;
        }

        if (createUserRoleRequest.getName() == null || createUserRoleRequest.getName().isEmpty()) {
            return false;
        }

        if (ProvinceServiceValidatorImpl.checkUsingIsDigitMethod(createUserRoleRequest.getName())) {
            return false;
        }

        return true;
    }

    @Override
    public boolean isRequestValidForEditing(EditUserRoleRequest editUserRoleRequest) {

        if (editUserRoleRequest == null) {
            return false;
        }

        if (editUserRoleRequest.getName().isEmpty()) {
            return false;
        }

        if (ProvinceServiceValidatorImpl.checkUsingIsDigitMethod(editUserRoleRequest.getName())) {
            return false;
        }

        return true;
    }

    @Override
    public boolean isIdValid(Long id) {
        return id != null && id > 1;
    }

    @Override
    public boolean isRequestValidToRetrieveUserRolesByMultipleFilters(UserRoleMultipleFiltersRequest userRoleMultipleFiltersRequest) {

        if (userRoleMultipleFiltersRequest == null){
            return false;
        }

        if (userRoleMultipleFiltersRequest.getPage() < 0) {
            return false;
        }

        return userRoleMultipleFiltersRequest.getSize() > 0;
    }

    @Override
    public boolean isStringValid(String stringValue) {
        return stringValue != null && !stringValue.isEmpty();
    }
}
