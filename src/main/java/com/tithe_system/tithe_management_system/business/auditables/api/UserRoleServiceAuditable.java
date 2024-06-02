package com.tithe_system.tithe_management_system.business.auditables.api;

import com.tithe_system.tithe_management_system.domain.UserRole;
import java.util.Locale;

public interface UserRoleServiceAuditable {
    UserRole create(UserRole userRole, Locale locale, String username);
    UserRole edit(UserRole userRole, Locale locale, String username);
    UserRole delete(UserRole userRole, Locale locale);
}
