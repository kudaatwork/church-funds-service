package com.tithe_system.tithe_management_system.service.processor.api;

import com.tithe_system.tithe_management_system.utils.requests.CreateAccountRequest;
import com.tithe_system.tithe_management_system.utils.requests.UpdateAccountRequest;
import com.tithe_system.tithe_management_system.utils.responses.AccountResponse;
import java.util.Locale;

public interface AccountServiceProcessor {
    AccountResponse createAccount(CreateAccountRequest createAccountRequest, String username, Locale locale);
    AccountResponse updateAccount(UpdateAccountRequest updateAccountRequest, String username, Locale locale);
    AccountResponse findById(Long id, Locale locale);
    AccountResponse findAllAsPages(int page, int size, Locale locale, String username);
}
