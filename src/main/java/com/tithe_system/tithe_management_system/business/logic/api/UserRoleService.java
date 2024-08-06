package com.tithe_system.tithe_management_system.business.logic.api;

import com.tithe_system.tithe_management_system.utils.requests.CreateUserGroupRequest;
import com.tithe_system.tithe_management_system.utils.requests.CreateUserRoleRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditUserGroupRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditUserRoleRequest;
import com.tithe_system.tithe_management_system.utils.requests.UserRoleMultipleFiltersRequest;
import com.tithe_system.tithe_management_system.utils.responses.UserGroupResponse;
import com.tithe_system.tithe_management_system.utils.responses.UserRoleResponse;

import java.util.Locale;

public interface UserRoleService {
    UserRoleResponse create(CreateUserRoleRequest createUserRoleRequest, String username, Locale locale);
    UserRoleResponse edit(EditUserRoleRequest editUserRoleRequest, String username, Locale locale);
    UserRoleResponse delete(Long id, Locale locale);
    UserRoleResponse findById(Long id, Locale locale);
    UserRoleResponse findAllAsAList(String username, Locale locale);
    UserRoleResponse findByMultipleFilters(UserRoleMultipleFiltersRequest userRoleMultipleFiltersRequest, Locale locale,
                                           String username);
}
