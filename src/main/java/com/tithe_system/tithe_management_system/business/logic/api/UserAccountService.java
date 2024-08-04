package com.tithe_system.tithe_management_system.business.logic.api;

import com.tithe_system.tithe_management_system.utils.requests.CreateUserAccountRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditUserAccountRequest;
import com.tithe_system.tithe_management_system.utils.responses.UserAccountResponse;
import java.util.Locale;

public interface UserAccountService {
    UserAccountResponse create(CreateUserAccountRequest createUserAccountRequest, String username, Locale locale);
    UserAccountResponse edit(EditUserAccountRequest editUserAccountRequest, String username, Locale locale);
    UserAccountResponse delete(Long id, Locale locale);
    UserAccountResponse findById(Long id, Locale locale);
    UserAccountResponse findByByUserGroupId(Long id, Locale locale, int page, int size);
    UserAccountResponse findByByAssemblyId(Long id, Locale locale, int page, int size);
    UserAccountResponse findAllAsAList(String username, Locale locale);
    UserAccountResponse findAllAsPages(int page, int size, Locale locale, String username);
}
