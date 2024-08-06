package com.tithe_system.tithe_management_system.business.validations.api;

import com.tithe_system.tithe_management_system.utils.requests.CreateProvinceRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditProvinceRequest;
import com.tithe_system.tithe_management_system.utils.requests.ProvinceMultipleFiltersRequest;

public interface ProvinceServiceValidator {
    boolean isRequestValidForCreation(CreateProvinceRequest createProvinceRequest);
    boolean isRequestValidForEditing(EditProvinceRequest editProvinceRequest);
    boolean isIdValid(Long id);
    boolean isRequestValidToRetrieveAssembliesByMultipleFilters(ProvinceMultipleFiltersRequest provinceMultipleFiltersRequest);
    boolean isStringValid(String stringValue);
}
