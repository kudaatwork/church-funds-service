package com.tithe_system.tithe_management_system.business.logic.impl;

import com.tithe_system.tithe_management_system.business.auditables.api.UserRoleServiceAuditable;
import com.tithe_system.tithe_management_system.business.logic.api.UserRoleService;
import com.tithe_system.tithe_management_system.business.validations.api.UserRoleServiceValidator;
import com.tithe_system.tithe_management_system.domain.EntityStatus;
import com.tithe_system.tithe_management_system.domain.UserGroup;
import com.tithe_system.tithe_management_system.domain.UserRole;
import com.tithe_system.tithe_management_system.repository.UserRoleRepository;
import com.tithe_system.tithe_management_system.repository.specification.UserGroupSpecification;
import com.tithe_system.tithe_management_system.repository.specification.UserRoleSpecification;
import com.tithe_system.tithe_management_system.utils.dtos.UserGroupDto;
import com.tithe_system.tithe_management_system.utils.dtos.UserRoleDto;
import com.tithe_system.tithe_management_system.utils.enums.I18Code;
import com.tithe_system.tithe_management_system.utils.i18.api.ApplicationMessagesService;
import com.tithe_system.tithe_management_system.utils.requests.CreateUserRoleRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditUserRoleRequest;
import com.tithe_system.tithe_management_system.utils.requests.UserRoleMultipleFiltersRequest;
import com.tithe_system.tithe_management_system.utils.responses.UserRoleResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleServiceValidator userRoleServiceValidator;
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;
    private final UserRoleServiceAuditable userRoleServiceAuditable;
    private final ApplicationMessagesService applicationMessagesService;

    public UserRoleServiceImpl(UserRoleServiceValidator userRoleServiceValidator, UserRoleRepository userRoleRepository,
                               ModelMapper modelMapper, UserRoleServiceAuditable userRoleServiceAuditable,
                               ApplicationMessagesService applicationMessagesService) {
        this.userRoleServiceValidator = userRoleServiceValidator;
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
        this.userRoleServiceAuditable = userRoleServiceAuditable;
        this.applicationMessagesService = applicationMessagesService;
    }

    @Override
    public UserRoleResponse create(CreateUserRoleRequest createUserRoleRequest, String username, Locale locale) {

        String message = "";

        boolean isRequestValid = userRoleServiceValidator.isRequestValidForCreation(createUserRoleRequest);

        if (!isRequestValid) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_CREATE_USER_ROLE_INVALID_REQUEST.getCode(), new String[]{},
                    locale);

            return buildUserRoleResponse(400, false, message, null, null,
                    null);
        }

        Optional<UserRole> userRoleRetrieved = userRoleRepository.findByNameAndEntityStatusNot(
                createUserRoleRequest.getName(), EntityStatus.DELETED);

        if (userRoleRetrieved.isPresent()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ROLE_ALREADY_EXISTS.getCode(), new String[]{},
                    locale);

            return buildUserRoleResponse(400, false, message, null, null,
                    null);
        }

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserRole userRoleToBeSaved = modelMapper.map(createUserRoleRequest, UserRole.class);

        UserRole userRoleSaved = userRoleServiceAuditable.create(userRoleToBeSaved, locale, username);

        UserRoleDto userRoleDtoReturned = modelMapper.map(userRoleSaved, UserRoleDto.class);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ROLE_CREATED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildUserRoleResponse(201, true, message, userRoleDtoReturned, null,
                null);
    }

    @Override
    public UserRoleResponse edit(EditUserRoleRequest editUserRoleRequest, String username, Locale locale) {

        String message = "";

        boolean isRequestValid = userRoleServiceValidator.isRequestValidForEditing(editUserRoleRequest);

        if(!isRequestValid){

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_EDIT_USER_ROLE_INVALID_REQUEST.getCode(), new String[]{},
                    locale);

            return buildUserRoleResponse(400, false, message, null, null,
                    null);
        }

        Optional<UserRole> userRoleRetrieved = userRoleRepository.findByIdAndEntityStatusNot(
                editUserRoleRequest.getId(), EntityStatus.DELETED);

        if (userRoleRetrieved.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ROLE_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildUserRoleResponse(400, false, message, null, null,
                    null);
        }

        UserRole userRoleToBeEdited = userRoleRetrieved.get();

        if (editUserRoleRequest.getName() != null) {

            Optional<UserRole> checkForDuplicateUserRole = userRoleRepository.findByNameAndEntityStatusNot(
                    editUserRoleRequest.getName(), EntityStatus.DELETED);

            if (checkForDuplicateUserRole.isPresent()) {

                if (!checkForDuplicateUserRole.get().getId().equals(editUserRoleRequest.getId())) {

                    message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ROLE_ALREADY_EXISTS.getCode(),
                            new String[]{}, locale);

                    return buildUserRoleResponse(400, false, message, null,
                            null, null);
                }
            }

            userRoleToBeEdited.setName(editUserRoleRequest.getName());
        }

        UserRole userRoleEdited = userRoleServiceAuditable.edit(userRoleToBeEdited, locale, username);

        UserRoleDto userRoleDto = modelMapper.map(userRoleEdited, UserRoleDto.class);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ROLE_EDITED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildUserRoleResponse(201, true, message, userRoleDto, null,
                null);
    }

    @Override
    public UserRoleResponse delete(Long id, Locale locale) {

        String message = "";

        boolean isIdValid = userRoleServiceValidator.isIdValid(id);

        if (!isIdValid) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_INVALID_USER_ROLE_ID_SUPPLIED.getCode(), new String[]{},
                    locale);

            return buildUserRoleResponse(400, false, message, null, null,
                    null);
        }

        Optional<UserRole> userRoleRetrieved = userRoleRepository.findByIdAndEntityStatusNot(id, EntityStatus.DELETED);

        if (userRoleRetrieved.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ROLE_DOES_NOT_EXIST.getCode(), new String[]{},
                    locale);

            return buildUserRoleResponse(404, false, message, null, null,
                    null);
        }

        UserRole userRoleToBeDeleted = userRoleRetrieved.get();
        userRoleToBeDeleted.setEntityStatus(EntityStatus.DELETED);
        userRoleToBeDeleted.setName(userRoleToBeDeleted.getName().replace(" ", "_") + LocalDateTime.now());

        UserRole userRoleDeleted = userRoleServiceAuditable.delete(userRoleToBeDeleted, locale);

        UserRoleDto userRoleDtoReturned = modelMapper.map(userRoleDeleted, UserRoleDto.class);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ROLE_DELETED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildUserRoleResponse(200, true, message, userRoleDtoReturned, null,
                null);
    }

    @Override
    public UserRoleResponse findById(Long id, Locale locale) {

        String message = "";

        boolean isIdValid = userRoleServiceValidator.isIdValid(id);

        if(!isIdValid) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_INVALID_USER_GROUP_ID_SUPPLIED.getCode(), new String[]
                    {}, locale);

            return buildUserRoleResponse(400, false, message, null, null,
                    null);
        }

        Optional<UserRole> userRoleRetrieved = userRoleRepository.findByIdAndEntityStatusNot(id, EntityStatus.DELETED);

        if (userRoleRetrieved.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ROLE_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildUserRoleResponse(404, false, message, null, null,
                    null);
        }

        UserRole userRoleReturned = userRoleRetrieved.get();

        UserRoleDto userRoleDto = modelMapper.map(userRoleReturned, UserRoleDto.class);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ROLE_RETRIEVED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildUserRoleResponse(200, true, message, userRoleDto, null,
                null);
    }

    @Override
    public UserRoleResponse findAllAsAList(String username, Locale locale) {

        String message = "";

        List<UserRole> userRoleList = userRoleRepository.findByEntityStatusNot(EntityStatus.DELETED);

        if(userRoleList.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ROLE_NOT_FOUND.getCode(), new String[]
                    {}, locale);

            return buildUserRoleResponse(404, false, message, null,
                    null, null);
        }

        List<UserRoleDto> userRoleDtoList = modelMapper.map(userRoleList, new TypeToken<List<UserGroupDto>>(){}.getType());

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ROLE_RETRIEVED_SUCCESSFULLY.getCode(),
                new String[]{}, locale);

        return buildUserRoleResponse(200, true, message, null,
                userRoleDtoList, null);
    }

    @Override
    public UserRoleResponse findByMultipleFilters(UserRoleMultipleFiltersRequest userRoleMultipleFiltersRequest, Locale locale,
                                                  String username) {

        String message = "";

        Specification<UserRole> spec = null;
        spec = addToSpec(spec, UserRoleSpecification::deleted);

        boolean isRequestValid = userRoleServiceValidator.isRequestValidToRetrieveUserRolesByMultipleFilters(
                userRoleMultipleFiltersRequest);

        if (!isRequestValid) {

            message = applicationMessagesService.getApplicationMessage(
                    I18Code.MESSAGE_INVALID_USER_ROLES_MULTIPLE_FILTERS_REQUEST.getCode(),
                    new String[]{}, locale);

            return buildUserRoleResponse(400, false, message,null, null,
                    null);
        }

        Pageable pageable = PageRequest.of(userRoleMultipleFiltersRequest.getPage(),
                userRoleMultipleFiltersRequest.getSize());

        boolean isSearchValueValid = userRoleServiceValidator.isStringValid(userRoleMultipleFiltersRequest.getSearchValue());

        if (isSearchValueValid) {

            spec = addToSpec(userRoleMultipleFiltersRequest.getSearchValue(), spec, UserRoleSpecification::any);
        }

        Page<UserRole> result = userRoleRepository.findAll(spec, pageable);

        if (result.getContent().isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ROLE_NOT_FOUND.getCode(),
                    new String[]{}, locale);

            return buildUserRoleResponse(404, false, message,null, null,
                    null);
        }

        Page<UserRoleDto> userRoleDtoPage = convertUserRoleEntityToUserRoleDto(result);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ROLE_RETRIEVED_SUCCESSFULLY.getCode(),
                new String[]{}, locale);

        return buildUserRoleResponse(200, true, message,null,
                null, userRoleDtoPage);
    }

    private Specification<UserRole> addToSpec(Specification<UserRole> spec,
                                               Function<EntityStatus, Specification<UserRole>> predicateMethod) {
        Specification<UserRole> localSpec = Specification.where(predicateMethod.apply(EntityStatus.DELETED));
        spec = (spec == null) ? localSpec : spec.and(localSpec);
        return spec;
    }

    private Specification<UserRole> addToSpec(final String aString, Specification<UserRole> spec, Function<String,
            Specification<UserRole>> predicateMethod) {
        if (aString != null && !aString.isEmpty()) {
            Specification<UserRole> localSpec = Specification.where(predicateMethod.apply(aString));
            spec = (spec == null) ? localSpec : spec.and(localSpec);
            return spec;
        }
        return spec;
    }

    private Page<UserRoleDto> convertUserRoleEntityToUserRoleDto(Page<UserRole> userRolePage){

        List<UserRole> userRoleList = userRolePage.getContent();
        List<UserRoleDto> userRoleDtoList = new ArrayList<>();

        for (UserRole userRole : userRolePage) {
            UserRoleDto userRoleDto = modelMapper.map(userRole, UserRoleDto.class);
            userRoleDtoList.add(userRoleDto);
        }

        int page = userRolePage.getNumber();
        int size = userRolePage.getSize();

        size = size <= 0 ? 10 : size;

        Pageable pageable = PageRequest.of(page, size);

        return new PageImpl<UserRoleDto>(userRoleDtoList, pageable, userRolePage.getTotalElements());
    }

    private UserRoleResponse buildUserRoleResponse(int statusCode, boolean isSuccess, String message,
                                                   UserRoleDto userRoleDto, List<UserRoleDto> userRoleDtoList,
                                                   Page<UserRoleDto> userRoleDtoPage){

        UserRoleResponse userRoleResponse = new UserRoleResponse();
        userRoleResponse.setStatusCode(statusCode);
        userRoleResponse.setSuccess(isSuccess);
        userRoleResponse.setMessage(message);
        userRoleResponse.setUserRoleDto(userRoleDto);
        userRoleResponse.setUserRoleDtoList(userRoleDtoList);
        userRoleResponse.setUserRoleDtoPage(userRoleDtoPage);

        return userRoleResponse;
    }
}
