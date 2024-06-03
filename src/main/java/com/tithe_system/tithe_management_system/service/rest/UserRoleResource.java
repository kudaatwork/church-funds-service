package com.tithe_system.tithe_management_system.service.rest;

import com.tithe_system.tithe_management_system.service.processor.api.UserRoleServiceProcessor;
import com.tithe_system.tithe_management_system.utils.constants.Constants;
import com.tithe_system.tithe_management_system.utils.requests.CreateUserRoleRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditUserRoleRequest;
import com.tithe_system.tithe_management_system.utils.responses.UserRoleResponse;
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
@RequestMapping("/tithe-management/v1/user-role")
public class UserRoleResource {
    private final UserRoleServiceProcessor userRoleServiceProcessor;

    public UserRoleResource(UserRoleServiceProcessor userRoleServiceProcessor) {
        this.userRoleServiceProcessor = userRoleServiceProcessor;
    }

    @Operation(summary = "Create a user role")
    @PostMapping(value = "")
    public UserRoleResponse create(@Valid @RequestBody final CreateUserRoleRequest createUserRoleRequest,
                                   @Parameter(name = "Authorization", in = ParameterIn.HEADER,
                                            description = "Bearer token", required = true)
                                    String authenticationToken,
                                   @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                    @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                            defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return userRoleServiceProcessor.create(createUserRoleRequest, authenticationToken, locale);
    }

    @Operation(summary = "Edit a user role")
    @PutMapping(value = "")
    public UserRoleResponse edit(@Valid @RequestBody final EditUserRoleRequest editUserRoleRequest,
                                  @Parameter(name = "Authorization", in = ParameterIn.HEADER,
                                          description = "Bearer token", required = true)
                                  String authenticationToken,
                                  @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                  @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                          defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return userRoleServiceProcessor.edit(editUserRoleRequest, authenticationToken, locale);
    }

    @Operation(summary = "Delete a user group")
    @DeleteMapping(value = "/delete/id/{id}")
    public UserRoleResponse delete(@PathVariable("id") final Long id,
                                    @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                    @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                            defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return userRoleServiceProcessor.delete(id, locale);
    }

    @Operation(summary = "Find a user role by id")
    @GetMapping(value = "/id/{id}")
    public UserRoleResponse findById(@PathVariable("id") final Long id,
                                      @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                      @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                              defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return userRoleServiceProcessor.findById(id, locale);
    }

    @Operation(summary = "Find all user roles as a list")
    @GetMapping(value = "/list")
    public UserRoleResponse findAllAsAList(@Parameter(name = "Authorization", in = ParameterIn.HEADER,
            description = "Bearer token", required = true)
                                            String authenticationToken,
                                            @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                            @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                                    defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return userRoleServiceProcessor.findAllAsAList(authenticationToken, locale);
    }

    @Operation(summary = "Find all user roles as pages")
    @GetMapping(value = "/pages")
    public UserRoleResponse findAllAsPages(@Parameter(name = "Authorization", in = ParameterIn.HEADER,
            description = "Bearer token", required = true)
                                            String authenticationToken,
                                            @RequestParam(value = "page", defaultValue = "0") int page,
                                            @RequestParam(value = "size", defaultValue = "10") int size,
                                            @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                            @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                                    defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return userRoleServiceProcessor.findAllAsPages(page, size, locale, authenticationToken);
    }
}
