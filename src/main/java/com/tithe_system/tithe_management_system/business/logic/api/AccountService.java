package com.tithe_system.tithe_management_system.business.logic.api;

import com.tithe_system.tithe_management_system.utils.requests.UpdateAccountRequest;
import com.tithe_system.tithe_management_system.utils.responses.AccountResponse;
import java.util.Locale;

public interface AccountService {
    AccountResponse updateAccount(UpdateAccountRequest updateAccountRequest, String username, Locale locale);
}
