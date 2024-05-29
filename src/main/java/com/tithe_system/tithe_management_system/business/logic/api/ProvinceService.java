package com.tithe_system.tithe_management_system.business.logic.api;

import com.tithe_system.tithe_management_system.utils.requests.CreateProvinceRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditProvinceRequest;
import com.tithe_system.tithe_management_system.utils.responses.ProvinceResponse;
import java.util.Locale;

public interface ProvinceService {
    ProvinceResponse create(CreateProvinceRequest createProvinceRequest, String username, Locale locale);
    ProvinceResponse edit(EditProvinceRequest editProvinceRequest, String username, Locale locale);
    ProvinceResponse delete(Long id, Locale locale);
    ProvinceResponse findById(Long id, Locale locale);
    ProvinceResponse findAllAsAList(String username, Locale locale);
    ProvinceResponse findAllAsPages(int page, int size, Locale locale, String username);
}
