package com.tithe_system.tithe_management_system.service.rest;

import com.tithe_system.tithe_management_system.service.processor.api.UserGroupServiceProcessor;
import com.tithe_system.tithe_management_system.utils.constants.Constants;
import com.tithe_system.tithe_management_system.utils.requests.CreateUserGroupRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditUserGroupRequest;
import com.tithe_system.tithe_management_system.utils.responses.UserGroupResponse;
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
@RequestMapping("/tithe-management/v1/user-group")
public class UserGroupResource {
    private final UserGroupServiceProcessor userGroupServiceProcessor;

    public UserGroupResource(UserGroupServiceProcessor userGroupServiceProcessor) {
        this.userGroupServiceProcessor = userGroupServiceProcessor;
    }

    @Operation(summary = "Create a user group")
    @PostMapping(value = "")
    public UserGroupResponse create(@Valid @RequestBody final CreateUserGroupRequest createUserGroupRequest,
                                    @Parameter(name = "Authorization", in = ParameterIn.HEADER,
                                              description = "Bearer token", required = true)
                                      String authenticationToken,
                                    @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                      @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                              defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return userGroupServiceProcessor.create(createUserGroupRequest, authenticationToken, locale);
    }

    @Operation(summary = "Edit a user group")
    @PutMapping(value = "")
    public UserGroupResponse edit(@Valid @RequestBody final EditUserGroupRequest editUserGroupRequest,
                                    @Parameter(name = "Authorization", in = ParameterIn.HEADER,
                                            description = "Bearer token", required = true)
                                    String authenticationToken,
                                    @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                    @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                            defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return userGroupServiceProcessor.edit(editUserGroupRequest, authenticationToken, locale);
    }

    @Operation(summary = "Delete a user group")
    @DeleteMapping(value = "/delete/id/{id}")
    public UserGroupResponse delete(@PathVariable("id") final Long id,
                                      @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                      @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                              defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return userGroupServiceProcessor.delete(id, locale);
    }

    @Operation(summary = "Find a user account by id")
    @GetMapping(value = "/id/{id}")
    public UserGroupResponse findById(@PathVariable("id") final Long id,
                                        @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                        @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                                defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return userGroupServiceProcessor.findById(id, locale);
    }

    @Operation(summary = "Find all user groups as a list")
    @GetMapping(value = "/list")
    public UserGroupResponse findAllAsAList(@Parameter(name = "Authorization", in = ParameterIn.HEADER,
            description = "Bearer token", required = true)
                                              String authenticationToken,
                                              @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                              @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                                      defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return userGroupServiceProcessor.findAllAsAList(authenticationToken, locale);
    }

    @Operation(summary = "Find all user groups as pages")
    @GetMapping(value = "/pages")
    public UserGroupResponse findAllAsPages(@Parameter(name = "Authorization", in = ParameterIn.HEADER,
            description = "Bearer token", required = true)
                                              String authenticationToken,
                                              @RequestParam(value = "page", defaultValue = "0") int page,
                                              @RequestParam(value = "size", defaultValue = "10") int size,
                                              @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                              @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                                      defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return userGroupServiceProcessor.findAllAsPages(page, size, locale, authenticationToken);
    }
}
