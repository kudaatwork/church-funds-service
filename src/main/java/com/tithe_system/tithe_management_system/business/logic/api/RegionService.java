package com.tithe_system.tithe_management_system.business.logic.api;

import com.tithe_system.tithe_management_system.utils.requests.AssemblyMultipleFiltersRequest;
import com.tithe_system.tithe_management_system.utils.requests.CreateRegionRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditRegionRequest;
import com.tithe_system.tithe_management_system.utils.requests.RegionMultipleFiltersRequest;
import com.tithe_system.tithe_management_system.utils.responses.AssemblyResponse;
import com.tithe_system.tithe_management_system.utils.responses.RegionResponse;
import java.util.Locale;

public interface RegionService {
    RegionResponse create(CreateRegionRequest createRegionRequest, String username, Locale locale);
    RegionResponse edit(EditRegionRequest editRegionRequest, String username, Locale locale);
    RegionResponse delete(Long id, Locale locale);
    RegionResponse findById(Long id, Locale locale);
    RegionResponse findAllAsAList(String username, Locale locale);
    RegionResponse findByMultipleFilters(RegionMultipleFiltersRequest regionMultipleFiltersRequest, Locale locale,
                                           String username);
}
