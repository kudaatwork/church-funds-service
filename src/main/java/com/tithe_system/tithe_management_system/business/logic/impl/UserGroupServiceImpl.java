package com.tithe_system.tithe_management_system.business.logic.impl;

import com.tithe_system.tithe_management_system.business.auditables.api.UserGroupServiceAuditable;
import com.tithe_system.tithe_management_system.business.logic.api.UserGroupService;
import com.tithe_system.tithe_management_system.business.validations.api.UserGroupServiceValidator;
import com.tithe_system.tithe_management_system.domain.EntityStatus;
import com.tithe_system.tithe_management_system.domain.UserGroup;
import com.tithe_system.tithe_management_system.repository.UserGroupRepository;
import com.tithe_system.tithe_management_system.utils.dtos.UserGroupDto;
import com.tithe_system.tithe_management_system.utils.enums.I18Code;
import com.tithe_system.tithe_management_system.utils.i18.api.MessageService;
import com.tithe_system.tithe_management_system.utils.requests.CreateUserGroupRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditUserGroupRequest;
import com.tithe_system.tithe_management_system.utils.responses.UserGroupResponse;
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

public class UserGroupServiceImpl implements UserGroupService {
    private final UserGroupServiceValidator userGroupServiceValidator;
    private final UserGroupRepository userGroupRepository;
    private final ModelMapper modelMapper;
    private final UserGroupServiceAuditable userGroupServiceAuditable;
    private final MessageService messageService;

    public UserGroupServiceImpl(UserGroupServiceValidator userGroupServiceValidator, UserGroupRepository userGroupRepository,
                                ModelMapper modelMapper, UserGroupServiceAuditable userGroupServiceAuditable,
                                MessageService messageService) {
        this.userGroupServiceValidator = userGroupServiceValidator;
        this.userGroupRepository = userGroupRepository;
        this.modelMapper = modelMapper;
        this.userGroupServiceAuditable = userGroupServiceAuditable;
        this.messageService = messageService;
    }

    @Override
    public UserGroupResponse create(CreateUserGroupRequest createUserGroupRequest, String username, Locale locale) {

        String message = "";

        boolean isRequestValid = userGroupServiceValidator.isRequestValidForCreation(createUserGroupRequest);

        if (!isRequestValid) {

            message = messageService.getMessage(I18Code.MESSAGE_CREATE_USER_GROUP_INVALID_REQUEST.getCode(), new String[]{},
                    locale);

            return buildUserGroupResponse(400, false, message, null, null,
                    null);
        }

        Optional<UserGroup> userGroupRetrieved = userGroupRepository.findByNameAndEntityStatusNot(
                createUserGroupRequest.getName(), EntityStatus.DELETED);

        if (userGroupRetrieved.isPresent()) {

            message = messageService.getMessage(I18Code.MESSAGE_USER_GROUP_ALREADY_EXISTS.getCode(), new String[]{},
                    locale);

            return buildUserGroupResponse(400, false, message, null, null,
                    null);
        }

        UserGroup userGroupToBeSaved = modelMapper.map(createUserGroupRequest, UserGroup.class);

        UserGroup userGroupSaved = userGroupServiceAuditable.create(userGroupToBeSaved, locale, username);

        UserGroupDto userAccountDtoReturned = modelMapper.map(userGroupSaved, UserGroupDto.class);

        message = messageService.getMessage(I18Code.MESSAGE_USER_GROUP_CREATED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildUserGroupResponse(201, true, message, userAccountDtoReturned, null,
                null);
    }

    @Override
    public UserGroupResponse edit(EditUserGroupRequest editUserGroupRequest, String username, Locale locale) {

        String message = "";

        boolean isRequestValid = userGroupServiceValidator.isRequestValidForEditing(editUserGroupRequest);

        if(!isRequestValid){

            message = messageService.getMessage(I18Code.MESSAGE_EDIT_USER_GROUP_INVALID_REQUEST.getCode(), new String[]{},
                    locale);

            return buildUserGroupResponse(400, false, message, null, null,
                    null);
        }

        Optional<UserGroup> userGroupRetrieved = userGroupRepository.findByIdAndEntityStatusNot(
                editUserGroupRequest.getId(), EntityStatus.DELETED);

        if (userGroupRetrieved.isEmpty()) {

            message = messageService.getMessage(I18Code.MESSAGE_USER_GROUP_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildUserGroupResponse(400, false, message, null, null,
                    null);
        }

        UserGroup userGroupToBeEdited = userGroupRetrieved.get();
        userGroupToBeEdited.setDescription(editUserGroupRequest.getDescription());

        if (editUserGroupRequest.getName() != null) {

            if (Objects.equals(userGroupToBeEdited.getId(), editUserGroupRequest.getId()) &&
                    Objects.equals(userGroupToBeEdited.getName().toLowerCase(), editUserGroupRequest.getName().toLowerCase())) {

                message = messageService.getMessage(I18Code.MESSAGE_USER_GROUP_ALREADY_EXISTS.getCode(), new String[]{},
                        locale);

                return buildUserGroupResponse(400, false, message, null,
                        null, null);
            }
            else {

                userGroupToBeEdited.setName(editUserGroupRequest.getName());
            }
        }

        UserGroup userGroupEdited = userGroupServiceAuditable.edit(userGroupToBeEdited, locale, username);

        UserGroupDto userGroupDto = modelMapper.map(userGroupEdited, UserGroupDto.class);

        message = messageService.getMessage(I18Code.MESSAGE_USER_GROUP_EDITED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildUserGroupResponse(201, true, message, userGroupDto, null,
                null);
    }

    @Override
    public UserGroupResponse delete(Long id, Locale locale) {

        String message = "";

        boolean isIdValid = userGroupServiceValidator.isIdValid(id);

        if (!isIdValid) {

            message = messageService.getMessage(I18Code.MESSAGE_INVALID_USER_GROUP_ID_SUPPLIED.getCode(), new String[]{},
                    locale);

            return buildUserGroupResponse(400, false, message, null, null,
                    null);
        }

        Optional<UserGroup> userGroupRetrieved = userGroupRepository.findByIdAndEntityStatusNot(id, EntityStatus.DELETED);

        if (userGroupRetrieved.isEmpty()) {

            message = messageService.getMessage(I18Code.MESSAGE_USER_GROUP_DOES_NOT_EXIST.getCode(), new String[]{},
                    locale);

            return buildUserGroupResponse(404, false, message, null, null,
                    null);
        }

        UserGroup userGroupToBeDeleted = userGroupRetrieved.get();
        userGroupToBeDeleted.setEntityStatus(EntityStatus.DELETED);
        userGroupToBeDeleted.setName(userGroupToBeDeleted.getName().replace(" ", "_") + LocalDateTime.now());

        UserGroup userGroupDeleted = userGroupServiceAuditable.delete(userGroupToBeDeleted, locale);

        UserGroupDto userGroupDtoReturned = modelMapper.map(userGroupDeleted, UserGroupDto.class);

        message = messageService.getMessage(I18Code.MESSAGE_USER_GROUP_DELETED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildUserGroupResponse(200, true, message, userGroupDtoReturned, null,
                null);
    }

    @Override
    public UserGroupResponse findById(Long id, Locale locale) {

        String message = "";

        boolean isIdValid = userGroupServiceValidator.isIdValid(id);

        if(!isIdValid) {

            message = messageService.getMessage(I18Code.MESSAGE_INVALID_USER_GROUP_ID_SUPPLIED.getCode(), new String[]
                    {}, locale);

            return buildUserGroupResponse(400, false, message, null, null,
                    null);
        }

        Optional<UserGroup> userGroupRetrieved = userGroupRepository.findByIdAndEntityStatusNot(id, EntityStatus.DELETED);

        if (userGroupRetrieved.isEmpty()) {

            message = messageService.getMessage(I18Code.MESSAGE_USER_GROUP_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildUserGroupResponse(404, false, message, null, null,
                    null);
        }

        UserGroup userGroupReturned = userGroupRetrieved.get();

        UserGroupDto userGroupDto = modelMapper.map(userGroupReturned, UserGroupDto.class);

        message = messageService.getMessage(I18Code.MESSAGE_USER_GROUP_RETRIEVED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildUserGroupResponse(200, true, message, userGroupDto, null,
                null);
    }

    @Override
    public UserGroupResponse findAllAsAList(String username, Locale locale) {

        String message = "";

        List<UserGroup> userGroupList = userGroupRepository.findByEntityStatusNot(EntityStatus.DELETED);

        if(userGroupList.isEmpty()) {

            message = messageService.getMessage(I18Code.MESSAGE_USER_GROUP_NOT_FOUND.getCode(), new String[]
                    {}, locale);

            return buildUserGroupResponse(404, false, message, null,
                    null, null);
        }

        List<UserGroupDto> userGroupDtoList = modelMapper.map(userGroupList, new TypeToken<List<UserGroupDto>>(){}.getType());

        message = messageService.getMessage(I18Code.MESSAGE_USER_GROUP_RETRIEVED_SUCCESSFULLY.getCode(),
                new String[]{}, locale);

        return buildUserGroupResponse(200, true, message, null,
                userGroupDtoList, null);
    }

    @Override
    public UserGroupResponse findAllAsPages(int page, int size, Locale locale, String username) {

        String message ="";

        final Pageable pageable = PageRequest.of(page, size);

        Page<UserGroup> userGroupPage = userGroupRepository.findByEntityStatusNot(EntityStatus.DELETED, pageable);

        Page<UserGroupDto> userGroupDtoPage = convertUserGroupEntityToUserGroupDto(userGroupPage);

        if(userGroupDtoPage.getContent().isEmpty()){

            message =  messageService.getMessage(I18Code.MESSAGE_USER_GROUP_NOT_FOUND.getCode(),
                    new String[]{}, locale);

            return buildUserGroupResponse(404, false, message, null, null,
                    userGroupDtoPage);
        }

        message =  messageService.getMessage(I18Code.MESSAGE_USER_GROUP_RETRIEVED_SUCCESSFULLY.getCode(),
                new String[]{}, locale);

        return buildUserGroupResponse(200, true, message, null,
                null, userGroupDtoPage);
    }

    private Page<UserGroupDto> convertUserGroupEntityToUserGroupDto(Page<UserGroup> userGroupPage){

        List<UserGroup> userGroupList = userGroupPage.getContent();
        List<UserGroupDto> userGroupDtoList = new ArrayList<>();

        for (UserGroup userGroup : userGroupPage) {
            UserGroupDto userGroupDto = modelMapper.map(userGroup, UserGroupDto.class);
            userGroupDtoList.add(userGroupDto);
        }

        int page = userGroupPage.getNumber();
        int size = userGroupPage.getSize();

        size = size <= 0 ? 10 : size;

        Pageable pageable = PageRequest.of(page, size);

        return new PageImpl<UserGroupDto>(userGroupDtoList, pageable, userGroupPage.getTotalElements());
    }

    private UserGroupResponse buildUserGroupResponse(int statusCode, boolean isSuccess, String message,
                                                     UserGroupDto userGroupDto, List<UserGroupDto> userGroupDtoList,
                                                     Page<UserGroupDto> userGroupDtoPage){

        UserGroupResponse userGroupResponse = new UserGroupResponse();
        userGroupResponse.setStatusCode(statusCode);
        userGroupResponse.setSuccess(isSuccess);
        userGroupResponse.setMessage(message);
        userGroupResponse.setUserGroupDto(userGroupDto);
        userGroupResponse.setUserGroupDtoList(userGroupDtoList);
        userGroupResponse.setUserGroupDtoPage(userGroupDtoPage);

        return userGroupResponse;
    }
}
