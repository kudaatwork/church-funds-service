package com.tithe_system.tithe_management_system.business.auditables.impl;

import com.tithe_system.tithe_management_system.business.auditables.api.UserGroupServiceAuditable;
import com.tithe_system.tithe_management_system.domain.UserGroup;
import com.tithe_system.tithe_management_system.repository.UserGroupRepository;
import java.util.Locale;

public class UserGroupServiceAuditableImpl implements UserGroupServiceAuditable {

    private final UserGroupRepository userGroupRepository;

    public UserGroupServiceAuditableImpl(UserGroupRepository userGroupRepository) {
        this.userGroupRepository = userGroupRepository;
    }

    @Override
    public UserGroup create(UserGroup userGroup, Locale locale, String username) {
        return userGroupRepository.save(userGroup);
    }

    @Override
    public UserGroup edit(UserGroup userGroup, Locale locale, String username) {
        return userGroupRepository.save(userGroup);
    }

    @Override
    public UserGroup delete(UserGroup userGroup, Locale locale) {
        return userGroupRepository.save(userGroup);
    }
}
