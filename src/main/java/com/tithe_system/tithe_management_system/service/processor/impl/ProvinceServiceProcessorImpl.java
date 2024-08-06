package com.tithe_system.tithe_management_system.service.processor.impl;

import com.tithe_system.tithe_management_system.business.logic.api.ProvinceService;
import com.tithe_system.tithe_management_system.service.processor.api.ProvinceServiceProcessor;
import com.tithe_system.tithe_management_system.utils.requests.CreateProvinceRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditProvinceRequest;
import com.tithe_system.tithe_management_system.utils.requests.ProvinceMultipleFiltersRequest;
import com.tithe_system.tithe_management_system.utils.responses.ProvinceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Locale;

public class ProvinceServiceProcessorImpl implements ProvinceServiceProcessor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ProvinceService provinceService;

    public ProvinceServiceProcessorImpl(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @Override
    public ProvinceResponse create(CreateProvinceRequest createProvinceRequest, String username, Locale locale) {

        logger.info("Incoming request to create a province : {}", createProvinceRequest);
        ProvinceResponse provinceResponse = provinceService.create(createProvinceRequest,
                username, locale);
        logger.info("Outgoing response after creating a province : {}", provinceResponse);

        return provinceResponse;
    }

    @Override
    public ProvinceResponse edit(EditProvinceRequest editProvinceRequest, String username, Locale locale) {

        logger.info("Incoming request to edit a province : {}", editProvinceRequest);
        ProvinceResponse provinceResponse = provinceService.edit(editProvinceRequest,
                username, locale);
        logger.info("Outgoing response after editing a province : {}", provinceResponse);

        return provinceResponse;
    }

    @Override
    public ProvinceResponse delete(Long id, Locale locale) {

        logger.info("Incoming request to delete a province by id : {}", id);
        ProvinceResponse provinceResponse = provinceService.delete(id, locale);
        logger.info("Outgoing response after deleting a province by id : {}", provinceResponse);

        return provinceResponse;
    }

    @Override
    public ProvinceResponse findById(Long id, Locale locale) {

        logger.info("Incoming request to find a province by id : {}", id);
        ProvinceResponse provinceResponse = provinceService.findById(id, locale);
        logger.info("Outgoing response after finding a province by id : {}", provinceResponse);

        return provinceResponse;
    }

    @Override
    public ProvinceResponse findByRegionId(Long id, Locale locale, int page, int size) {

        logger.info("Incoming request to find provinces by region id : {}", id);
        ProvinceResponse provinceResponse = provinceService.findByRegionId(id, locale, page , size);
        logger.info("Outgoing response after finding provinces by region id : {}", provinceResponse);

        return provinceResponse;
    }

    @Override
    public ProvinceResponse findAllAsAList(String username, Locale locale) {

        logger.info("Incoming request to find all provinces as a list");
        ProvinceResponse provinceResponse = provinceService.findAllAsAList(username, locale);
        logger.info("Outgoing response after finding all provinces as a list : {}", provinceResponse.getMessage());

        return provinceResponse;
    }

    @Override
    public ProvinceResponse findByMultipleFilters(ProvinceMultipleFiltersRequest provinceMultipleFiltersRequest, Locale locale,
                                                  String username) {

        logger.info("Incoming request to find all provinces by multiple filters as pages: {}", provinceMultipleFiltersRequest);
        ProvinceResponse provinceResponse = provinceService.findByMultipleFilters(provinceMultipleFiltersRequest, locale, username);
        logger.info("Outgoing response after finding all provinces by multiple filters as pages : {}", provinceResponse.getMessage());

        return provinceResponse;
    }
}
