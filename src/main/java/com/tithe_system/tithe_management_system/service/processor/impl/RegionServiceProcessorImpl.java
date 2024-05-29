package com.tithe_system.tithe_management_system.service.processor.impl;

import com.tithe_system.tithe_management_system.business.logic.api.RegionService;
import com.tithe_system.tithe_management_system.service.processor.api.RegionServiceProcessor;
import com.tithe_system.tithe_management_system.utils.requests.CreateRegionRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditRegionRequest;
import com.tithe_system.tithe_management_system.utils.responses.RegionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Locale;

public class RegionServiceProcessorImpl implements RegionServiceProcessor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RegionService regionService;

    public RegionServiceProcessorImpl(RegionService regionService) {
        this.regionService = regionService;
    }

    @Override
    public RegionResponse create(CreateRegionRequest createRegionRequest, String username, Locale locale) {

        logger.info("Incoming request to create a region : {}", createRegionRequest);
        RegionResponse regionResponse = regionService.create(createRegionRequest,
                username, locale);
        logger.info("Outgoing response after creating a region : {}", regionResponse);

        return regionResponse;
    }

    @Override
    public RegionResponse edit(EditRegionRequest editRegionRequest, String username, Locale locale) {

        logger.info("Incoming request to edit a region : {}", editRegionRequest);
        RegionResponse regionResponse = regionService.edit(editRegionRequest,
                username, locale);
        logger.info("Outgoing response after editing a region : {}", regionResponse);

        return regionResponse;
    }

    @Override
    public RegionResponse delete(Long id, Locale locale) {

        logger.info("Incoming id to delete a region : {}", id);
        RegionResponse regionResponse = regionService.delete(id, locale);
        logger.info("Outgoing response after deleting a region : {}", regionResponse);

        return regionResponse;
    }

    @Override
    public RegionResponse findById(Long id, Locale locale) {

        logger.info("Incoming id to find a region by id: {}", id);
        RegionResponse regionResponse = regionService.findById(id, locale);
        logger.info("Outgoing response after finding a region by id : {}", regionResponse);

        return regionResponse;
    }

    @Override
    public RegionResponse findAllAsAList(String username, Locale locale) {

        logger.info("Incoming request to find all regions as a list ");
        RegionResponse regionResponse = regionService.findAllAsAList(username, locale);
        logger.info("Outgoing response after finding all regions as a list : {}", regionResponse.getMessage());

        return regionResponse;
    }

    @Override
    public RegionResponse findAllAsPages(int page, int size, Locale locale, String username) {

        logger.info("Incoming request to find all regions as a list with pages");
        RegionResponse regionResponse = regionService.findAllAsPages(page, size, locale, username);
        logger.info("Outgoing response after finding all regions as a list with pages: {}", regionResponse.getMessage());

        return regionResponse;
    }
}
