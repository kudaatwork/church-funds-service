package com.tithe_system.tithe_management_system.service.rest;

import com.tithe_system.tithe_management_system.service.processor.api.AssemblyServiceProcessor;
import com.tithe_system.tithe_management_system.utils.constants.Constants;
import com.tithe_system.tithe_management_system.utils.requests.CreateAssemblyRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditAssemblyRequest;
import com.tithe_system.tithe_management_system.utils.responses.AssemblyResponse;
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
@RequestMapping("/church-funds-management/v1/assembly")
public class AssemblyResource {
    private final AssemblyServiceProcessor assemblyServiceProcessor;

    public AssemblyResource(AssemblyServiceProcessor assemblyServiceProcessor) {
        this.assemblyServiceProcessor = assemblyServiceProcessor;
    }

    @Operation(summary = "Create an assembly")
    @PostMapping(value = "")
    public AssemblyResponse create(@Valid @RequestBody final CreateAssemblyRequest createAssemblyRequest,
                                   @Parameter(name = "Authorization", in = ParameterIn.HEADER,
                                           description = "Bearer token", required = true)
                                   String authenticationToken,
                                   @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                   @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                           defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return assemblyServiceProcessor.create(createAssemblyRequest, authenticationToken, locale);
    }

    @Operation(summary = "Edit an assembly")
    @PutMapping(value = "")
    public AssemblyResponse edit(@Valid @RequestBody final EditAssemblyRequest editAssemblyRequest,
                                 @Parameter(name = "Authorization", in = ParameterIn.HEADER,
                                         description = "Bearer token", required = true)
                                 String authenticationToken,
                                 @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                 @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                         defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return assemblyServiceProcessor.edit(editAssemblyRequest, authenticationToken, locale);
    }

    @Operation(summary = "Delete an assembly")
    @DeleteMapping(value = "/delete/id/{id}")
    public AssemblyResponse delete(@PathVariable("id") final Long id,
                                   @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                   @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                           defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return assemblyServiceProcessor.delete(id, locale);
    }

    @Operation(summary = "Find an assembly by id")
    @GetMapping(value = "/id/{id}")
    public AssemblyResponse findById(@PathVariable("id") final Long id,
                                     @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                     @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                             defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return assemblyServiceProcessor.findById(id, locale);
    }

    @Operation(summary = "Find assemblies by district id")
    @GetMapping(value = "/district-id/{id}")
    public AssemblyResponse findByDistrictId(@PathVariable("id") final Long id,
                                     @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                     @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                             defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return assemblyServiceProcessor.findByDistrictId(id, locale);
    }


    @Operation(summary = "Find all assemblies as a list")
    @GetMapping(value = "/list")
    public AssemblyResponse findAllAsAList(@Parameter(name = "Authorization", in = ParameterIn.HEADER,
            description = "Bearer token", required = true)
                                           String authenticationToken,
                                           @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                           @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                                   defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return assemblyServiceProcessor.findAllAsAList(authenticationToken, locale);
    }

    @Operation(summary = "Find all assemblies as pages")
    @GetMapping(value = "/pages")
    public AssemblyResponse findAllAsPages(@Parameter(name = "Authorization", in = ParameterIn.HEADER,
            description = "Bearer token", required = true)
                                           String authenticationToken,
                                           @RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "10") int size,
                                           @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                           @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                                   defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return assemblyServiceProcessor.findAllAsPages(page, size, locale, authenticationToken);
    }
}
