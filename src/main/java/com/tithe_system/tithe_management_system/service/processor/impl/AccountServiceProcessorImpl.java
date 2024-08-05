package com.tithe_system.tithe_management_system.service.processor.impl;

import com.tithe_system.tithe_management_system.business.logic.api.AccountService;
import com.tithe_system.tithe_management_system.business.logic.api.AssemblyService;
import com.tithe_system.tithe_management_system.service.processor.api.AccountServiceProcessor;
import com.tithe_system.tithe_management_system.utils.requests.AccountMultipleFilterRequest;
import com.tithe_system.tithe_management_system.utils.requests.CreateAccountRequest;
import com.tithe_system.tithe_management_system.utils.requests.UpdateAccountRequest;
import com.tithe_system.tithe_management_system.utils.responses.AccountResponse;
import com.tithe_system.tithe_management_system.utils.responses.AssemblyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Locale;

public class AccountServiceProcessorImpl implements AccountServiceProcessor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AccountService accountService;

    public AccountServiceProcessorImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public AccountResponse createAccount(CreateAccountRequest createAccountRequest, String username, Locale locale) {
        return null;
    }

    @Override
    public AccountResponse updateAccount(UpdateAccountRequest updateAccountRequest, String username, Locale locale) {
        return null;
    }

    @Override
    public AccountResponse findById(Long id, Locale locale) {

        logger.info("Incoming id to find an account by id: {}", id);
        AccountResponse accountResponse = accountService.findById(id, locale);
        logger.info("Outgoing response after finding an account by id : {}", accountResponse);

        return accountResponse;
    }

    @Override
    public  AccountResponse findByMultipleFilters(
            AccountMultipleFilterRequest accountMultipleFilterRequest, Locale locale, String username) {

        logger.info("Incoming request to retrieve accounts by multiple filters: {}", accountMultipleFilterRequest);
        AccountResponse accountResponse = accountService.findByMultipleFilters(accountMultipleFilterRequest, locale, username);
        logger.info("Outgoing response after retrieving accounts by multiple filters : {}", accountResponse.getMessage());

        return accountResponse;
    }
}
