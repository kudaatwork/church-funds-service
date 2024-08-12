package com.tithe_system.tithe_management_system.service.rest;

import com.tithe_system.tithe_management_system.service.processor.api.UserAccountServiceProcessor;
import com.tithe_system.tithe_management_system.utils.constants.Constants;
import com.tithe_system.tithe_management_system.utils.requests.CreateUserAccountRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditUserAccountRequest;
import com.tithe_system.tithe_management_system.utils.requests.UserAccountsMultipleFiltersRequest;
import com.tithe_system.tithe_management_system.utils.responses.UserAccountResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Locale;

@RestController
@CrossOrigin
@RequestMapping("/tithe-management/v1/user-account")
public class UserAccountResource {
    private final UserAccountServiceProcessor userAccountServiceProcessor;

    public UserAccountResource(UserAccountServiceProcessor userAccountServiceProcessor) {
        this.userAccountServiceProcessor = userAccountServiceProcessor;
    }

    @Operation(summary = "Create a user account")
    @PostMapping(value = "")
    public UserAccountResponse create(@Valid @RequestBody final CreateUserAccountRequest createUserAccountRequest,
                                      @Parameter(name = "Authorization", in = ParameterIn.HEADER,
                                           description = "Bearer token", required = true)
                                   String authenticationToken,
                                      @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                   @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                           defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return userAccountServiceProcessor.create(createUserAccountRequest, authenticationToken, locale);
    }

    @Operation(summary = "Edit a user account")
    @PutMapping(value = "")
    public UserAccountResponse edit(@Valid @RequestBody final EditUserAccountRequest editUserAccountRequest,
                                 @Parameter(name = "Authorization", in = ParameterIn.HEADER,
                                         description = "Bearer token", required = true)
                                 String authenticationToken,
                                 @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                 @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                         defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return userAccountServiceProcessor.edit(editUserAccountRequest, authenticationToken, locale);
    }

    @Operation(summary = "Delete a user account")
    @DeleteMapping(value = "/delete/id/{id}")
    public UserAccountResponse delete(@PathVariable("id") final Long id,
                                   @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                   @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                           defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return userAccountServiceProcessor.delete(id, locale);
    }

    @Operation(summary = "Find a user account by id")
    @GetMapping(value = "/id/{id}")
    public UserAccountResponse findById(@PathVariable("id") final Long id,
                                     @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                     @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                             defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return userAccountServiceProcessor.findById(id, locale);
    }

    @Operation(summary = "Find a user accounts as pages by user group id")
    @GetMapping(value = "/user-group-id/{id}")
    public UserAccountResponse findByUserGroupId(@PathVariable("id") final Long id,
                                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                                 @RequestParam(value = "size", defaultValue = "10") int size,
                                        @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                        @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                                defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return userAccountServiceProcessor.findByByUserGroupId(id, locale, page, size);
    }

    @Operation(summary = "Find a user accounts as pages by assembly id")
    @GetMapping(value = "/assembly-id/{id}")
    public UserAccountResponse findByAssemblyId(@PathVariable("id") final Long id,
                                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                                 @RequestParam(value = "size", defaultValue = "10") int size,
                                                 @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                                 @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                                         defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return userAccountServiceProcessor.findByByAssemblyId(id, locale, page, size);
    }

    @Operation(summary = "Find all user accounts as a list")
    @GetMapping(value = "/list")
    public UserAccountResponse findAllAsAList(@Parameter(name = "Authorization", in = ParameterIn.HEADER,
            description = "Bearer token", required = true)
                                           String authenticationToken,
                                           @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                           @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                                   defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return userAccountServiceProcessor.findAllAsAList(authenticationToken, locale);
    }

    @Operation(summary = "Find all user accounts by multiple filters as pages")
    @PostMapping(value = "/multiple-filters")
    public UserAccountResponse findByMultipleFilters(@Valid @RequestBody UserAccountsMultipleFiltersRequest
            userAccountsMultipleFiltersRequest,
                                            @Parameter(name = "Authorization", in = ParameterIn.HEADER,
                                            description = "Bearer token", required = true)
                                           String authenticationToken,
                                           @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                           @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                                   defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return userAccountServiceProcessor.findByMultipleFilters(userAccountsMultipleFiltersRequest, locale, authenticationToken);
    }
}
