package com.tithe_system.tithe_management_system.business.validations.api;

import com.tithe_system.tithe_management_system.utils.requests.AssemblyMultipleFiltersRequest;
import com.tithe_system.tithe_management_system.utils.requests.CreateRegionRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditRegionRequest;
import com.tithe_system.tithe_management_system.utils.requests.RegionMultipleFiltersRequest;

public interface RegionServiceValidator {
    boolean isRequestValidForCreation(CreateRegionRequest createRegionRequest);
    boolean isRequestValidForEditing(EditRegionRequest editRegionRequest);
    boolean isIdValid(Long id);
    boolean isRequestValidToRetrieveAssembliesByMultipleFilters(RegionMultipleFiltersRequest regionMultipleFiltersRequest);
    boolean isStringValid(String stringValue);
}
