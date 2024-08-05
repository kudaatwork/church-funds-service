package com.tithe_system.tithe_management_system.business.validations.impl;

import com.tithe_system.tithe_management_system.business.validations.api.RegionServiceValidator;
import com.tithe_system.tithe_management_system.utils.requests.CreateRegionRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditRegionRequest;
import com.tithe_system.tithe_management_system.utils.requests.RegionMultipleFiltersRequest;

public class RegionServiceValidatorImpl implements RegionServiceValidator {

    @Override
    public boolean isRequestValidForCreation(CreateRegionRequest createRegionRequest) {

        if (createRegionRequest == null) {
            return false;
        }

        if (createRegionRequest.getName() == null || createRegionRequest.getName().isEmpty()) {
            return false;
        }

        if (ProvinceServiceValidatorImpl.checkUsingIsDigitMethod(createRegionRequest.getName())) {
            return false;
        }

        return true;
    }

    @Override
    public boolean isRequestValidForEditing(EditRegionRequest editRegionRequest) {

        if (editRegionRequest == null) {
            return false;
        }

        if (editRegionRequest.getId() < 1) {
            return false;
        }

        if (editRegionRequest.getName() == null || editRegionRequest.getName().isEmpty()) {
            return false;
        }

        if (ProvinceServiceValidatorImpl.checkUsingIsDigitMethod(editRegionRequest.getName())) {
            return false;
        }

        return true;
    }

    @Override
    public boolean isIdValid(Long id) {
        return id != null && id >= 1;
    }

    @Override
    public boolean isRequestValidToRetrieveAssembliesByMultipleFilters(RegionMultipleFiltersRequest regionMultipleFiltersRequest) {

        if (regionMultipleFiltersRequest == null){
            return false;
        }

        if (regionMultipleFiltersRequest.getPage() < 0) {
            return false;
        }

        return regionMultipleFiltersRequest.getSize() > 0;
    }

    @Override
    public boolean isStringValid(String stringValue) {
        return stringValue != null && !stringValue.isEmpty();
    }
}
