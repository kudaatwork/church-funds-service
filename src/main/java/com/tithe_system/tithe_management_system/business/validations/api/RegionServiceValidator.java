package com.tithe_system.tithe_management_system.business.validations.api;

import com.tithe_system.tithe_management_system.utils.requests.CreateRegionRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditRegionRequest;

public interface RegionServiceValidator {
    boolean isRequestValidForCreation(CreateRegionRequest createRegionRequest);
    boolean isRequestValidForEditing(EditRegionRequest editRegionRequest);
    boolean isIdValid(Long id);
}
