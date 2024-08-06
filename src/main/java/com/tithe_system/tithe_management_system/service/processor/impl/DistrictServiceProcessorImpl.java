package com.tithe_system.tithe_management_system.service.processor.impl;

import com.tithe_system.tithe_management_system.business.logic.api.DistrictService;
import com.tithe_system.tithe_management_system.service.processor.api.DistrictServiceProcessor;
import com.tithe_system.tithe_management_system.utils.requests.CreateDistrictRequest;
import com.tithe_system.tithe_management_system.utils.requests.DistrictMultipleFiltersRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditDistrictRequest;
import com.tithe_system.tithe_management_system.utils.responses.DistrictResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Locale;

public class DistrictServiceProcessorImpl implements DistrictServiceProcessor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final DistrictService districtService;

    public DistrictServiceProcessorImpl(DistrictService districtService) {
        this.districtService = districtService;
    }

    @Override
    public DistrictResponse create(CreateDistrictRequest createDistrictRequest, String username, Locale locale) {

        logger.info("Incoming request to create a district : {}", createDistrictRequest);
        DistrictResponse districtResponse = districtService.create(createDistrictRequest,
                username, locale);
        logger.info("Outgoing response after creating a district : {}", districtResponse);

        return districtResponse;
    }

    @Override
    public DistrictResponse edit(EditDistrictRequest editDistrictRequest, String username, Locale locale) {

        logger.info("Incoming request to edit a district : {}", editDistrictRequest);
        DistrictResponse districtResponse = districtService.edit(editDistrictRequest,
                username, locale);
        logger.info("Outgoing response after editing a district : {}", districtResponse);

        return districtResponse;
    }

    @Override
    public DistrictResponse delete(Long id, Locale locale) {

        logger.info("Incoming id to delete a district : {}", id);
        DistrictResponse districtResponse = districtService.delete(id, locale);
        logger.info("Outgoing response after deleting a district : {}", districtResponse);

        return districtResponse;
    }

    @Override
    public DistrictResponse findById(Long id, Locale locale) {

        logger.info("Incoming id to find a district by id: {}", id);
        DistrictResponse districtResponse = districtService.findById(id, locale);
        logger.info("Outgoing response after finding a district by id : {}", districtResponse);

        return districtResponse;
    }

    @Override
    public DistrictResponse findByProvinceId(Long id, Locale locale, int page, int size) {

        logger.info("Incoming id to find districts by province id: {}", id);
        DistrictResponse districtResponse = districtService.findByProvinceId(id, locale, page, size);
        logger.info("Outgoing response after finding districts by province id : {}", districtResponse);

        return districtResponse;
    }

    @Override
    public DistrictResponse findAllAsAList(String username, Locale locale) {

        logger.info("Incoming request to find all districts as a list ");
        DistrictResponse districtResponse = districtService.findAllAsAList(username, locale);
        logger.info("Outgoing response after finding all districts as a list : {}", districtResponse.getMessage());

        return districtResponse;
    }

    @Override
    public DistrictResponse findByMultipleFilters(DistrictMultipleFiltersRequest districtMultipleFiltersRequest,
                                                  Locale locale, String username) {

        logger.info("Incoming request to find all districts by multiple filters as pages: {}", districtMultipleFiltersRequest);
        DistrictResponse districtResponse = districtService.findByMultipleFilters(districtMultipleFiltersRequest, locale,
                username);
        logger.info("Outgoing response after finding all districts by multiple filters as pages: {}", districtResponse.getMessage());

        return districtResponse;
    }
}
