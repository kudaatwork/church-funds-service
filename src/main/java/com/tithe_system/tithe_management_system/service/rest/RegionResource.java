package com.tithe_system.tithe_management_system.service.rest;

import com.tithe_system.tithe_management_system.service.processor.api.RegionServiceProcessor;
import com.tithe_system.tithe_management_system.utils.constants.Constants;
import com.tithe_system.tithe_management_system.utils.requests.CreateRegionRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditRegionRequest;
import com.tithe_system.tithe_management_system.utils.requests.RegionMultipleFiltersRequest;
import com.tithe_system.tithe_management_system.utils.responses.RegionResponse;
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
@RequestMapping("/church-funds-management/v1/region")
public class RegionResource {
    private final RegionServiceProcessor regionServiceProcessor;

    public RegionResource(RegionServiceProcessor regionServiceProcessor) {
        this.regionServiceProcessor = regionServiceProcessor;
    }

    @Operation(summary = "Create a region")
    @PostMapping(value = "")
    public RegionResponse create(@Valid @RequestBody final CreateRegionRequest createRegionRequest,
                                 @Parameter(name = "Authorization", in = ParameterIn.HEADER,
                                           description = "Bearer token", required = true)
                                   String authenticationToken,
                                 @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                   @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                           defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return regionServiceProcessor.create(createRegionRequest, authenticationToken, locale);
    }

    @Operation(summary = "Edit a region")
    @PutMapping(value = "")
    public RegionResponse edit(@Valid @RequestBody final EditRegionRequest editRegionRequest,
                                 @Parameter(name = "Authorization", in = ParameterIn.HEADER,
                                         description = "Bearer token", required = true)
                                 String authenticationToken,
                                 @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                 @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                         defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return regionServiceProcessor.edit(editRegionRequest, authenticationToken, locale);
    }

    @Operation(summary = "Delete a region")
    @DeleteMapping(value = "/delete/id/{id}")
    public RegionResponse delete(@PathVariable("id") final Long id,
                                   @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                   @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                           defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return regionServiceProcessor.delete(id, locale);
    }

    @Operation(summary = "Find a region by id")
    @GetMapping(value = "/id/{id}")
    public RegionResponse findById(@PathVariable("id") final Long id,
                                     @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                     @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                             defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return regionServiceProcessor.findById(id, locale);
    }

    @Operation(summary = "Find all regions as a list")
    @GetMapping(value = "/list")
    public RegionResponse findAllAsAList(@Parameter(name = "Authorization", in = ParameterIn.HEADER,
            description = "Bearer token", required = true)
                                           String authenticationToken,
                                           @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                           @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                                   defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return regionServiceProcessor.findAllAsAList(authenticationToken, locale);
    }

    @Operation(summary = "Find all regions by multiple filters as pages")
    @GetMapping(value = "/pages")
    public RegionResponse findByMultipleFilters(@Valid @RequestBody RegionMultipleFiltersRequest regionMultipleFiltersRequest,
                                                @Parameter(name = "Authorization", in = ParameterIn.HEADER,
                                                    description = "Bearer token", required = true)
                                           String authenticationToken,
                                           @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                           @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                                   defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return regionServiceProcessor.findByMultipleFilters(regionMultipleFiltersRequest, locale, authenticationToken);
    }
}
