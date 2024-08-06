package com.tithe_system.tithe_management_system.business.validations.api;

import com.tithe_system.tithe_management_system.utils.requests.CreateUserAccountRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditUserAccountRequest;
import com.tithe_system.tithe_management_system.utils.requests.UserAccountsMultipleFiltersRequest;
import com.tithe_system.tithe_management_system.utils.requests.UserGroupMultipleFiltersRequest;

import java.util.List;

public interface UserAccountServiceValidator {
    boolean isRequestValidForCreation(CreateUserAccountRequest createUserAccountRequest);
    boolean isRequestValidForEditing(EditUserAccountRequest editUserAccountRequest);
    boolean isIdValid(Long id);
    boolean isRequestValidToRetrieveUserAccountsByMultipleFilters(UserAccountsMultipleFiltersRequest
                                                                          userAccountsMultipleFiltersRequest);
    boolean isStringValid(String stringValue);
    boolean isListValid(List<String> listValue);
}
