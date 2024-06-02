package com.tithe_system.tithe_management_system.business.auditables.impl;

import com.tithe_system.tithe_management_system.business.auditables.api.UserAccountServiceAuditable;
import com.tithe_system.tithe_management_system.domain.UserAccount;
import com.tithe_system.tithe_management_system.repository.UserAccountRepository;
import java.util.Locale;

public class UserAccountServiceAuditableImpl implements UserAccountServiceAuditable {

    private final UserAccountRepository userAccountRepository;

    public UserAccountServiceAuditableImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserAccount create(UserAccount userAccount, Locale locale, String username) {
        return userAccountRepository.save(userAccount);
    }

    @Override
    public UserAccount edit(UserAccount userAccount, Locale locale, String username) {
        return userAccountRepository.save(userAccount);
    }

    @Override
    public UserAccount delete(UserAccount userAccount, Locale locale) {
        return userAccountRepository.save(userAccount);
    }
}
