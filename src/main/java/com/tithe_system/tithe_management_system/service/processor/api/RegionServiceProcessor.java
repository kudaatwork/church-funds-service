package com.tithe_system.tithe_management_system.service.processor.api;

import com.tithe_system.tithe_management_system.utils.requests.CreateRegionRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditRegionRequest;
import com.tithe_system.tithe_management_system.utils.responses.RegionResponse;
import java.util.Locale;

public interface RegionServiceProcessor {
    RegionResponse create(CreateRegionRequest createRegionRequest, String username, Locale locale);
    RegionResponse edit(EditRegionRequest editRegionRequest, String username, Locale locale);
    RegionResponse delete(Long id, Locale locale);
    RegionResponse findById(Long id, Locale locale);
    RegionResponse findAllAsAList(String username, Locale locale);
    RegionResponse findAllAsPages(int page, int size, Locale locale, String username);
}
