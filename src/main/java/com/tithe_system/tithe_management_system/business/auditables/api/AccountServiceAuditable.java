package com.tithe_system.tithe_management_system.business.auditables.api;

import com.tithe_system.tithe_management_system.domain.Account;

import java.util.Locale;

public interface AccountServiceAuditable {
    Account create(Account assemblyAccount, Locale locale, String username);
    Account update(Account assemblyAccount, Locale locale, String username);
    Account delete(Account assemblyAccount, Locale locale);
}
