package com.tithe_system.tithe_management_system.business.logic.api;

import com.tithe_system.tithe_management_system.utils.requests.CreateDistrictRequest;
import com.tithe_system.tithe_management_system.utils.requests.DistrictMultipleFiltersRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditDistrictRequest;
import com.tithe_system.tithe_management_system.utils.responses.DistrictResponse;
import java.util.Locale;

public interface DistrictService {
    DistrictResponse create(CreateDistrictRequest createDistrictRequest, String username, Locale locale);
    DistrictResponse edit(EditDistrictRequest editDistrictRequest, String username, Locale locale);
    DistrictResponse delete(Long id, Locale locale);
    DistrictResponse findById(Long id, Locale locale);
    DistrictResponse findByProvinceId(Long id, Locale locale, int page, int size);
    DistrictResponse findAllAsAList(String username, Locale locale);
    DistrictResponse findByMultipleFilters(DistrictMultipleFiltersRequest districtMultipleFiltersRequest, Locale locale, String username);
}
