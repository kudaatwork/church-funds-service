package com.tithe_system.tithe_management_system.business.auditables.impl;

import com.tithe_system.tithe_management_system.business.auditables.api.AccountServiceAuditable;
import com.tithe_system.tithe_management_system.domain.Account;
import com.tithe_system.tithe_management_system.repository.AccountRepository;
import java.util.Locale;

public class AccountServiceAuditableImpl implements AccountServiceAuditable {
    private final AccountRepository accountRepository;

    public AccountServiceAuditableImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account create(Account assemblyAccount, Locale locale, String username) {
        return accountRepository.save(assemblyAccount);
    }

    @Override
    public Account update(Account assemblyAccount, Locale locale, String username) {
        return accountRepository.save(assemblyAccount);
    }
}
