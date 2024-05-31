package com.tithe_system.tithe_management_system.business.logic.impl;

import com.tithe_system.tithe_management_system.business.auditables.api.AccountServiceAuditable;
import com.tithe_system.tithe_management_system.business.logic.api.AccountService;
import com.tithe_system.tithe_management_system.business.validations.api.AccountServiceValidator;
import com.tithe_system.tithe_management_system.repository.AccountRepository;
import com.tithe_system.tithe_management_system.utils.dtos.AccountDto;
import com.tithe_system.tithe_management_system.utils.dtos.AssemblyDto;
import com.tithe_system.tithe_management_system.utils.enums.I18Code;
import com.tithe_system.tithe_management_system.utils.i18.api.MessageService;
import com.tithe_system.tithe_management_system.utils.requests.UpdateAccountRequest;
import com.tithe_system.tithe_management_system.utils.responses.AccountResponse;
import com.tithe_system.tithe_management_system.utils.responses.AssemblyResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Locale;

public class AccountServiceImpl implements AccountService {
    private final AccountServiceValidator accountServiceValidator;
    private final AccountServiceAuditable accountServiceAuditable;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final MessageService messageService;

    public AccountServiceImpl(AccountServiceValidator accountServiceValidator, AccountServiceAuditable accountServiceAuditable,
                              AccountRepository accountRepository, ModelMapper modelMapper,
                              MessageService messageService) {
        this.accountServiceValidator = accountServiceValidator;
        this.accountServiceAuditable = accountServiceAuditable;
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
        this.messageService = messageService;
    }

    @Override
    public AccountResponse updateAccount(UpdateAccountRequest updateAccountRequest, String username, Locale locale) {

        String message = "";

       boolean isRequestValid = accountServiceValidator.isUpdateRequestValid(updateAccountRequest);

        if (!isRequestValid) {
            message = messageService.getMessage(I18Code.MESSAGE_CREATE_ACCOUNT_INVALID_REQUEST.getCode(), new String[]{},
                    locale);

            return buildAccountResponse(400, false, message, null, null,
                    null);
        }

        return null;
    }

    private AccountResponse buildAccountResponse(int statusCode, boolean isSuccess, String message,
                                                 AccountDto accountDto, List<AccountDto> accountDtoList,
                                                 Page<AccountDto> accountDtoPage){

        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setStatusCode(statusCode);
        accountResponse.setSuccess(isSuccess);
        accountResponse.setMessage(message);
        accountResponse.setAccountDto(accountDto);
        accountResponse.setAccountDtoList(accountDtoList);
        accountResponse.setAccountDtoPage(accountDtoPage);

        return accountResponse;
    }
}
