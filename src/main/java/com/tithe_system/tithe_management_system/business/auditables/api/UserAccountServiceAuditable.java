package com.tithe_system.tithe_management_system.business.auditables.api;

import com.tithe_system.tithe_management_system.domain.UserAccount;
import java.util.Locale;

public interface UserAccountServiceAuditable {
    UserAccount create(UserAccount userAccount, Locale locale, String username);
    UserAccount edit(UserAccount userAccount, Locale locale, String username);
    UserAccount delete(UserAccount userAccount, Locale locale);
}
