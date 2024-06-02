package com.tithe_system.tithe_management_system.business.auditables.api;

import com.tithe_system.tithe_management_system.domain.UserGroup;
import java.util.Locale;

public interface UserGroupServiceAuditable {
    UserGroup create(UserGroup userGroup, Locale locale, String username);
    UserGroup edit(UserGroup userGroup, Locale locale, String username);
    UserGroup delete(UserGroup userGroup, Locale locale);
}
