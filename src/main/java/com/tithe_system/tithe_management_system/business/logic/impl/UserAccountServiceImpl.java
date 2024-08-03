package com.tithe_system.tithe_management_system.business.logic.impl;

import com.tithe_system.tithe_management_system.business.auditables.api.UserAccountServiceAuditable;
import com.tithe_system.tithe_management_system.business.logic.api.UserAccountService;
import com.tithe_system.tithe_management_system.business.validations.api.UserAccountServiceValidator;
import com.tithe_system.tithe_management_system.domain.Assembly;
import com.tithe_system.tithe_management_system.domain.EntityStatus;
import com.tithe_system.tithe_management_system.domain.Gender;
import com.tithe_system.tithe_management_system.domain.Title;
import com.tithe_system.tithe_management_system.domain.UserAccount;
import com.tithe_system.tithe_management_system.domain.UserGroup;
import com.tithe_system.tithe_management_system.repository.AssemblyRepository;
import com.tithe_system.tithe_management_system.repository.UserAccountRepository;
import com.tithe_system.tithe_management_system.repository.UserGroupRepository;
import com.tithe_system.tithe_management_system.utils.dtos.UserAccountDto;
import com.tithe_system.tithe_management_system.utils.enums.I18Code;
import com.tithe_system.tithe_management_system.utils.generators.PasswordEncryptionAlgorithm;
import com.tithe_system.tithe_management_system.utils.i18.api.ApplicationMessagesService;
import com.tithe_system.tithe_management_system.utils.requests.CreateUserAccountRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditUserAccountRequest;
import com.tithe_system.tithe_management_system.utils.responses.UserAccountResponse;
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
import java.util.Optional;

public class UserAccountServiceImpl implements UserAccountService {
    private final UserAccountServiceValidator userAccountServiceValidator;
    private final UserAccountRepository userAccountRepository;
    private final UserGroupRepository userGroupRepository;
    private final AssemblyRepository assemblyRepository;
    private final ModelMapper modelMapper;
    private final UserAccountServiceAuditable userAccountServiceAuditable;
    private final ApplicationMessagesService applicationMessagesService;

    public UserAccountServiceImpl(UserAccountServiceValidator userAccountServiceValidator, UserAccountRepository
            userAccountRepository, UserGroupRepository userGroupRepository, AssemblyRepository assemblyRepository,
                                  ModelMapper modelMapper, UserAccountServiceAuditable userAccountServiceAuditable,
                                  ApplicationMessagesService applicationMessagesService) {
        this.userAccountServiceValidator = userAccountServiceValidator;
        this.userAccountRepository = userAccountRepository;
        this.userGroupRepository = userGroupRepository;
        this.assemblyRepository = assemblyRepository;
        this.modelMapper = modelMapper;
        this.userAccountServiceAuditable = userAccountServiceAuditable;
        this.applicationMessagesService = applicationMessagesService;
    }

    @Override
    public UserAccountResponse create(CreateUserAccountRequest createUserAccountRequest, String username, Locale locale) {

        String message = "";

        boolean isRequestValid = userAccountServiceValidator.isRequestValidForCreation(createUserAccountRequest);

        if (!isRequestValid) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_CREATE_USER_ACCOUNT_INVALID_REQUEST.getCode(), new String[]{},
                    locale);

            return buildUserAccountResponse(400, false, message, null, null,
                    null);
        }

        Optional<Assembly> assemblyRetrieved = assemblyRepository.findByIdAndEntityStatusNot(
                createUserAccountRequest.getAssemblyId(), EntityStatus.DELETED);

        if (assemblyRetrieved.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_ASSEMBLY_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildUserAccountResponse(400, false, message, null, null,
                    null);
        }

        Optional<UserGroup> userGroupRetrieved = userGroupRepository.findByIdAndEntityStatusNot(
                createUserAccountRequest.getUserGroupId(), EntityStatus.DELETED);

        if (userGroupRetrieved.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_GROUP_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildUserAccountResponse(400, false, message, null, null,
                    null);
        }

        Optional<UserAccount> userAccountRetrieved = userAccountRepository.findByPhoneNumberAndEntityStatusNot(
                createUserAccountRequest.getPhoneNumber(), EntityStatus.DELETED);

        if (userAccountRetrieved.isPresent()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ACCOUNT_ALREADY_EXISTS.getCode(), new String[]{},
                    locale);

            return buildUserAccountResponse(400, false, message, null, null,
                    null);
        }

        Optional<UserAccount> userAccountRetrieved2 = userAccountRepository.findByEmailAddressAndEntityStatusNot(
                createUserAccountRequest.getEmailAddress(), EntityStatus.DELETED);

        if (userAccountRetrieved2.isPresent()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ACCOUNT_ALREADY_EXISTS.getCode(), new String[]{},
                    locale);

            return buildUserAccountResponse(400, false, message, null, null,
                    null);
        }

        Optional<UserAccount> userAccountRetrieved3 = userAccountRepository.findByUsernameAndEntityStatusNot(
                createUserAccountRequest.getUsername(), EntityStatus.DELETED);

        if (userAccountRetrieved3.isPresent()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ACCOUNT_ALREADY_EXISTS.getCode(), new String[]{},
                    locale);

            return buildUserAccountResponse(400, false, message, null, null,
                    null);
        }


        String encryptedPassword = PasswordEncryptionAlgorithm.encrypt(createUserAccountRequest.getPassword());

        UserAccount userAccountToBeSaved = modelMapper.map(createUserAccountRequest, UserAccount.class);
        userAccountToBeSaved.setPassword(encryptedPassword);
        userAccountToBeSaved.setAssembly(assemblyRetrieved.get());
        userAccountToBeSaved.setUserGroup(userGroupRetrieved.get());

        UserAccount userAccountSaved = userAccountServiceAuditable.create(userAccountToBeSaved, locale, username);

        UserAccountDto userAccountDtoReturned = modelMapper.map(userAccountSaved, UserAccountDto.class);
        userAccountDtoReturned.setPassword(null);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ACCOUNT_CREATED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildUserAccountResponse(201, true, message, userAccountDtoReturned, null,
                null);
    }

    @Override
    public UserAccountResponse edit(EditUserAccountRequest editUserAccountRequest, String username, Locale locale) {

        String message = "";

        boolean isRequestValid = userAccountServiceValidator.isRequestValidForEditing(editUserAccountRequest);

        if(!isRequestValid){

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_EDIT_USER_ACCOUNT_INVALID_REQUEST.getCode(), new String[]{},
                    locale);

            return buildUserAccountResponse(400, false, message, null, null,
                    null);
        }

        Optional<Assembly> assemblyRetrieved = assemblyRepository.findByIdAndEntityStatusNot(
                editUserAccountRequest.getAssemblyId(), EntityStatus.DELETED);

        if (assemblyRetrieved.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_ASSEMBLY_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildUserAccountResponse(400, false, message, null, null,
                    null);
        }

        Optional<UserGroup> userGroupRetrieved = userGroupRepository.findByIdAndEntityStatusNot(
                editUserAccountRequest.getUserGroupId(), EntityStatus.DELETED);

        if (userGroupRetrieved.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ACCOUNT_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildUserAccountResponse(400, false, message, null, null,
                    null);
        }

        Optional<UserAccount> userAccountRetrieved = userAccountRepository.findByIdAndEntityStatusNot(
                editUserAccountRequest.getId(), EntityStatus.DELETED);

        if (userAccountRetrieved.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ACCOUNT_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildUserAccountResponse(400, false, message, null, null,
                    null);
        }

        UserAccount userAccountToBeEdited = userAccountRetrieved.get();
        userAccountToBeEdited.setFirstName(editUserAccountRequest.getFirstName());
        userAccountToBeEdited.setLastName(editUserAccountRequest.getLastName());
        userAccountToBeEdited.setGender(Gender.valueOf(editUserAccountRequest.getGender()));
        userAccountToBeEdited.setTitle(Title.valueOf(editUserAccountRequest.getTitle()));
        userAccountToBeEdited.setUserGroup(userAccountRetrieved.get().getUserGroup());
        userAccountToBeEdited.setAssembly(assemblyRetrieved.get());

        Optional<UserAccount> checkForDuplicateUserAccount = userAccountRepository.findByPhoneNumberAndEmailAddressAndEntityStatusNot(
                    editUserAccountRequest.getPhoneNumber(), editUserAccountRequest.getEmailAddress(), EntityStatus.DELETED);

        if (checkForDuplicateUserAccount.isPresent()) {

            if (!checkForDuplicateUserAccount.get().getId().equals(editUserAccountRequest.getId())) {

                message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ACCOUNT_ALREADY_EXISTS.getCode(),
                            new String[]{}, locale);

                return buildUserAccountResponse(400, false, message, null,
                            null, null);
            }
        }

        userAccountToBeEdited.setPhoneNumber(editUserAccountRequest.getPhoneNumber());
        userAccountToBeEdited.setEmailAddress(editUserAccountRequest.getEmailAddress());

        UserAccount userAccountEdited = userAccountServiceAuditable.edit(userAccountToBeEdited, locale, username);

        UserAccountDto userAccountDtoReturned = modelMapper.map(userAccountEdited, UserAccountDto.class);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ACCOUNT_EDITED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildUserAccountResponse(201, true, message, userAccountDtoReturned, null,
                null);
    }

    @Override
    public UserAccountResponse delete(Long id, Locale locale) {

        String message = "";

        boolean isIdValid = userAccountServiceValidator.isIdValid(id);

        if (!isIdValid) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_INVALID_USER_ACCOUNT_ID_SUPPLIED.getCode(), new String[]{},
                    locale);

            return buildUserAccountResponse(400, false, message, null, null,
                    null);
        }

        Optional<UserAccount> userAccountRetrieved = userAccountRepository.findByIdAndEntityStatusNot(id, EntityStatus.DELETED);

        if (userAccountRetrieved.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ACCOUNT_DOES_NOT_EXIST.getCode(), new String[]{},
                    locale);

            return buildUserAccountResponse(404, false, message, null, null,
                    null);
        }

        UserAccount userAccountToBeDeleted = userAccountRetrieved.get();
        userAccountToBeDeleted.setEntityStatus(EntityStatus.DELETED);
        userAccountToBeDeleted.setEmailAddress(userAccountToBeDeleted.getEmailAddress().replace(" ", "_") +
                LocalDateTime.now());

        UserAccount userAccountDeleted = userAccountServiceAuditable.delete(userAccountToBeDeleted, locale);

        UserAccountDto userAccountDtoReturned = modelMapper.map(userAccountDeleted, UserAccountDto.class);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ACCOUNT_DELETED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildUserAccountResponse(200, true, message, userAccountDtoReturned, null,
                null);
    }

    @Override
    public UserAccountResponse findById(Long id, Locale locale) {

        String message = "";

        boolean isIdValid = userAccountServiceValidator.isIdValid(id);

        if(!isIdValid) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_INVALID_USER_ACCOUNT_ID_SUPPLIED.getCode(), new String[]
                    {}, locale);

            return buildUserAccountResponse(400, false, message, null, null,
                    null);
        }

        Optional<UserAccount> userAccountRetrieved = userAccountRepository.findByIdAndEntityStatusNot(id, EntityStatus.DELETED);

        if (userAccountRetrieved.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ACCOUNT_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildUserAccountResponse(404, false, message, null, null,
                    null);
        }

        UserAccount userAccountReturned = userAccountRetrieved.get();

        UserAccountDto userAccountDto = modelMapper.map(userAccountReturned, UserAccountDto.class);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ACCOUNT_RETRIEVED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildUserAccountResponse(200, true, message, userAccountDto, null,
                null);
    }

    @Override
    public UserAccountResponse findAllAsAList(String username, Locale locale) {

        String message = "";

        List<UserAccount> userAccountList = userAccountRepository.findByEntityStatusNot(EntityStatus.DELETED);

        if(userAccountList.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ACCOUNT_NOT_FOUND.getCode(), new String[]
                    {}, locale);

            return buildUserAccountResponse(404, false, message, null,
                    null, null);
        }

        List<UserAccountDto> userAccountDtoList = modelMapper.map(userAccountList, new TypeToken<List<UserAccountDto>>(){}.getType());

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ACCOUNT_RETRIEVED_SUCCESSFULLY.getCode(),
                new String[]{}, locale);

        return buildUserAccountResponse(200, true, message, null,
                userAccountDtoList, null);
    }

    @Override
    public UserAccountResponse findAllAsPages(int page, int size, Locale locale, String username) {

        String message ="";

        final Pageable pageable = PageRequest.of(page, size);

        Page<UserAccount> userAccountPage = userAccountRepository.findByEntityStatusNot(EntityStatus.DELETED, pageable);

        Page<UserAccountDto> userAccountDtoPage = convertUserAccountEntityToUserAccountDto(userAccountPage);

        if(userAccountPage.getContent().isEmpty()){

            message =  applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_ASSEMBLY_NOT_FOUND.getCode(),
                    new String[]{}, locale);

            return buildUserAccountResponse(404, false, message, null, null,
                    userAccountDtoPage);
        }

        message =  applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ACCOUNT_RETRIEVED_SUCCESSFULLY.getCode(),
                new String[]{}, locale);

        return buildUserAccountResponse(200, true, message, null,
                null, userAccountDtoPage);
    }

    private Page<UserAccountDto> convertUserAccountEntityToUserAccountDto(Page<UserAccount> userAccountPage){

        List<UserAccount> userAccountList = userAccountPage.getContent();
        List<UserAccountDto> userAccountDtoList = new ArrayList<>();

        for (UserAccount userAccount : userAccountPage) {
            UserAccountDto userAccountDto = modelMapper.map(userAccount, UserAccountDto.class);
            userAccountDtoList.add(userAccountDto);
        }

        int page = userAccountPage.getNumber();
        int size = userAccountPage.getSize();

        size = size <= 0 ? 10 : size;

        Pageable pageable = PageRequest.of(page, size);

        return new PageImpl<UserAccountDto>(userAccountDtoList, pageable, userAccountPage.getTotalElements());
    }

    private UserAccountResponse buildUserAccountResponse(int statusCode, boolean isSuccess, String message,
                                                         UserAccountDto userAccountDto, List<UserAccountDto> userAccountDtoList,
                                                         Page<UserAccountDto> userAccountDtoPage){

        UserAccountResponse userAccountResponse = new UserAccountResponse();
        userAccountResponse.setStatusCode(statusCode);
        userAccountResponse.setSuccess(isSuccess);
        userAccountResponse.setMessage(message);
        userAccountResponse.setUserAccountDto(userAccountDto);
        userAccountResponse.setUserAccountDtoList(userAccountDtoList);
        userAccountResponse.setUserAccountDtoPage(userAccountDtoPage);

        return userAccountResponse;
    }
}
