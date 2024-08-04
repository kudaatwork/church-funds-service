package com.tithe_system.tithe_management_system.business.validations.api;

import com.tithe_system.tithe_management_system.utils.requests.AssignUserRoleToUserGroupRequest;
import com.tithe_system.tithe_management_system.utils.requests.CreateUserGroupRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditUserGroupRequest;
import com.tithe_system.tithe_management_system.utils.requests.RemoveUserRolesFromUserGroupRequest;

public interface UserGroupServiceValidator {
    boolean isRequestValidForCreation(CreateUserGroupRequest createUserGroupRequest);
    boolean isRequestValidForEditing(EditUserGroupRequest editUserGroupRequest);
    boolean isIdValid(Long id);
    boolean isRequestValidToAssignUserRolesToUserGroup(AssignUserRoleToUserGroupRequest assignUserRoleToUserGroupRequest);
    boolean isRequestValidToRemoveUserRolesFromUserGroup(RemoveUserRolesFromUserGroupRequest removeUserRolesFromUserGroupRequest);
}
