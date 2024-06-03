package com.tithe_system.tithe_management_system.business.logic.impl;

import com.tithe_system.tithe_management_system.business.auditables.api.AccountServiceAuditable;
import com.tithe_system.tithe_management_system.business.logic.api.AccountService;
import com.tithe_system.tithe_management_system.business.validations.api.AccountServiceValidator;
import com.tithe_system.tithe_management_system.domain.Account;
import com.tithe_system.tithe_management_system.domain.Narration;
import com.tithe_system.tithe_management_system.domain.Assembly;
import com.tithe_system.tithe_management_system.domain.EntityStatus;
import com.tithe_system.tithe_management_system.repository.AccountRepository;
import com.tithe_system.tithe_management_system.repository.AssemblyRepository;
import com.tithe_system.tithe_management_system.repository.UserAccountRepository;
import com.tithe_system.tithe_management_system.utils.dtos.AccountDto;
import com.tithe_system.tithe_management_system.utils.enums.I18Code;
import com.tithe_system.tithe_management_system.utils.generators.AccountAndReferencesGenerator;
import com.tithe_system.tithe_management_system.utils.i18.api.MessageService;
import com.tithe_system.tithe_management_system.utils.requests.CreateAccountRequest;
import com.tithe_system.tithe_management_system.utils.requests.UpdateAccountRequest;
import com.tithe_system.tithe_management_system.utils.responses.AccountResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class AccountServiceImpl implements AccountService {
    private final AccountServiceValidator accountServiceValidator;
    private final AccountServiceAuditable accountServiceAuditable;
    private final AccountRepository accountRepository;
    private final AssemblyRepository assemblyRepository;
    private final UserAccountRepository userAccountRepository;
    private final ModelMapper modelMapper;
    private final MessageService messageService;

    public AccountServiceImpl(AccountServiceValidator accountServiceValidator, AccountServiceAuditable accountServiceAuditable,
                              AccountRepository accountRepository, AssemblyRepository assemblyRepository, UserAccountRepository userAccountRepository, ModelMapper modelMapper,
                              MessageService messageService) {
        this.accountServiceValidator = accountServiceValidator;
        this.accountServiceAuditable = accountServiceAuditable;
        this.accountRepository = accountRepository;
        this.assemblyRepository = assemblyRepository;
        this.userAccountRepository = userAccountRepository;
        this.modelMapper = modelMapper;
        this.messageService = messageService;
    }

    @Override
    public AccountResponse createAccount(CreateAccountRequest createAccountRequest, String username, Locale locale) {

        String message = "";

        boolean isRequestValid = accountServiceValidator.isCreateRequestValid(createAccountRequest);

        if (!isRequestValid) {
            message = messageService.getMessage(I18Code.MESSAGE_CREATE_ACCOUNT_INVALID_REQUEST.getCode(), new String[]{},
                    locale);

            return buildAccountResponse(400, false, message, null, null,
                    null);
        }

        Optional<Assembly> assemblyRetrieved = assemblyRepository.findByIdAndEntityStatusNot(
                createAccountRequest.getAssemblyId(), EntityStatus.DELETED);

        if (assemblyRetrieved.isEmpty()) {

            message = messageService.getMessage(I18Code.MESSAGE_ASSEMBLY_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildAccountResponse(400, false, message, null, null,
                    null);
        }

        createAccountRequest.setAccountNumber(AccountAndReferencesGenerator.getAccountNumber());

        Optional<Account> accountRetrieved = accountRepository.findByAccountNumberAndEntityStatusNot(
                createAccountRequest.getAccountNumber(), EntityStatus.DELETED);

        if (accountRetrieved.isPresent()) {

            message = messageService.getMessage(I18Code.MESSAGE_ACCOUNT_ALREADY_EXISTS.getCode(), new String[]{},
                    locale);

            return buildAccountResponse(400, false, message, null, null,
                    null);
        }

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Account accountToBeSaved = modelMapper.map(createAccountRequest, Account.class);
        accountToBeSaved.setAssembly(assemblyRetrieved.get());
        accountToBeSaved.setCreditBalance(BigDecimal.ZERO);
        accountToBeSaved.setDebitBalance(BigDecimal.ZERO);
        accountToBeSaved.setCumulativeBalance(BigDecimal.ZERO);
        accountToBeSaved.setTransactionReference(AccountAndReferencesGenerator.getTransactionReference().toString());
        accountToBeSaved.setNarration(Narration.ACCOUNT_CREATION.getAccountNarration());

        Account accountSaved = accountServiceAuditable.create(accountToBeSaved, locale, username);

        AccountDto accountDtoReturned = modelMapper.map(accountSaved, AccountDto.class);

        message = messageService.getMessage(I18Code.MESSAGE_ACCOUNT_CREATED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildAccountResponse(201, true, message, accountDtoReturned, null,
                null);
    }

    @Override
    public AccountResponse updateAccount(UpdateAccountRequest updateAccountRequest, String username, Locale locale) {

        String message = "";

       boolean isRequestValid = accountServiceValidator.isUpdateRequestValid(updateAccountRequest);

        if (!isRequestValid) {
            message = messageService.getMessage(I18Code.MESSAGE_UPDATE_ACCOUNT_INVALID_REQUEST.getCode(), new String[]{},
                    locale);

            return buildAccountResponse(400, false, message, null, null,
                    null);
        }

        Optional<Account> accountRetrieved = accountRepository.findByIdAndEntityStatusNot(updateAccountRequest.getId(), EntityStatus.DELETED);

        if (accountRetrieved.isEmpty()) {
            message = messageService.getMessage(I18Code.MESSAGE_ACCOUNT_DOES_NOT_EXIST.getCode(), new String[]{},
                    locale);
            return buildAccountResponse(404, false, message, null, null,
                    null);
        }

        Account accountToBeUpdated = accountRetrieved.get();

        if (updateAccountRequest.getNarration().equals(Narration.PAYMENT.toString())) {

            accountToBeUpdated.setDebitBalance(updateAccountRequest.getAmount());
            accountToBeUpdated.setCumulativeBalance(accountRetrieved.get().getCumulativeBalance()
                    .add(updateAccountRequest.getAmount()));
        }

        if (updateAccountRequest.getNarration().equals(Narration.REVERSAL.toString())) {

            accountToBeUpdated.setCreditBalance(updateAccountRequest.getAmount());
            accountToBeUpdated.setCumulativeBalance(accountRetrieved.get().getCumulativeBalance()
                    .subtract(updateAccountRequest.getAmount()));
        }

        Account accountSaved = accountServiceAuditable.update(accountToBeUpdated, locale, username);

        AccountDto accountDtoReturned = modelMapper.map(accountSaved, AccountDto.class);

        message = messageService.getMessage(I18Code.MESSAGE_ACCOUNT_UPDATED_SUCCESSFULLY.getCode(), new String[]{},
                locale);
        return buildAccountResponse(201, false, message, null, null,
                null);
    }

    @Override
    public AccountResponse findById(Long id, Locale locale) {

        String message = "";

        boolean isIdValid = accountServiceValidator.isIdValid(id);

        if(!isIdValid) {

            message = messageService.getMessage(I18Code.MESSAGE_INVALID_ACCOUNT_ID_SUPPLIED.getCode(), new String[]
                    {}, locale);

            return buildAccountResponse(400, false, message, null, null,
                    null);
        }

        Optional<Account> accountRetrieved = accountRepository.findByIdAndEntityStatusNot(id, EntityStatus.DELETED);

        if (accountRetrieved.isEmpty()) {

            message = messageService.getMessage(I18Code.MESSAGE_ACCOUNT_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildAccountResponse(404, false, message, null, null,
                    null);
        }

        Account accountReturned = accountRetrieved.get();

        AccountDto accountDto = modelMapper.map(accountReturned, AccountDto.class);

        message = messageService.getMessage(I18Code.MESSAGE_ACCOUNT_RETRIEVED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildAccountResponse(200, true, message, accountDto, null,
                null);
    }


    @Override
    public AccountResponse findAllAsPages(int page, int size, Locale locale, String username) {

        String message ="";

        final Pageable pageable = PageRequest.of(page, size);

        Page<Account> accountPage = accountRepository.findByEntityStatusNot(EntityStatus.DELETED, pageable);

        Page<AccountDto> accountDtoPage = convertAccountEntityToAccountDto(accountPage);

        if(accountDtoPage.getContent().isEmpty()){

            message =  messageService.getMessage(I18Code.MESSAGE_ACCOUNT_NOT_FOUND.getCode(),
                    new String[]{}, locale);

            return buildAccountResponse(404, false, message, null, null,
                    accountDtoPage);
        }

        message =  messageService.getMessage(I18Code.MESSAGE_ACCOUNT_RETRIEVED_SUCCESSFULLY.getCode(),
                new String[]{}, locale);

        return buildAccountResponse(200, true, message, null,
                null, accountDtoPage);
    }

    private Page<AccountDto> convertAccountEntityToAccountDto(Page<Account> accountPage){

        List<Account> accountList = accountPage.getContent();
        List<AccountDto> accountDtoList = new ArrayList<>();

        for (Account account : accountPage) {
            AccountDto accountDto = modelMapper.map(account, AccountDto.class);
            accountDtoList.add(accountDto);
        }

        int page = accountPage.getNumber();
        int size = accountPage.getSize();

        size = size <= 0 ? 10 : size;

        Pageable pageableAccounts = PageRequest.of(page, size);

        return new PageImpl<AccountDto>(accountDtoList, pageableAccounts, accountPage.getTotalElements());
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
