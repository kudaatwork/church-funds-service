package com.tithe_system.tithe_management_system.business.validations.api;

import com.tithe_system.tithe_management_system.utils.requests.AssemblyMultipleFiltersRequest;
import com.tithe_system.tithe_management_system.utils.requests.CreateAssemblyRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditAssemblyRequest;

public interface AssemblyServiceValidator {
    boolean isRequestValidForCreation(CreateAssemblyRequest createAssemblyRequest);
    boolean isRequestValidForEditing(EditAssemblyRequest editAssemblyRequest);
    boolean isIdValid(Long id);
    boolean isRequestValidToRetrieveAssembliesByMultipleFilters(AssemblyMultipleFiltersRequest assemblyMultipleFiltersRequest);
    boolean isStringValid(String stringValue);
}
