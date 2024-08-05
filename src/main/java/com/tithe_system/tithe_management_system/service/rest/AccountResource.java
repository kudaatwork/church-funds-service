package com.tithe_system.tithe_management_system.service.rest;

import com.tithe_system.tithe_management_system.service.processor.api.AccountServiceProcessor;
import com.tithe_system.tithe_management_system.utils.constants.Constants;
import com.tithe_system.tithe_management_system.utils.requests.AccountMultipleFilterRequest;
import com.tithe_system.tithe_management_system.utils.responses.AccountResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Locale;

@RestController
@CrossOrigin
@RequestMapping("/church-funds-management/v1/account")
public class AccountResource {
    private final AccountServiceProcessor accountServiceProcessor;

    public AccountResource(AccountServiceProcessor accountServiceProcessor) {
        this.accountServiceProcessor = accountServiceProcessor;
    }

    @Operation(summary = "Find an account by id")
    @GetMapping(value = "/id/{id}")
    public AccountResponse findById(@PathVariable("id") final Long id,
                                    @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                     @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                             defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return accountServiceProcessor.findById(id, locale);
    }

    @Operation(summary = "Retrieve accounts by multiple filters as pages")
    @PostMapping(value = "/multiple-filters")
    public AccountResponse findByMultipleFilters(@Valid @RequestBody AccountMultipleFilterRequest accountMultipleFilterRequest,
                                            @Parameter(name = "Authorization", in = ParameterIn.HEADER,
                                            description = "Bearer token", required = true)
                                           String authenticationToken,
                                           @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                           @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                                   defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return accountServiceProcessor.findByMultipleFilters(accountMultipleFilterRequest, locale, authenticationToken);
    }
}
