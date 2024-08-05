package com.tithe_system.tithe_management_system.business.logic.api;

import com.tithe_system.tithe_management_system.utils.requests.AccountMultipleFilterRequest;
import com.tithe_system.tithe_management_system.utils.requests.CreateAccountRequest;
import com.tithe_system.tithe_management_system.utils.requests.PaymentMultipleFilterRequest;
import com.tithe_system.tithe_management_system.utils.requests.UpdateAccountRequest;
import com.tithe_system.tithe_management_system.utils.responses.AccountResponse;
import com.tithe_system.tithe_management_system.utils.responses.PaymentResponse;

import java.util.Locale;

public interface AccountService {
    AccountResponse createAccount(CreateAccountRequest createAccountRequest, String username, Locale locale);
    AccountResponse updateAccountRecords(UpdateAccountRequest updateAccountRequest, String username, Locale locale);
    AccountResponse findById(Long id, Locale locale);
    AccountResponse findByMultipleFilters(
            AccountMultipleFilterRequest accountMultipleFilterRequest, Locale locale, String username);
}
