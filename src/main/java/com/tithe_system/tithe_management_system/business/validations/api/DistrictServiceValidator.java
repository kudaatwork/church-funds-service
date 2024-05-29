package com.tithe_system.tithe_management_system.business.validations.api;

import com.tithe_system.tithe_management_system.utils.requests.CreateDistrictRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditDistrictRequest;

public interface DistrictServiceValidator {
    boolean isRequestValidForCreation(CreateDistrictRequest createDistrictRequest);
    boolean isRequestValidForEditing(EditDistrictRequest editDistrictRequest);
    boolean isIdValid(Long id);
}
