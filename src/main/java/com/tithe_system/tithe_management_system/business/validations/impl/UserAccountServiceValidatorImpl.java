package com.tithe_system.tithe_management_system.business.validations.impl;

import com.tithe_system.tithe_management_system.business.validations.api.UserAccountServiceValidator;
import com.tithe_system.tithe_management_system.domain.Gender;
import com.tithe_system.tithe_management_system.domain.Title;
import com.tithe_system.tithe_management_system.utils.requests.CreateUserAccountRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditUserAccountRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserAccountServiceValidatorImpl implements UserAccountServiceValidator {
    private static Logger logger = LoggerFactory.getLogger(PaymentServiceValidatorImpl.class);
    @Override
    public boolean isRequestValidForCreation(CreateUserAccountRequest createUserAccountRequest) {

        if (createUserAccountRequest == null) {
            return false;
        }

        if (createUserAccountRequest.getUserGroupId() == null || createUserAccountRequest.getAssemblyId() == null
        || createUserAccountRequest.getUserGroupId() < 1 || createUserAccountRequest.getAssemblyId() < 1) {
            return false;
        }

        if (createUserAccountRequest.getFirstName() == null || createUserAccountRequest.getFirstName().isEmpty() ||
        createUserAccountRequest.getLastName() == null || createUserAccountRequest.getLastName().isEmpty() ||
        createUserAccountRequest.getGender() == null || createUserAccountRequest.getGender().isEmpty() ||
        createUserAccountRequest.getEmailAddress() == null || createUserAccountRequest.getEmailAddress().isEmpty() ||
        createUserAccountRequest.getTitle() == null || createUserAccountRequest.getTitle().isEmpty() ||
        createUserAccountRequest.getPhoneNumber() == null || createUserAccountRequest.getPhoneNumber().isEmpty() ||
        createUserAccountRequest.getUsername() == null || createUserAccountRequest.getUsername().isEmpty() ||
        createUserAccountRequest.getPassword() == null || createUserAccountRequest.getPassword().isEmpty()) {
            return false;
        }

        if (!AssemblyServiceValidatorImpl.isValidEmail(createUserAccountRequest.getEmailAddress())) {
            return false;
        }

        if (!AssemblyServiceValidatorImpl.isValidPhoneLocalPhoneNumber(createUserAccountRequest.getPhoneNumber())) {
            return false;
        }

        if (!isGenderValid(createUserAccountRequest.getGender())) {
            return false;
        }

        if (!isTitleValid(createUserAccountRequest.getTitle())) {
            return false;
        }

        return true;
    }

    @Override
    public boolean isRequestValidForEditing(EditUserAccountRequest editUserAccountRequest) {

        if (editUserAccountRequest == null) {
            return false;
        }

        if (editUserAccountRequest.getUserGroupId() == null || editUserAccountRequest.getAssemblyId() == null
                || editUserAccountRequest.getUserGroupId() < 1 || editUserAccountRequest.getAssemblyId() < 1) {
            return false;
        }

        if (editUserAccountRequest.getFirstName() != null && editUserAccountRequest.getFirstName().isEmpty()) {
            return false;
        }

        if (editUserAccountRequest.getLastName() != null && editUserAccountRequest.getLastName().isEmpty()) {
            return false;
        }

        if (editUserAccountRequest.getGender() != null && editUserAccountRequest.getGender().isEmpty()) {
            return false;
        }

        if (editUserAccountRequest.getEmailAddress() != null && editUserAccountRequest.getEmailAddress().isEmpty()) {
            return false;
        }

        if (editUserAccountRequest.getTitle() != null && editUserAccountRequest.getTitle().isEmpty()) {
            return false;
        }

        if (editUserAccountRequest.getPhoneNumber() != null && editUserAccountRequest.getPhoneNumber().isEmpty()) {
            return false;
        }

        if (editUserAccountRequest.getUsername() != null && editUserAccountRequest.getUsername().isEmpty()) {
            return false;
        }

        if (editUserAccountRequest.getFirstName() != null &&
                ProvinceServiceValidatorImpl.checkUsingIsDigitMethod(editUserAccountRequest.getFirstName())) {
            return false;
        }

        if (editUserAccountRequest.getLastName() != null &&
                ProvinceServiceValidatorImpl.checkUsingIsDigitMethod(editUserAccountRequest.getLastName())) {
            return false;
        }

        if (editUserAccountRequest.getEmailAddress() != null) {

            if (!AssemblyServiceValidatorImpl.isValidEmail(editUserAccountRequest.getEmailAddress())) {
                return false;
            }
        }

        if (editUserAccountRequest.getPhoneNumber() != null) {

            if (!AssemblyServiceValidatorImpl.isValidPhoneLocalPhoneNumber(editUserAccountRequest.getPhoneNumber())) {
                return false;
            }
        }

        if (editUserAccountRequest.getGender() != null) {

            if (!isGenderValid(editUserAccountRequest.getGender())) {
                return false;
            }
        }

        if (editUserAccountRequest.getTitle() != null) {

            if (!isTitleValid(editUserAccountRequest.getTitle())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isIdValid(Long id) {
        return id != null && id > 1;
    }

    private boolean isGenderValid(String genderSupplied) {

        try {

            Gender[] genders = Gender.values();

            for (Gender gender : genders)

                if (gender.getGender().equals(genderSupplied)){
                    return true;
                }

            return false;
        } catch (Exception ex) {

            logger.info("Error encountered while converting gender value from request : {}", genderSupplied);

            return false;
        }
    }

    private boolean isTitleValid(String titleSupplied) {

        try {

            Title[] titles = Title.values();

            for (Title title : titles)

                if (title.getTitle().equals(titleSupplied)){
                    return true;
                }

            return false;
        } catch (Exception ex) {

            logger.info("Error encountered while converting title value from request : {}", titleSupplied);

            return false;
        }
    }
}
