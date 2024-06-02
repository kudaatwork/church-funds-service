package com.tithe_system.tithe_management_system.service.processor.impl;

import com.tithe_system.tithe_management_system.business.logic.api.UserRoleService;
import com.tithe_system.tithe_management_system.service.processor.api.UserRoleServiceProcessor;
import com.tithe_system.tithe_management_system.utils.requests.CreateUserRoleRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditUserRoleRequest;
import com.tithe_system.tithe_management_system.utils.responses.UserRoleResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Locale;

public class UserRoleServiceProcessorImpl implements UserRoleServiceProcessor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserRoleService userRoleService;

    public UserRoleServiceProcessorImpl(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Override
    public UserRoleResponse create(CreateUserRoleRequest createUserRoleRequest, String username, Locale locale) {

        logger.info("Incoming request to create a user role: {} ", createUserRoleRequest);
        UserRoleResponse userRoleResponse = userRoleService.create(createUserRoleRequest, username, locale);
        logger.info("Outgoing response after creating a user role : {}", userRoleResponse);

        return userRoleResponse;
    }

    @Override
    public UserRoleResponse edit(EditUserRoleRequest editUserRoleRequest, String username, Locale locale) {

        logger.info("Incoming request to create a user role: {} ", editUserRoleRequest);
        UserRoleResponse userRoleResponse = userRoleService.edit(editUserRoleRequest, username, locale);
        logger.info("Outgoing response after editing a user role : {}", userRoleResponse);

        return userRoleResponse;
    }

    @Override
    public UserRoleResponse delete(Long id, Locale locale) {

        logger.info("Incoming request to delete a user role: {} ", id);
        UserRoleResponse userRoleResponse = userRoleService.delete(id, locale);
        logger.info("Outgoing response after deleting a user role : {}", userRoleResponse);

        return userRoleResponse;
    }

    @Override
    public UserRoleResponse findById(Long id, Locale locale) {

        logger.info("Incoming request to find a user role by id : {} ", id);
        UserRoleResponse userRoleResponse = userRoleService.delete(id, locale);
        logger.info("Outgoing response after finding a user role by id : {}", userRoleResponse);

        return userRoleResponse;
    }

    @Override
    public UserRoleResponse findAllAsAList(String username, Locale locale) {

        logger.info("Incoming request to find user roles as a list");
        UserRoleResponse userRoleResponse = userRoleService.findAllAsAList(username, locale);
        logger.info("Outgoing response after finding user roles as a list: {}", userRoleResponse);

        return userRoleResponse;
    }

    @Override
    public UserRoleResponse findAllAsPages(int page, int size, Locale locale, String username) {

        logger.info("Incoming request to find user roles as pages");
        UserRoleResponse userRoleResponse = userRoleService.findAllAsPages(page, size, locale, username);
        logger.info("Outgoing response after finding user roles as pages: {}", userRoleResponse);

        return userRoleResponse;
    }
}
