package com.tithe_system.tithe_management_system.business.validations.impl;

import com.tithe_system.tithe_management_system.business.validations.api.ProvinceServiceValidator;
import com.tithe_system.tithe_management_system.utils.requests.CreateProvinceRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditProvinceRequest;

public class ProvinceServiceValidatorImpl implements ProvinceServiceValidator {
    @Override
    public boolean isRequestValidForCreation(CreateProvinceRequest createProvinceRequest) {

        if (createProvinceRequest == null) {
            return false;
        }

        if (createProvinceRequest.getRegionId() == null || createProvinceRequest.getRegionId() < 1) {
            return false;
        }

        if (createProvinceRequest.getName() == null || createProvinceRequest.getName().isEmpty()) {
            return false;
        }

        if (checkUsingIsDigitMethod(createProvinceRequest.getName())){
            return false;
        }

        return true;
    }

    @Override
    public boolean isRequestValidForEditing(EditProvinceRequest editProvinceRequest) {

        if (editProvinceRequest == null) {
            return false;
        }

        if (editProvinceRequest.getId() == null || editProvinceRequest.getId() < 1) {
            return false;
        }

        if (editProvinceRequest.getRegionId() == null || editProvinceRequest.getRegionId() < 1) {
            return false;
        }

        if (editProvinceRequest.getName() == null || editProvinceRequest.getName().isEmpty()) {
            return false;
        }

        if (checkUsingIsDigitMethod(editProvinceRequest.getName())){
            return false;
        }

        return true;
    }

    @Override
    public boolean isIdValid(Long id) {

        return id != null && id >= 1;
    }

    static boolean checkUsingIsDigitMethod(String input) {
        for (char c : input.toCharArray()) {
            if (Character.isDigit(c)) {
                return true;
            }
        }

        return false;
    }
}
