package com.tithe_system.tithe_management_system.business.validations.impl;

import com.tithe_system.tithe_management_system.business.validations.api.AssemblyServiceValidator;
import com.tithe_system.tithe_management_system.utils.constants.Constants;
import com.tithe_system.tithe_management_system.utils.requests.AssemblyMultipleFiltersRequest;
import com.tithe_system.tithe_management_system.utils.requests.CreateAssemblyRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditAssemblyRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AssemblyServiceValidatorImpl implements AssemblyServiceValidator {
    @Override
    public boolean isRequestValidForCreation(CreateAssemblyRequest createAssemblyRequest) {

        if (createAssemblyRequest == null) {
            return false;
        }

        if (createAssemblyRequest.getProvinceId() == null || createAssemblyRequest.getRegionId() == null ||
        createAssemblyRequest.getDistrictId() == null) {
            return false;
        }

        if (createAssemblyRequest.getProvinceId() < 1 || createAssemblyRequest.getRegionId() < 1 ||
                createAssemblyRequest.getDistrictId() < 1) {
            return false;
        }

        if (createAssemblyRequest.getName() == null || createAssemblyRequest.getName().isEmpty() ||
        createAssemblyRequest.getContactPhoneNumber() == null || createAssemblyRequest.getContactPhoneNumber().isEmpty() ||
        createAssemblyRequest.getContactEmail() == null || createAssemblyRequest.getContactEmail().isEmpty() ||
        createAssemblyRequest.getAddress() == null || createAssemblyRequest.getAddress().isEmpty()) {
            return false;
        }

        if (ProvinceServiceValidatorImpl.checkUsingIsDigitMethod(createAssemblyRequest.getName())) {
            return false;
        }

        if (!isValidEmail(createAssemblyRequest.getContactEmail())){
            return false;
        }

        if (!isValidPhoneLocalPhoneNumber(createAssemblyRequest.getContactPhoneNumber())){
            return false;
        }

        return true;
    }

    @Override
    public boolean isRequestValidForEditing(EditAssemblyRequest editAssemblyRequest) {

        if (editAssemblyRequest == null) {
            return false;
        }

        if (editAssemblyRequest.getId() == null || editAssemblyRequest.getProvinceId() == null ||
                editAssemblyRequest.getRegionId() == null || editAssemblyRequest.getDistrictId() == null) {
            return false;
        }

        if (editAssemblyRequest.getId() < 1 || editAssemblyRequest.getProvinceId() < 1 || editAssemblyRequest.getRegionId() < 1 ||
                editAssemblyRequest.getDistrictId() < 1) {
            return false;
        }

        if (editAssemblyRequest.getName().isEmpty() || editAssemblyRequest.getContactPhoneNumber().isEmpty() ||
                editAssemblyRequest.getContactEmail().isEmpty() || editAssemblyRequest.getAddress().isEmpty()) {
            return false;
        }

        if (editAssemblyRequest.getName() != null){
            if (ProvinceServiceValidatorImpl.checkUsingIsDigitMethod(editAssemblyRequest.getName())) {
                return false;
            }
        }

        if (editAssemblyRequest.getContactEmail() != null){
            if (!isValidEmail(editAssemblyRequest.getContactEmail())){
                return false;
            }
        }

        if (editAssemblyRequest.getContactPhoneNumber() != null){
            if (!isValidPhoneLocalPhoneNumber(editAssemblyRequest.getContactPhoneNumber())){
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isIdValid(Long id) {
        return id != null && id >= 1;
    }

    @Override
    public boolean isRequestValidToRetrieveAssembliesByMultipleFilters(AssemblyMultipleFiltersRequest assemblyMultipleFiltersRequest) {

        if (assemblyMultipleFiltersRequest == null){
            return false;
        }

        if (assemblyMultipleFiltersRequest.getPage() < 0) {
            return false;
        }

        return assemblyMultipleFiltersRequest.getSize() > 0;
    }

    @Override
    public boolean isStringValid(String stringValue) {

        return stringValue != null && !stringValue.isEmpty();
    }

    public static boolean isValidEmail(String name)
    {
        String regex = Constants.EMAIL_REGEX;

        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(name);

        return m.matches();
    }

    public static boolean isValidPhoneLocalPhoneNumber(String name)
    {
        String regex = Constants.ZIM_NUMBER_REGEX;

        Pattern p = Pattern.compile(regex);

        Matcher m = p.matcher(name);

        return m.matches();
    }
}
