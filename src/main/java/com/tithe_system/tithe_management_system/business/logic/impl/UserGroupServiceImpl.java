package com.tithe_system.tithe_management_system.business.logic.impl;

import com.tithe_system.tithe_management_system.business.auditables.api.UserGroupServiceAuditable;
import com.tithe_system.tithe_management_system.business.logic.api.UserGroupService;
import com.tithe_system.tithe_management_system.business.validations.api.UserGroupServiceValidator;
import com.tithe_system.tithe_management_system.domain.District;
import com.tithe_system.tithe_management_system.domain.EntityStatus;
import com.tithe_system.tithe_management_system.domain.UserGroup;
import com.tithe_system.tithe_management_system.domain.UserRole;
import com.tithe_system.tithe_management_system.repository.UserGroupRepository;
import com.tithe_system.tithe_management_system.repository.UserRoleRepository;
import com.tithe_system.tithe_management_system.repository.specification.DistrictSpecification;
import com.tithe_system.tithe_management_system.repository.specification.UserGroupSpecification;
import com.tithe_system.tithe_management_system.utils.dtos.DistrictDto;
import com.tithe_system.tithe_management_system.utils.dtos.UserGroupDto;
import com.tithe_system.tithe_management_system.utils.dtos.UserRoleDto;
import com.tithe_system.tithe_management_system.utils.enums.I18Code;
import com.tithe_system.tithe_management_system.utils.i18.api.ApplicationMessagesService;
import com.tithe_system.tithe_management_system.utils.requests.AssignUserRoleToUserGroupRequest;
import com.tithe_system.tithe_management_system.utils.requests.CreateUserGroupRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditUserGroupRequest;
import com.tithe_system.tithe_management_system.utils.requests.RemoveUserRolesFromUserGroupRequest;
import com.tithe_system.tithe_management_system.utils.requests.UserGroupMultipleFiltersRequest;
import com.tithe_system.tithe_management_system.utils.responses.UserGroupResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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
import java.util.Set;
import java.util.function.Function;

public class UserGroupServiceImpl implements UserGroupService {
    private final UserGroupServiceValidator userGroupServiceValidator;
    private final UserGroupRepository userGroupRepository;
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;
    private final UserGroupServiceAuditable userGroupServiceAuditable;
    private final ApplicationMessagesService applicationMessagesService;

    public UserGroupServiceImpl(UserGroupServiceValidator userGroupServiceValidator, UserGroupRepository userGroupRepository, UserRoleRepository userRoleRepository,
                                ModelMapper modelMapper, UserGroupServiceAuditable userGroupServiceAuditable,
                                ApplicationMessagesService applicationMessagesService) {
        this.userGroupServiceValidator = userGroupServiceValidator;
        this.userGroupRepository = userGroupRepository;
        this.userRoleRepository = userRoleRepository;
        this.modelMapper = modelMapper;
        this.userGroupServiceAuditable = userGroupServiceAuditable;
        this.applicationMessagesService = applicationMessagesService;
    }

    @Override
    public UserGroupResponse create(CreateUserGroupRequest createUserGroupRequest, String username, Locale locale) {

        String message = "";

        boolean isRequestValid = userGroupServiceValidator.isRequestValidForCreation(createUserGroupRequest);

        if (!isRequestValid) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_CREATE_USER_GROUP_INVALID_REQUEST.getCode(), new String[]{},
                    locale);

            return buildUserGroupResponse(400, false, message, null, null,
                    null);
        }

        Optional<UserGroup> userGroupRetrieved = userGroupRepository.findByNameAndEntityStatusNot(
                createUserGroupRequest.getName(), EntityStatus.DELETED);

        if (userGroupRetrieved.isPresent()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_GROUP_ALREADY_EXISTS.getCode(), new String[]{},
                    locale);

            return buildUserGroupResponse(400, false, message, null, null,
                    null);
        }

        UserGroup userGroupToBeSaved = modelMapper.map(createUserGroupRequest, UserGroup.class);

        UserGroup userGroupSaved = userGroupServiceAuditable.create(userGroupToBeSaved, locale, username);

        UserGroupDto userAccountDtoReturned = modelMapper.map(userGroupSaved, UserGroupDto.class);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_GROUP_CREATED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildUserGroupResponse(201, true, message, userAccountDtoReturned, null,
                null);
    }

    @Override
    public UserGroupResponse edit(EditUserGroupRequest editUserGroupRequest, String username, Locale locale) {

        String message = "";

        boolean isRequestValid = userGroupServiceValidator.isRequestValidForEditing(editUserGroupRequest);

        if(!isRequestValid){

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_EDIT_USER_GROUP_INVALID_REQUEST.getCode(), new String[]{},
                    locale);

            return buildUserGroupResponse(400, false, message, null, null,
                    null);
        }

        Optional<UserGroup> userGroupRetrieved = userGroupRepository.findByIdAndEntityStatusNot(
                editUserGroupRequest.getId(), EntityStatus.DELETED);

        if (userGroupRetrieved.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_GROUP_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildUserGroupResponse(400, false, message, null, null,
                    null);
        }

        UserGroup userGroupToBeEdited = userGroupRetrieved.get();
        userGroupToBeEdited.setDescription(editUserGroupRequest.getDescription());

        if (editUserGroupRequest.getName() != null) {

            Optional<UserGroup> checkForDuplicateUserGroup = userGroupRepository.findByNameAndEntityStatusNot(
                    editUserGroupRequest.getName(), EntityStatus.DELETED);

            if (checkForDuplicateUserGroup.isPresent()) {

                if (!checkForDuplicateUserGroup.get().getId().equals(editUserGroupRequest.getId())) {

                    message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_GROUP_ALREADY_EXISTS.getCode(),
                            new String[]{}, locale);

                    return buildUserGroupResponse(400, false, message, null,
                            null, null);
                }
            }

            userGroupToBeEdited.setName(editUserGroupRequest.getName());
        }

        UserGroup userGroupEdited = userGroupServiceAuditable.edit(userGroupToBeEdited, locale, username);

        UserGroupDto userGroupDto = modelMapper.map(userGroupEdited, UserGroupDto.class);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_GROUP_EDITED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildUserGroupResponse(201, true, message, userGroupDto, null,
                null);
    }

    @Override
    public UserGroupResponse delete(Long id, Locale locale) {

        String message = "";

        boolean isIdValid = userGroupServiceValidator.isIdValid(id);

        if (!isIdValid) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_INVALID_USER_GROUP_ID_SUPPLIED.getCode(), new String[]{},
                    locale);

            return buildUserGroupResponse(400, false, message, null, null,
                    null);
        }

        Optional<UserGroup> userGroupRetrieved = userGroupRepository.findByIdAndEntityStatusNot(id, EntityStatus.DELETED);

        if (userGroupRetrieved.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_GROUP_DOES_NOT_EXIST.getCode(), new String[]{},
                    locale);

            return buildUserGroupResponse(404, false, message, null, null,
                    null);
        }

        UserGroup userGroupToBeDeleted = userGroupRetrieved.get();
        userGroupToBeDeleted.setEntityStatus(EntityStatus.DELETED);
        userGroupToBeDeleted.setName(userGroupToBeDeleted.getName().replace(" ", "_") + LocalDateTime.now());

        UserGroup userGroupDeleted = userGroupServiceAuditable.delete(userGroupToBeDeleted, locale);

        UserGroupDto userGroupDtoReturned = modelMapper.map(userGroupDeleted, UserGroupDto.class);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_GROUP_DELETED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildUserGroupResponse(200, true, message, userGroupDtoReturned, null,
                null);
    }

    @Override
    public UserGroupResponse findById(Long id, Locale locale) {

        String message = "";

        boolean isIdValid = userGroupServiceValidator.isIdValid(id);

        if(!isIdValid) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_INVALID_USER_GROUP_ID_SUPPLIED.getCode(), new String[]
                    {}, locale);

            return buildUserGroupResponse(400, false, message, null, null,
                    null);
        }

        Optional<UserGroup> userGroupRetrieved = userGroupRepository.findByIdAndEntityStatusNot(id, EntityStatus.DELETED);

        if (userGroupRetrieved.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_GROUP_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildUserGroupResponse(404, false, message, null, null,
                    null);
        }

        UserGroup userGroupReturned = userGroupRetrieved.get();

        UserGroupDto userGroupDto = modelMapper.map(userGroupReturned, UserGroupDto.class);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_GROUP_RETRIEVED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildUserGroupResponse(200, true, message, userGroupDto, null,
                null);
    }

    @Override
    public UserGroupResponse findAllAsAList(String username, Locale locale) {

        String message = "";

        List<UserGroup> userGroupList = userGroupRepository.findByEntityStatusNot(EntityStatus.DELETED);

        if(userGroupList.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_GROUP_NOT_FOUND.getCode(), new String[]
                    {}, locale);

            return buildUserGroupResponse(404, false, message, null,
                    null, null);
        }

        List<UserGroupDto> userGroupDtoList = modelMapper.map(userGroupList, new TypeToken<List<UserGroupDto>>(){}.getType());

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_GROUP_RETRIEVED_SUCCESSFULLY.getCode(),
                new String[]{}, locale);

        return buildUserGroupResponse(200, true, message, null,
                userGroupDtoList, null);
    }

    @Override
    public UserGroupResponse findByMultipleFilters(UserGroupMultipleFiltersRequest userGroupMultipleFiltersRequest, Locale
            locale, String username) {

        String message = "";

        Specification<UserGroup> spec = null;
        spec = addToSpec(spec, UserGroupSpecification::deleted);

        boolean isRequestValid = userGroupServiceValidator.isRequestValidToRetrieveUserGroupsByMultipleFilters(
                userGroupMultipleFiltersRequest);

        if (!isRequestValid) {

            message = applicationMessagesService.getApplicationMessage(
                    I18Code.MESSAGE_INVALID_USER_GROUPS_MULTIPLE_FILTERS_REQUEST.getCode(),
                    new String[]{}, locale);

            return buildUserGroupResponse(400, false, message,null, null,
                    null);
        }

        Pageable pageable = PageRequest.of(userGroupMultipleFiltersRequest.getPage(),
                userGroupMultipleFiltersRequest.getSize());

        boolean isSearchValueValid = userGroupServiceValidator.isStringValid(userGroupMultipleFiltersRequest.getSearchValue());

        if (isSearchValueValid) {

            spec = addToSpec(userGroupMultipleFiltersRequest.getSearchValue(), spec, UserGroupSpecification::any);
        }

        Page<UserGroup> result = userGroupRepository.findAll(spec, pageable);

        if (result.getContent().isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_GROUP_NOT_FOUND.getCode(),
                    new String[]{}, locale);

            return buildUserGroupResponse(404, false, message,null, null,
                    null);
        }

        Page<UserGroupDto> userGroupDtoPage = convertUserGroupEntityToUserGroupDto(result);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_GROUP_RETRIEVED_SUCCESSFULLY.getCode(),
                new String[]{}, locale);

        return buildUserGroupResponse(200, true, message,null,
                null, userGroupDtoPage);
    }

    private Specification<UserGroup> addToSpec(Specification<UserGroup> spec,
                                              Function<EntityStatus, Specification<UserGroup>> predicateMethod) {
        Specification<UserGroup> localSpec = Specification.where(predicateMethod.apply(EntityStatus.DELETED));
        spec = (spec == null) ? localSpec : spec.and(localSpec);
        return spec;
    }

    private Specification<UserGroup> addToSpec(final String aString, Specification<UserGroup> spec, Function<String,
            Specification<UserGroup>> predicateMethod) {
        if (aString != null && !aString.isEmpty()) {
            Specification<UserGroup> localSpec = Specification.where(predicateMethod.apply(aString));
            spec = (spec == null) ? localSpec : spec.and(localSpec);
            return spec;
        }
        return spec;
    }

    @Override
    public UserGroupResponse assignUserRoleToUserGroup(AssignUserRoleToUserGroupRequest assignUserRoleToUserGroupRequest, Locale locale, String username) {

            String message = "";

            boolean isValid = userGroupServiceValidator.isRequestValidToAssignUserRolesToUserGroup(assignUserRoleToUserGroupRequest);

            if (!isValid) {

                message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_INVALID_ASSIGN_USER_ROLE_TO_USER_GROUP_REQUEST.getCode(),
                        new String[]{}, locale);

                return buildUserGroupResponse(400, false, message, null, null,
                        null);
            }

            Optional<UserGroup> userGroup =
                    userGroupRepository.findByIdAndEntityStatusNot(assignUserRoleToUserGroupRequest.getUserGroupId(),
                            EntityStatus.DELETED);

            if (userGroup.isEmpty()) {

                message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_GROUP_NOT_FOUND.getCode(), new String[]{},
                        locale);

                return buildUserGroupResponse(400, false, message, null, null,
                        null);
            }

            UserGroup userGroupToBeUpdated = userGroup.get();

            Set<UserRole> userRoleSet = userRoleRepository.findByIdInAndEntityStatusNot(
                    assignUserRoleToUserGroupRequest.getUserRoleIds(), EntityStatus.DELETED);

            if (userRoleSet.isEmpty()) {

                message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ROLE_NOT_FOUND.getCode(),
                        new String[]{}, locale);

                return buildUserGroupResponse(400, false, message, null, null,
                        null);
            }

            if (userGroupToBeUpdated.getUserRoles().containsAll(userRoleSet)) {

                message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ROLES_ALREADY_ASSIGNED.getCode(),
                        new String[]{}, locale);

                return buildUserGroupResponse(400, false, message, null, null,
                        null);
            }

            userRoleSet.addAll(userGroupToBeUpdated.getUserRoles());
            userGroupToBeUpdated.setUserRoles(userRoleSet);

            UserGroup userGroupUpdated = userGroupServiceAuditable.edit(userGroupToBeUpdated, locale, username);

            UserGroupDto userGroupDto = modelMapper.map(userGroupUpdated, UserGroupDto.class);

            List<UserRoleDto> userRoleDtoList = modelMapper.map(userGroupUpdated.getUserRoles(),
                    new TypeToken<List<UserRoleDto>>() {}.getType());

            userGroupDto.setUserRoleDtoSet(userRoleDtoList);

            message = applicationMessagesService.getApplicationMessage(
                    I18Code.MESSAGE_USER_ROLE_ASSIGNED_SUCCESSFULLY.getCode(), new String[]{},
                    locale);

            return buildUserGroupResponse(201, true, message, userGroupDto, null,
                    null);
    }

    @Override
    public UserGroupResponse removeUserRolesFromUserGroup(RemoveUserRolesFromUserGroupRequest removeUserRolesFromUserGroupRequest, Locale locale, String username) {

        String message;

        boolean isValid = userGroupServiceValidator.isRequestValidToRemoveUserRolesFromUserGroup(removeUserRolesFromUserGroupRequest);

        if (!isValid) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_INVALID_REMOVE_USER_ROLES_FROM_USER_GROUP_REQUEST.getCode(),
                    new String[]{}, locale);

            return buildUserGroupResponse(400, false, message, null, null,
                    null);
        }

        Optional<UserGroup> userGroup =
                userGroupRepository.findByIdAndEntityStatusNot(removeUserRolesFromUserGroupRequest.getUserGroupId(),
                        EntityStatus.DELETED);

        if (userGroup.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_GROUP_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildUserGroupResponse(400, false, message, null, null,
                    null);
        }

        UserGroup userGroupToBeUpdated = userGroup.get();

        Set<UserRole> userRoleSet = userRoleRepository.findByIdInAndEntityStatusNot(
                removeUserRolesFromUserGroupRequest.getUserRoleIds(), EntityStatus.DELETED);

        if (userRoleSet.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ROLE_NOT_FOUND.getCode(),
                    new String[]{}, locale);

            return buildUserGroupResponse(400, false, message, null, null,
                    null);
        }

        Set<UserRole> remainingUserRoles = userGroupToBeUpdated.getUserRoles();

        boolean removed = remainingUserRoles.removeAll(userRoleSet);

        if(!removed) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ROLE_NOT_ASSIGNED.getCode(),
                    new String[]{}, locale);

            return buildUserGroupResponse(400, false, message, null, null,
                    null);
        }

        userGroupToBeUpdated.setUserRoles(remainingUserRoles);

        UserGroup userGroupReturned = userGroupServiceAuditable.edit(userGroupToBeUpdated, locale, username);

        UserGroupDto userGroupDto = modelMapper.map(userGroupReturned, UserGroupDto.class);

        List<UserRoleDto> userRoleDtoList = modelMapper.map(userGroupReturned.getUserRoles(),
                new TypeToken<List<UserRoleDto>>() {}.getType());

        userGroupDto.setUserRoleDtoSet(userRoleDtoList);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ROLES_REMOVED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildUserGroupResponse(201, true, message, userGroupDto,
                null, null);
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
