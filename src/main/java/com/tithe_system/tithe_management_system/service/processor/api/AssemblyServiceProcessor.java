package com.tithe_system.tithe_management_system.service.processor.api;

import com.tithe_system.tithe_management_system.utils.requests.CreateAssemblyRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditAssemblyRequest;
import com.tithe_system.tithe_management_system.utils.responses.AssemblyResponse;
import java.util.Locale;

public interface AssemblyServiceProcessor {
    AssemblyResponse create(CreateAssemblyRequest createAssemblyRequest , String username, Locale locale);
    AssemblyResponse edit(EditAssemblyRequest editAssemblyRequest, String username, Locale locale);
    AssemblyResponse delete(Long id, Locale locale);
    AssemblyResponse findById(Long id, Locale locale);
    AssemblyResponse findByDistrictId(Long id, Locale locale);
    AssemblyResponse findAllAsAList(String username, Locale locale);
    AssemblyResponse findAllAsPages(int page, int size, Locale locale, String username);
}
