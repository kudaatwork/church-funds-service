package com.tithe_system.tithe_management_system.service.rest;

import com.tithe_system.tithe_management_system.service.processor.api.ProvinceServiceProcessor;
import com.tithe_system.tithe_management_system.utils.constants.Constants;
import com.tithe_system.tithe_management_system.utils.requests.CreateProvinceRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditProvinceRequest;
import com.tithe_system.tithe_management_system.utils.responses.ProvinceResponse;
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
@RequestMapping("/church-funds-management/v1/province")
public class ProvinceResource {
    private final ProvinceServiceProcessor provinceServiceProcessor;

    public ProvinceResource(ProvinceServiceProcessor provinceServiceProcessor) {
        this.provinceServiceProcessor = provinceServiceProcessor;
    }

    @Operation(summary = "Create a province")
    @PostMapping(value = "")
    public ProvinceResponse create(@Valid @RequestBody final CreateProvinceRequest createProvinceRequest,
                                   @Parameter(name = "Authorization", in = ParameterIn.HEADER,
                                           description = "Bearer token", required = true)
                                   String authenticationToken,
                                   @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                   @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                           defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return provinceServiceProcessor.create(createProvinceRequest, authenticationToken, locale);
    }

    @Operation(summary = "Edit a province")
    @PutMapping(value = "")
    public ProvinceResponse edit(@Valid @RequestBody final EditProvinceRequest editProvinceRequest,
                                 @Parameter(name = "Authorization", in = ParameterIn.HEADER,
                                         description = "Bearer token", required = true)
                                 String authenticationToken,
                                 @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                 @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                         defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return provinceServiceProcessor.edit(editProvinceRequest, authenticationToken, locale);
    }

    @Operation(summary = "Delete a province")
    @DeleteMapping(value = "/delete/id/{id}")
    public ProvinceResponse delete(@PathVariable("id") final Long id,
                                   @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                   @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                           defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return provinceServiceProcessor.delete(id, locale);
    }

    @Operation(summary = "Find a province by id")
    @GetMapping(value = "/id/{id}")
    public ProvinceResponse findById(@PathVariable("id") final Long id,
                                     @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                     @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                             defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return provinceServiceProcessor.findById(id, locale);
    }

    @Operation(summary = "Find all provinces as a list")
    @GetMapping(value = "/list")
    public ProvinceResponse findAllAsAList(@Parameter(name = "Authorization", in = ParameterIn.HEADER,
                                            description = "Bearer token", required = true)
                                           String authenticationToken,
                                           @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                           @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                                   defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return provinceServiceProcessor.findAllAsAList(authenticationToken, locale);
    }

    @Operation(summary = "Find all provinces as pages")
    @GetMapping(value = "/pages")
    public ProvinceResponse findAllAsPages(@Parameter(name = "Authorization", in = ParameterIn.HEADER,
                                            description = "Bearer token", required = true)
                                           String authenticationToken,
                                           @RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "10") int size,
                                           @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                           @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                                   defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return provinceServiceProcessor.findAllAsPages(page, size, locale, authenticationToken);
    }
}
