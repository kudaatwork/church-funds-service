package com.tithe_system.tithe_management_system.business.logic.impl;

import com.tithe_system.tithe_management_system.business.auditables.api.UserRoleServiceAuditable;
import com.tithe_system.tithe_management_system.business.logic.api.UserRoleService;
import com.tithe_system.tithe_management_system.business.validations.api.UserRoleServiceValidator;
import com.tithe_system.tithe_management_system.domain.EntityStatus;
import com.tithe_system.tithe_management_system.domain.UserRole;
import com.tithe_system.tithe_management_system.repository.UserRoleRepository;
import com.tithe_system.tithe_management_system.utils.dtos.UserGroupDto;
import com.tithe_system.tithe_management_system.utils.dtos.UserRoleDto;
import com.tithe_system.tithe_management_system.utils.enums.I18Code;
import com.tithe_system.tithe_management_system.utils.i18.api.MessageService;
import com.tithe_system.tithe_management_system.utils.requests.CreateUserRoleRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditUserRoleRequest;
import com.tithe_system.tithe_management_system.utils.responses.UserRoleResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleServiceValidator userGroupServiceValidator;
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;
    private final UserRoleServiceAuditable userGroupServiceAuditable;
    private final MessageService messageService;

    public UserRoleServiceImpl(UserRoleServiceValidator userGroupServiceValidator, UserRoleRepository userRoleRepository, ModelMapper modelMapper, UserRoleServiceAuditable userGroupServiceAuditable, MessageService messageService) {
        this.userGroupServiceValidator = userGroupServiceValidator;
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
        this.userGroupServiceAuditable = userGroupServiceAuditable;
        this.messageService = messageService;
    }

    @Override
    public UserRoleResponse create(CreateUserRoleRequest createUserRoleRequest, String username, Locale locale) {

        String message = "";

        boolean isRequestValid = userGroupServiceValidator.isRequestValidForCreation(createUserRoleRequest);

        if (!isRequestValid) {

            message = messageService.getMessage(I18Code.MESSAGE_CREATE_USER_ROLE_INVALID_REQUEST.getCode(), new String[]{},
                    locale);

            return buildUserRoleResponse(400, false, message, null, null,
                    null);
        }

        Optional<UserRole> userRoleRetrieved = userRoleRepository.findByNameAndEntityStatusNot(
                createUserRoleRequest.getName(), EntityStatus.DELETED);

        if (userRoleRetrieved.isPresent()) {

            message = messageService.getMessage(I18Code.MESSAGE_USER_ROLE_ALREADY_EXISTS.getCode(), new String[]{},
                    locale);

            return buildUserRoleResponse(400, false, message, null, null,
                    null);
        }

        UserRole userRoleToBeSaved = modelMapper.map(createUserRoleRequest, UserRole.class);

        UserRole userRoleSaved = userGroupServiceAuditable.create(userRoleToBeSaved, locale, username);

        UserRoleDto userRoleDtoReturned = modelMapper.map(userRoleSaved, UserRoleDto.class);

        message = messageService.getMessage(I18Code.MESSAGE_USER_ROLE_CREATED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildUserRoleResponse(201, true, message, userRoleDtoReturned, null,
                null);
    }

    @Override
    public UserRoleResponse edit(EditUserRoleRequest editUserRoleRequest, String username, Locale locale) {

        String message = "";

        boolean isRequestValid = userGroupServiceValidator.isRequestValidForEditing(editUserRoleRequest);

        if(!isRequestValid){

            message = messageService.getMessage(I18Code.MESSAGE_EDIT_USER_ROLE_INVALID_REQUEST.getCode(), new String[]{},
                    locale);

            return buildUserRoleResponse(400, false, message, null, null,
                    null);
        }

        Optional<UserRole> userRoleRetrieved = userRoleRepository.findByIdAndEntityStatusNot(
                editUserRoleRequest.getId(), EntityStatus.DELETED);

        if (userRoleRetrieved.isEmpty()) {

            message = messageService.getMessage(I18Code.MESSAGE_USER_ROLE_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildUserRoleResponse(400, false, message, null, null,
                    null);
        }

        UserRole userRoleToBeEdited = userRoleRetrieved.get();

        if (editUserRoleRequest.getName() != null) {

            if (Objects.equals(userRoleToBeEdited.getId(), editUserRoleRequest.getId()) &&
                    Objects.equals(userRoleToBeEdited.getName().toLowerCase(), editUserRoleRequest.getName().toLowerCase())) {

                message = messageService.getMessage(I18Code.MESSAGE_USER_ROLE_ALREADY_EXISTS.getCode(), new String[]{},
                        locale);

                return buildUserRoleResponse(400, false, message, null,
                        null, null);
            }
            else {

                userRoleToBeEdited.setName(editUserRoleRequest.getName());
            }
        }

        UserRole userRoleEdited = userGroupServiceAuditable.edit(userRoleToBeEdited, locale, username);

        UserRoleDto userRoleDto = modelMapper.map(userRoleEdited, UserRoleDto.class);

        message = messageService.getMessage(I18Code.MESSAGE_USER_ROLE_EDITED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildUserRoleResponse(201, true, message, userRoleDto, null,
                null);
    }

    @Override
    public UserRoleResponse delete(Long id, Locale locale) {

        String message = "";

        boolean isIdValid = userGroupServiceValidator.isIdValid(id);

        if (!isIdValid) {

            message = messageService.getMessage(I18Code.MESSAGE_INVALID_USER_ROLE_ID_SUPPLIED.getCode(), new String[]{},
                    locale);

            return buildUserRoleResponse(400, false, message, null, null,
                    null);
        }

        Optional<UserRole> userRoleRetrieved = userRoleRepository.findByIdAndEntityStatusNot(id, EntityStatus.DELETED);

        if (userRoleRetrieved.isEmpty()) {

            message = messageService.getMessage(I18Code.MESSAGE_USER_ROLE_DOES_NOT_EXIST.getCode(), new String[]{},
                    locale);

            return buildUserRoleResponse(404, false, message, null, null,
                    null);
        }

        UserRole userRoleToBeDeleted = userRoleRetrieved.get();
        userRoleToBeDeleted.setEntityStatus(EntityStatus.DELETED);
        userRoleToBeDeleted.setName(userRoleToBeDeleted.getName().replace(" ", "_") + LocalDateTime.now());

        UserRole userRoleDeleted = userGroupServiceAuditable.delete(userRoleToBeDeleted, locale);

        UserRoleDto userRoleDtoReturned = modelMapper.map(userRoleDeleted, UserRoleDto.class);

        message = messageService.getMessage(I18Code.MESSAGE_USER_ROLE_DELETED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildUserRoleResponse(200, true, message, userRoleDtoReturned, null,
                null);
    }

    @Override
    public UserRoleResponse findById(Long id, Locale locale) {

        String message = "";

        boolean isIdValid = userGroupServiceValidator.isIdValid(id);

        if(!isIdValid) {

            message = messageService.getMessage(I18Code.MESSAGE_INVALID_USER_GROUP_ID_SUPPLIED.getCode(), new String[]
                    {}, locale);

            return buildUserRoleResponse(400, false, message, null, null,
                    null);
        }

        Optional<UserRole> userRoleRetrieved = userRoleRepository.findByIdAndEntityStatusNot(id, EntityStatus.DELETED);

        if (userRoleRetrieved.isEmpty()) {

            message = messageService.getMessage(I18Code.MESSAGE_USER_ROLE_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildUserRoleResponse(404, false, message, null, null,
                    null);
        }

        UserRole userRoleReturned = userRoleRetrieved.get();

        UserRoleDto userRoleDto = modelMapper.map(userRoleReturned, UserRoleDto.class);

        message = messageService.getMessage(I18Code.MESSAGE_USER_ROLE_RETRIEVED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildUserRoleResponse(200, true, message, userRoleDto, null,
                null);
    }

    @Override
    public UserRoleResponse findAllAsAList(String username, Locale locale) {

        String message = "";

        List<UserRole> userRoleList = userRoleRepository.findByEntityStatusNot(EntityStatus.DELETED);

        if(userRoleList.isEmpty()) {

            message = messageService.getMessage(I18Code.MESSAGE_USER_ROLE_NOT_FOUND.getCode(), new String[]
                    {}, locale);

            return buildUserRoleResponse(404, false, message, null,
                    null, null);
        }

        List<UserRoleDto> userRoleDtoList = modelMapper.map(userRoleList, new TypeToken<List<UserGroupDto>>(){}.getType());

        message = messageService.getMessage(I18Code.MESSAGE_USER_ROLE_RETRIEVED_SUCCESSFULLY.getCode(),
                new String[]{}, locale);

        return buildUserRoleResponse(200, true, message, null,
                userRoleDtoList, null);
    }

    @Override
    public UserRoleResponse findAllAsPages(int page, int size, Locale locale, String username) {

        String message ="";

        final Pageable pageable = PageRequest.of(page, size);

        Page<UserRole> userRolePage = userRoleRepository.findByEntityStatusNot(EntityStatus.DELETED, pageable);

        Page<UserRoleDto> userRoleDtoPage = convertUserRoleEntityToUserRoleDto(userRolePage);

        if(userRoleDtoPage.getContent().isEmpty()){

            message =  messageService.getMessage(I18Code.MESSAGE_USER_ROLE_NOT_FOUND.getCode(),
                    new String[]{}, locale);

            return buildUserRoleResponse(404, false, message, null, null,
                    userRoleDtoPage);
        }

        message =  messageService.getMessage(I18Code.MESSAGE_USER_ROLE_RETRIEVED_SUCCESSFULLY.getCode(),
                new String[]{}, locale);

        return buildUserRoleResponse(200, true, message, null,
                null, userRoleDtoPage);
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
