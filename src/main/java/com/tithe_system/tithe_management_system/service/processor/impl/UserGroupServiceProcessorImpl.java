package com.tithe_system.tithe_management_system.service.processor.impl;

import com.tithe_system.tithe_management_system.business.logic.api.UserGroupService;
import com.tithe_system.tithe_management_system.service.processor.api.UserGroupServiceProcessor;
import com.tithe_system.tithe_management_system.utils.requests.AssignUserRoleToUserGroupRequest;
import com.tithe_system.tithe_management_system.utils.requests.CreateUserGroupRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditUserGroupRequest;
import com.tithe_system.tithe_management_system.utils.requests.RemoveUserRolesFromUserGroupRequest;
import com.tithe_system.tithe_management_system.utils.requests.UserGroupMultipleFiltersRequest;
import com.tithe_system.tithe_management_system.utils.responses.UserGroupResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Locale;

public class UserGroupServiceProcessorImpl implements UserGroupServiceProcessor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserGroupService userGroupService;

    public UserGroupServiceProcessorImpl(UserGroupService userGroupService) {
        this.userGroupService = userGroupService;
    }

    @Override
    public UserGroupResponse create(CreateUserGroupRequest createUserGroupRequest, String username, Locale locale) {

        logger.info("Incoming request to create a user group: {} ", createUserGroupRequest);
        UserGroupResponse userGroupResponse = userGroupService.create(createUserGroupRequest, username, locale);
        logger.info("Outgoing response after creating a user group : {}", userGroupResponse);

        return userGroupResponse;
    }

    @Override
    public UserGroupResponse edit(EditUserGroupRequest editUserGroupRequest, String username, Locale locale) {

        logger.info("Incoming request to edit a user group: {} ", editUserGroupRequest);
        UserGroupResponse userGroupResponse = userGroupService.edit(editUserGroupRequest, username, locale);
        logger.info("Outgoing response after editing a user group : {}", userGroupResponse);

        return userGroupResponse;
    }

    @Override
    public UserGroupResponse delete(Long id, Locale locale) {

        logger.info("Incoming request to delete a user group by id: {} ", id);
        UserGroupResponse userGroupResponse = userGroupService.delete(id, locale);
        logger.info("Outgoing response after deleting a user group : {}", userGroupResponse);

        return userGroupResponse;
    }

    @Override
    public UserGroupResponse findById(Long id, Locale locale) {

        logger.info("Incoming request to find a user group by id: {} ", id);
        UserGroupResponse userGroupResponse = userGroupService.delete(id, locale);
        logger.info("Outgoing response after finding a user group : {}", userGroupResponse);

        return userGroupResponse;
    }

    @Override
    public UserGroupResponse findAllAsAList(String username, Locale locale) {

        logger.info("Incoming request to find user groups as a list");
        UserGroupResponse userGroupResponse = userGroupService.findAllAsAList(username, locale);
        logger.info("Outgoing response after finding user groups as a list: {}", userGroupResponse);

        return userGroupResponse;
    }

    @Override
    public UserGroupResponse findByMultipleFilters(UserGroupMultipleFiltersRequest userGroupMultipleFiltersRequest, Locale
            locale, String username) {

        logger.info("Incoming request to find user groups as pages");
        UserGroupResponse userGroupResponse = userGroupService.findByMultipleFilters(userGroupMultipleFiltersRequest,
                locale, username);
        logger.info("Outgoing response after finding user groups as pages: {}", userGroupResponse);

        return userGroupResponse;
    }

    @Override
    public UserGroupResponse assignUserRoleToUserGroup(AssignUserRoleToUserGroupRequest assignUserRoleToUserGroupRequest,
                                                       Locale locale, String username) {

        logger.info("Incoming request to assign user roles to a user group: {} ", assignUserRoleToUserGroupRequest);
        UserGroupResponse userGroupResponse = userGroupService.assignUserRoleToUserGroup(assignUserRoleToUserGroupRequest,
                locale, username);
        logger.info("Outgoing response after assigning user roles to a user group : {}", userGroupResponse);

        return userGroupResponse;
    }

    @Override
    public UserGroupResponse removeUserRolesFromUserGroup(RemoveUserRolesFromUserGroupRequest removeUserRolesFromUserGroupRequest, Locale locale, String username) {

        logger.info("Incoming request to remove user roles from a user group: {} ", removeUserRolesFromUserGroupRequest);
        UserGroupResponse userGroupResponse = userGroupService.removeUserRolesFromUserGroup(removeUserRolesFromUserGroupRequest,
                locale, username);
        logger.info("Outgoing response after removing user roles from a user group : {}", userGroupResponse);

        return userGroupResponse;
    }
}
