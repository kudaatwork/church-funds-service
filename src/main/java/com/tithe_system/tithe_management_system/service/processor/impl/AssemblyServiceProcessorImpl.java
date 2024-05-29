package com.tithe_system.tithe_management_system.service.processor.impl;

import com.tithe_system.tithe_management_system.business.logic.api.AssemblyService;
import com.tithe_system.tithe_management_system.service.processor.api.AssemblyServiceProcessor;
import com.tithe_system.tithe_management_system.utils.requests.CreateAssemblyRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditAssemblyRequest;
import com.tithe_system.tithe_management_system.utils.responses.AssemblyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Locale;

public class AssemblyServiceProcessorImpl implements AssemblyServiceProcessor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AssemblyService assemblyService;

    public AssemblyServiceProcessorImpl(AssemblyService assemblyService) {
        this.assemblyService = assemblyService;
    }

    @Override
    public AssemblyResponse create(CreateAssemblyRequest createAssemblyRequest, String username, Locale locale) {

        logger.info("Incoming request to create an assembly : {}", createAssemblyRequest);
        AssemblyResponse assemblyResponse = assemblyService.create(createAssemblyRequest,
                username, locale);
        logger.info("Outgoing response after creating an assembly : {}", assemblyResponse);

        return assemblyResponse;
    }

    @Override
    public AssemblyResponse edit(EditAssemblyRequest editAssemblyRequest, String username, Locale locale) {

        logger.info("Incoming request to edit an assembly : {}", editAssemblyRequest);
        AssemblyResponse assemblyResponse = assemblyService.edit(editAssemblyRequest,
                username, locale);
        logger.info("Outgoing response after editing an assembly : {}", assemblyResponse);

        return assemblyResponse;
    }

    @Override
    public AssemblyResponse delete(Long id, Locale locale) {

        logger.info("Incoming id to delete an assembly : {}", id);
        AssemblyResponse assemblyResponse = assemblyService.delete(id, locale);
        logger.info("Outgoing response after deleting an assembly : {}", assemblyResponse);

        return assemblyResponse;
    }

    @Override
    public AssemblyResponse findById(Long id, Locale locale) {

        logger.info("Incoming id to find an assembly by id: {}", id);
        AssemblyResponse assemblyResponse = assemblyService.findById(id, locale);
        logger.info("Outgoing response after finding as assembly by id : {}", assemblyResponse);

        return assemblyResponse;
    }

    @Override
    public AssemblyResponse findAllAsAList(String username, Locale locale) {

        logger.info("Incoming request to find all assemblies as a list ");
        AssemblyResponse assemblyResponse = assemblyService.findAllAsAList(username, locale);
        logger.info("Outgoing response after finding all assemblies as a list : {}", assemblyResponse.getMessage());

        return assemblyResponse;
    }

    @Override
    public AssemblyResponse findAllAsPages(int page, int size, Locale locale, String username) {

        logger.info("Incoming request to find all assemblies as a list with pages");
        AssemblyResponse assemblyResponse = assemblyService.findAllAsPages(page, size, locale, username);
        logger.info("Outgoing response after finding all assemblies as a list with pages: {}", assemblyResponse.getMessage());

        return assemblyResponse;
    }
}
