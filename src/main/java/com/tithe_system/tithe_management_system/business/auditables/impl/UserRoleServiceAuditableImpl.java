package com.tithe_system.tithe_management_system.business.auditables.impl;

import com.tithe_system.tithe_management_system.business.auditables.api.UserRoleServiceAuditable;
import com.tithe_system.tithe_management_system.domain.UserRole;
import com.tithe_system.tithe_management_system.repository.UserRoleRepository;
import java.util.Locale;

public class UserRoleServiceAuditableImpl implements UserRoleServiceAuditable {

    private final UserRoleRepository userRoleRepository;

    public UserRoleServiceAuditableImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserRole create(UserRole userRole, Locale locale, String username) {
        return null;
    }

    @Override
    public UserRole edit(UserRole userRole, Locale locale, String username) {
        return null;
    }

    @Override
    public UserRole delete(UserRole userRole, Locale locale) {
        return null;
    }
}
