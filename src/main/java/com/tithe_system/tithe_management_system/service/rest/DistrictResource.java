package com.tithe_system.tithe_management_system.service.rest;

import com.tithe_system.tithe_management_system.service.processor.api.DistrictServiceProcessor;
import com.tithe_system.tithe_management_system.utils.constants.Constants;
import com.tithe_system.tithe_management_system.utils.requests.CreateDistrictRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditDistrictRequest;
import com.tithe_system.tithe_management_system.utils.responses.DistrictResponse;
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
@RequestMapping("/church-funds-management/v1/district")
public class DistrictResource {
    private final DistrictServiceProcessor districtServiceProcessor;

    public DistrictResource(DistrictServiceProcessor districtServiceProcessor) {
        this.districtServiceProcessor = districtServiceProcessor;
    }

    @Operation(summary = "Create a district")
    @PostMapping(value = "")
    public DistrictResponse create(@Valid @RequestBody final CreateDistrictRequest createDistrictRequest,
                                   @Parameter(name = "Authorization", in = ParameterIn.HEADER,
                                           description = "Bearer token", required = true)
                                   String authenticationToken,
                                   @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                                     @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                                             defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return districtServiceProcessor.create(createDistrictRequest, authenticationToken, locale);
    }

    @Operation(summary = "Edit a district")
    @PutMapping(value = "")
    public DistrictResponse edit(@Valid @RequestBody final EditDistrictRequest editDistrictRequest,
                                   @Parameter(name = "Authorization", in = ParameterIn.HEADER,
                                           description = "Bearer token", required = true)
                                   String authenticationToken,
                                   @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                   @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                           defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return districtServiceProcessor.edit(editDistrictRequest, authenticationToken, locale);
    }

    @Operation(summary = "Delete a district")
    @DeleteMapping(value = "/delete/id/{id}")
    public DistrictResponse delete(@PathVariable("id") final Long id,
                                 @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                 @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                         defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return districtServiceProcessor.delete(id, locale);
    }

    @Operation(summary = "Find a district by id")
    @GetMapping(value = "/id/{id}")
    public DistrictResponse findById(@PathVariable("id") final Long id,
                                   @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                   @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                           defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return districtServiceProcessor.findById(id, locale);
    }

    @Operation(summary = "Find all districts as a list")
    @GetMapping(value = "/list")
    public DistrictResponse findAllAsAList(@Parameter(name = "Authorization", in = ParameterIn.HEADER,
                                        description = "Bearer token", required = true)
                                               String authenticationToken,
                                     @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                     @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                             defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return districtServiceProcessor.findAllAsAList(authenticationToken, locale);
    }

    @Operation(summary = "Find all districts as pages")
    @GetMapping(value = "/pages")
    public DistrictResponse findAllAsPages(@Parameter(name = "Authorization", in = ParameterIn.HEADER,
            description = "Bearer token", required = true)
                                           String authenticationToken,
                                           @RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "10") int size,
                                           @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                           @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                                   defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return districtServiceProcessor.findAllAsPages(page, size, locale, authenticationToken);
    }
}
