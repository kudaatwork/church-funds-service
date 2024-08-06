package com.tithe_system.tithe_management_system.business.validations.impl;

import com.tithe_system.tithe_management_system.business.validations.api.DistrictServiceValidator;
import com.tithe_system.tithe_management_system.utils.requests.CreateDistrictRequest;
import com.tithe_system.tithe_management_system.utils.requests.DistrictMultipleFiltersRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditDistrictRequest;

public class DistrictServiceValidatorImpl implements DistrictServiceValidator {
    @Override
    public boolean isRequestValidForCreation(CreateDistrictRequest createDistrictRequest) {

        if (createDistrictRequest == null) {
            return false;
        }

        if (createDistrictRequest.getProvinceId() == null || createDistrictRequest.getRegionId() == null) {
            return false;
        }

        if (createDistrictRequest.getProvinceId() < 1 || createDistrictRequest.getRegionId() < 1 ) {
            return false;
        }

        if (createDistrictRequest.getName() == null || createDistrictRequest.getName().isEmpty()) {
            return false;
        }

        if (ProvinceServiceValidatorImpl.checkUsingIsDigitMethod(createDistrictRequest.getName())) {
            return false;
        }

        return true;
    }

    @Override
    public boolean isRequestValidForEditing(EditDistrictRequest editDistrictRequest) {

        if (editDistrictRequest == null) {
            return false;
        }

        if (editDistrictRequest.getId() == null || editDistrictRequest.getProvinceId() == null ||
                editDistrictRequest.getRegionId() == null) {
            return false;
        }

        if (editDistrictRequest.getId() < 1 || editDistrictRequest.getProvinceId() < 1 ||
                editDistrictRequest.getRegionId() < 1 ) {
            return false;
        }

        if (editDistrictRequest.getName() == null || editDistrictRequest.getName().isEmpty()) {
            return false;
        }

        if (ProvinceServiceValidatorImpl.checkUsingIsDigitMethod(editDistrictRequest.getName())) {
            return false;
        }

        return true;
    }

    @Override
    public boolean isIdValid(Long id) {

        return id != null && id >= 1;
    }

    @Override
    public boolean isRequestValidToRetrieveDistrictsByMultipleFilters(DistrictMultipleFiltersRequest
                                                                                  districtMultipleFiltersRequest) {

        if (districtMultipleFiltersRequest == null){
            return false;
        }

        if (districtMultipleFiltersRequest.getPage() < 0) {
            return false;
        }

        return districtMultipleFiltersRequest.getSize() > 0;
    }

    @Override
    public boolean isStringValid(String stringValue) {
        return stringValue != null && !stringValue.isEmpty();
    }
}
