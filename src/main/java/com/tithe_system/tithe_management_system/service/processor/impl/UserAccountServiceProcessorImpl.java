package com.tithe_system.tithe_management_system.service.processor.impl;

import com.tithe_system.tithe_management_system.business.logic.api.UserAccountService;
import com.tithe_system.tithe_management_system.service.processor.api.UserAccountServiceProcessor;
import com.tithe_system.tithe_management_system.utils.requests.CreateUserAccountRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditUserAccountRequest;
import com.tithe_system.tithe_management_system.utils.requests.UserAccountsMultipleFiltersRequest;
import com.tithe_system.tithe_management_system.utils.responses.UserAccountResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Locale;

public class UserAccountServiceProcessorImpl implements UserAccountServiceProcessor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserAccountService userAccountService;

    public UserAccountServiceProcessorImpl(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @Override
    public UserAccountResponse create(CreateUserAccountRequest createUserAccountRequest, String username, Locale locale) {

        logger.info("Incoming request to create a user account : {}", createUserAccountRequest);
        UserAccountResponse userAccountResponse = userAccountService.create(createUserAccountRequest, username, locale);
        logger.info("Outgoing response after creating a user account : {}", userAccountResponse);

        return userAccountResponse;
    }

    @Override
    public UserAccountResponse edit(EditUserAccountRequest editUserAccountRequest, String username, Locale locale) {

        logger.info("Incoming request to edit a user account : {}", editUserAccountRequest);
        UserAccountResponse userAccountResponse = userAccountService.edit(editUserAccountRequest, username, locale);
        logger.info("Outgoing response after editing a user account : {}", userAccountResponse);

        return userAccountResponse;
    }

    @Override
    public UserAccountResponse delete(Long id, Locale locale) {

        logger.info("Incoming request to delete a user account : {}", id);
        UserAccountResponse userAccountResponse = userAccountService.delete(id, locale);
        logger.info("Outgoing response after editing a user account : {}", userAccountResponse);

        return userAccountResponse;
    }

    @Override
    public UserAccountResponse findById(Long id, Locale locale) {

        logger.info("Incoming request to find a user account by id : {}", id);
        UserAccountResponse userAccountResponse = userAccountService.findById(id, locale);
        logger.info("Outgoing response after finding a user account by id : {}", userAccountResponse);

        return userAccountResponse;
    }

    @Override
    public UserAccountResponse findByByUserGroupId(Long id, Locale locale, int page, int size) {

        logger.info("Incoming request to find a user accounts as pages by user group id : {}", id);
        UserAccountResponse userAccountResponse = userAccountService.findByByUserGroupId(id, locale, page, size);
        logger.info("Outgoing response after finding a user accounts as pages by user group id : {}", userAccountResponse);

        return userAccountResponse;
    }

    @Override
    public UserAccountResponse findByByAssemblyId(Long id, Locale locale, int page, int size) {

        logger.info("Incoming request to find a user accounts as pages by user group id : {}", id);
        UserAccountResponse userAccountResponse = userAccountService.findByByAssemblyId(id, locale, page, size);
        logger.info("Outgoing response after finding a user accounts as pages by user group id : {}", userAccountResponse);

        return userAccountResponse;
    }

    @Override
    public UserAccountResponse findAllAsAList(String username, Locale locale) {

        logger.info("Incoming request to find user accounts as a list");
        UserAccountResponse userAccountResponse = userAccountService.findAllAsAList(username, locale);
        logger.info("Outgoing response after finding user accounts as a list: {}", userAccountResponse);

        return userAccountResponse;
    }

    @Override
    public UserAccountResponse findByMultipleFilters(UserAccountsMultipleFiltersRequest userAccountsMultipleFiltersRequest,
                                                     Locale locale, String username) {

        logger.info("Incoming request to find user accounts by multiple filters as pages: {}", userAccountsMultipleFiltersRequest );
        UserAccountResponse userAccountResponse = userAccountService.findByMultipleFilters(userAccountsMultipleFiltersRequest,
                locale, username);
        logger.info("Outgoing response after finding user accounts as pages: {}", userAccountResponse.getMessage());

        return userAccountResponse;
    }
}
