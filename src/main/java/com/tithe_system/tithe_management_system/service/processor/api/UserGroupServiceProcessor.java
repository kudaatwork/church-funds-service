package com.tithe_system.tithe_management_system.service.processor.api;

import com.tithe_system.tithe_management_system.utils.requests.CreateUserGroupRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditUserGroupRequest;
import com.tithe_system.tithe_management_system.utils.responses.UserGroupResponse;
import java.util.Locale;

public interface UserGroupServiceProcessor {
    UserGroupResponse create(CreateUserGroupRequest createUserGroupRequest, String username, Locale locale);
    UserGroupResponse edit(EditUserGroupRequest editUserGroupRequest, String username, Locale locale);
    UserGroupResponse delete(Long id, Locale locale);
    UserGroupResponse findById(Long id, Locale locale);
    UserGroupResponse findAllAsAList(String username, Locale locale);
    UserGroupResponse findAllAsPages(int page, int size, Locale locale, String username);
}
