package com.tithe_system.tithe_management_system.business.validations.impl;

import com.tithe_system.tithe_management_system.business.validations.api.UserGroupServiceValidator;
import com.tithe_system.tithe_management_system.utils.requests.AssignUserRoleToUserGroupRequest;
import com.tithe_system.tithe_management_system.utils.requests.CreateUserGroupRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditUserGroupRequest;
import com.tithe_system.tithe_management_system.utils.requests.RemoveUserRolesFromUserGroupRequest;
import com.tithe_system.tithe_management_system.utils.requests.UserGroupMultipleFiltersRequest;

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

    @Override
    public boolean isRequestValidToAssignUserRolesToUserGroup(AssignUserRoleToUserGroupRequest
                                                                          assignUserRoleToUserGroupRequest) {

        if(assignUserRoleToUserGroupRequest == null){
            return false;
        }

        if(assignUserRoleToUserGroupRequest.getUserGroupId() == null ||
                assignUserRoleToUserGroupRequest.getUserGroupId() < 1){
            return false;
        }

        if(assignUserRoleToUserGroupRequest.getUserRoleIds() == null ||
                assignUserRoleToUserGroupRequest.getUserRoleIds().isEmpty()){
            return false;
        }

        for (Long userRoleId: assignUserRoleToUserGroupRequest.getUserRoleIds()) {

            if (userRoleId < 1) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isRequestValidToRemoveUserRolesFromUserGroup(RemoveUserRolesFromUserGroupRequest removeUserRolesFromUserGroupRequest) {

        if(removeUserRolesFromUserGroupRequest == null){
            return false;
        }

        if(removeUserRolesFromUserGroupRequest.getUserGroupId() == null ||
                removeUserRolesFromUserGroupRequest.getUserGroupId() < 1){
            return false;
        }

        if(removeUserRolesFromUserGroupRequest.getUserRoleIds() == null ||
                removeUserRolesFromUserGroupRequest.getUserRoleIds().isEmpty()){
            return false;
        }

        for (Long userRoleId: removeUserRolesFromUserGroupRequest.getUserRoleIds()) {

            if (userRoleId < 1) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isRequestValidToRetrieveUserGroupsByMultipleFilters(UserGroupMultipleFiltersRequest userGroupMultipleFiltersRequest) {

        if (userGroupMultipleFiltersRequest == null){
            return false;
        }

        if (userGroupMultipleFiltersRequest.getPage() < 0) {
            return false;
        }

        return userGroupMultipleFiltersRequest.getSize() > 0;
    }

    @Override
    public boolean isStringValid(String stringValue) {
        return stringValue != null && !stringValue.isEmpty();
    }
}
