package com.tithe_system.tithe_management_system.business.logic.impl;

import com.tithe_system.tithe_management_system.business.auditables.api.AccountServiceAuditable;
import com.tithe_system.tithe_management_system.business.logic.api.AccountService;
import com.tithe_system.tithe_management_system.business.validations.api.AccountServiceValidator;
import com.tithe_system.tithe_management_system.domain.Account;
import com.tithe_system.tithe_management_system.domain.Currency;
import com.tithe_system.tithe_management_system.domain.Narration;
import com.tithe_system.tithe_management_system.domain.Assembly;
import com.tithe_system.tithe_management_system.domain.EntityStatus;
import com.tithe_system.tithe_management_system.domain.Payment;
import com.tithe_system.tithe_management_system.repository.AccountRepository;
import com.tithe_system.tithe_management_system.repository.AssemblyRepository;
import com.tithe_system.tithe_management_system.repository.UserAccountRepository;
import com.tithe_system.tithe_management_system.repository.specification.AccountSpecification;
import com.tithe_system.tithe_management_system.repository.specification.PaymentSpecification;
import com.tithe_system.tithe_management_system.utils.dtos.AccountDto;
import com.tithe_system.tithe_management_system.utils.dtos.PaymentDto;
import com.tithe_system.tithe_management_system.utils.enums.I18Code;
import com.tithe_system.tithe_management_system.utils.generators.AccountAndReferencesGenerator;
import com.tithe_system.tithe_management_system.utils.i18.api.ApplicationMessagesService;
import com.tithe_system.tithe_management_system.utils.requests.AccountMultipleFilterRequest;
import com.tithe_system.tithe_management_system.utils.requests.CreateAccountRequest;
import com.tithe_system.tithe_management_system.utils.requests.UpdateAccountRequest;
import com.tithe_system.tithe_management_system.utils.responses.AccountResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

public class AccountServiceImpl implements AccountService {
    private final AccountServiceValidator accountServiceValidator;
    private final AccountServiceAuditable accountServiceAuditable;
    private final AccountRepository accountRepository;
    private final AssemblyRepository assemblyRepository;
    private final UserAccountRepository userAccountRepository;
    private final ModelMapper modelMapper;
    private final ApplicationMessagesService applicationMessagesService;

    public AccountServiceImpl(AccountServiceValidator accountServiceValidator, AccountServiceAuditable accountServiceAuditable,
                              AccountRepository accountRepository, AssemblyRepository assemblyRepository, UserAccountRepository userAccountRepository, ModelMapper modelMapper,
                              ApplicationMessagesService applicationMessagesService) {
        this.accountServiceValidator = accountServiceValidator;
        this.accountServiceAuditable = accountServiceAuditable;
        this.accountRepository = accountRepository;
        this.assemblyRepository = assemblyRepository;
        this.userAccountRepository = userAccountRepository;
        this.modelMapper = modelMapper;
        this.applicationMessagesService = applicationMessagesService;
    }

    @Override
    public AccountResponse createAccount(CreateAccountRequest createAccountRequest, String username, Locale locale) {

        String message = "";

        boolean isRequestValid = accountServiceValidator.isCreateRequestValid(createAccountRequest);

        if (!isRequestValid) {
            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_CREATE_ACCOUNT_INVALID_REQUEST.getCode(), new String[]{},
                    locale);

            return buildAccountResponse(400, false, message, null, null,
                    null);
        }

        Optional<Assembly> assemblyRetrieved = assemblyRepository.findByIdAndEntityStatusNot(
                createAccountRequest.getAssemblyId(), EntityStatus.DELETED);

        if (assemblyRetrieved.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_ASSEMBLY_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildAccountResponse(400, false, message, null, null,
                    null);
        }

        createAccountRequest.setAccountNumber(AccountAndReferencesGenerator.getAccountNumber());

        Optional<Account> accountRetrieved = accountRepository.findByAccountNumberAndEntityStatusNot(
                createAccountRequest.getAccountNumber(), EntityStatus.DELETED);

        if (accountRetrieved.isPresent()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_ACCOUNT_ALREADY_EXISTS.getCode(), new String[]{},
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
        accountToBeSaved.setTransactionAmount(BigDecimal.ZERO);
        accountToBeSaved.setTransactionReference(AccountAndReferencesGenerator.getTransactionReference().toString());
        accountToBeSaved.setNarration(Narration.ACCOUNT_CREATION);

        Account accountSaved = accountServiceAuditable.create(accountToBeSaved, locale, username);

        AccountDto accountDtoReturned = modelMapper.map(accountSaved, AccountDto.class);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_ACCOUNT_CREATED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildAccountResponse(201, true, message, accountDtoReturned, null,
                null);
    }

    @Override
    public AccountResponse updateAccountRecords(UpdateAccountRequest updateAccountRequest, String username, Locale locale) {

        String message = "";

        Optional<Account> account = accountRepository.findByAccountNumberAndEntityStatusNot(updateAccountRequest.getAccountNumber(),
                EntityStatus.DELETED);

        if (account.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_ACCOUNT_DOES_NOT_EXIST.getCode(), new String[]{},
                    locale);

            return buildAccountResponse(400, false, message, null, null,
                    null);
        }

        updateAccountRequest.setId(account.get().getId());

       boolean isRequestValid = accountServiceValidator.isUpdateRequestValid(updateAccountRequest);

        if (!isRequestValid) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_UPDATE_ACCOUNT_INVALID_REQUEST.getCode(), new String[]{},
                    locale);

            return buildAccountResponse(400, false, message, null, null,
                    null);
        }

        Optional<Account> accountRetrieved = accountRepository.findByIdAndEntityStatusNot(updateAccountRequest.getId(),
                EntityStatus.DELETED);

        if (accountRetrieved.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_ACCOUNT_DOES_NOT_EXIST.getCode(), new String[]{},
                    locale);

            return buildAccountResponse(400, false, message, null, null,
                    null);
        }

        Account accountRecordToBeUpdated = accountRetrieved.get();
        accountRecordToBeUpdated.setTransactionAmount(updateAccountRequest.getAmount());
        accountRecordToBeUpdated.setNarration(Narration.ACCOUNT_IN_USE);

        if (updateAccountRequest.getNarration().equals(Narration.PAYMENT.toString())) {

            accountRecordToBeUpdated.setDebitBalance(accountRecordToBeUpdated.getDebitBalance().add(updateAccountRequest.getAmount()));
            accountRecordToBeUpdated.setCumulativeBalance(accountRecordToBeUpdated.getCumulativeBalance()
                    .add(updateAccountRequest.getAmount()));
        }

        if (updateAccountRequest.getNarration().equals(Narration.REVERSAL.toString())) {

            accountRecordToBeUpdated.setCreditBalance(accountRecordToBeUpdated.getCreditBalance().add(updateAccountRequest.getAmount()));
            accountRecordToBeUpdated.setCumulativeBalance(accountRecordToBeUpdated.getCumulativeBalance()
                    .subtract(updateAccountRequest.getAmount()));
        }

        Account accountSaved = accountServiceAuditable.update(accountRecordToBeUpdated, locale, username);

        AccountDto accountDtoReturned = modelMapper.map(accountSaved, AccountDto.class);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_ACCOUNT_UPDATED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildAccountResponse(201, false, message, accountDtoReturned, null,
                null);
    }

    @Override
    public AccountResponse findById(Long id, Locale locale) {

        String message = "";

        boolean isIdValid = accountServiceValidator.isIdValid(id);

        if(!isIdValid) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_INVALID_ACCOUNT_ID_SUPPLIED.getCode(), new String[]
                    {}, locale);

            return buildAccountResponse(400, false, message, null, null,
                    null);
        }

        Optional<Account> accountRetrieved = accountRepository.findByIdAndEntityStatusNot(id, EntityStatus.DELETED);

        if (accountRetrieved.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_ACCOUNT_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildAccountResponse(404, false, message, null, null,
                    null);
        }

        Account accountReturned = accountRetrieved.get();

        AccountDto accountDto = modelMapper.map(accountReturned, AccountDto.class);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_ACCOUNT_RETRIEVED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildAccountResponse(200, true, message, accountDto, null,
                null);
    }

    @Override
    public AccountResponse findByMultipleFilters(AccountMultipleFilterRequest accountMultipleFilterRequest, Locale locale, String username) {

        String message = "";

        Specification<Account> spec = null;
        spec = addToSpec(spec, AccountSpecification::deleted);

        boolean isRequestValid = accountServiceValidator.isRequestValidToRetrievePaymentsByMultipleFilters(
                accountMultipleFilterRequest);

        if (!isRequestValid) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_INVALID_ACCOUNTS_MULTIPLE_FILTERS_REQUEST.getCode(),
                    new String[]{}, locale);

            return buildAccountResponse(400, false, message,null, null,
                    null);
        }

        Pageable pageable = PageRequest.of(accountMultipleFilterRequest.getPage(),
                accountMultipleFilterRequest.getSize());

        boolean isAccountNumberValid = accountServiceValidator.isStringValid(
                accountMultipleFilterRequest.getAccountNumber());

        if (isAccountNumberValid) {

            spec = addToSpec(accountMultipleFilterRequest.getAccountNumber(), spec,
                    AccountSpecification::accountNumberLike);
        }

        boolean isTransactionReferenceValid = accountServiceValidator.isStringValid(
                accountMultipleFilterRequest.getTransactionReference());

        if (isTransactionReferenceValid) {

            spec = addToSpec(accountMultipleFilterRequest.getTransactionReference(), spec,
                    AccountSpecification::transactionReferenceLike);
        }

        boolean isNarrationValid =
                accountServiceValidator.isListValid(accountMultipleFilterRequest.getNarration());

        if (isNarrationValid) {

            List<Narration> narrationList = new ArrayList<>();

            for (String narration: accountMultipleFilterRequest.getNarration()
            ) {
                try{
                    narrationList.add(Narration.valueOf(narration));
                }catch (Exception e){}
            }

            spec = addToNarrationSpec(narrationList, spec, AccountSpecification::narrationIn);
        }

        boolean isCurrencyValid =
                accountServiceValidator.isListValid(accountMultipleFilterRequest.getCurrency());

        if (isCurrencyValid) {

            List<Currency> currencyList = new ArrayList<>();

            for (String currency: accountMultipleFilterRequest.getCurrency()
            ) {
                try{
                    currencyList.add(Currency.valueOf(currency));
                }catch (Exception e){}
            }

            spec = addToCurrencySpec(currencyList, spec, AccountSpecification::currencyIn);
        }

        boolean isSearchValueValid = accountServiceValidator.isStringValid(accountMultipleFilterRequest.getSearchValue());

        if (isSearchValueValid) {

            spec = addToSpec(accountMultipleFilterRequest.getSearchValue(), spec, AccountSpecification::any);
        }

        Page<Account> result = accountRepository.findAll(spec, pageable);

        if (result.getContent().isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_ACCOUNT_NOT_FOUND.getCode(),
                    new String[]{}, locale);

            return buildAccountResponse(404, false, message,null, null,
                    null);
        }

        Page<AccountDto> accountDtoPage = convertAccountEntityToAccountDto(result);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_PAYMENT_RETRIEVED_SUCCESSFULLY.getCode(),
                new String[]{}, locale);

        return buildAccountResponse(200, true, message,null,
                null, accountDtoPage);
    }

    private Specification<Account> addToSpec(Specification<Account> spec,
                                             Function<EntityStatus, Specification<Account>> predicateMethod) {
        Specification<Account> localSpec = Specification.where(predicateMethod.apply(EntityStatus.DELETED));
        spec = (spec == null) ? localSpec : spec.and(localSpec);
        return spec;
    }

    private Specification<Account> addToSpec(final String aString, Specification<Account> spec, Function<String,
            Specification<Account>> predicateMethod) {
        if (aString != null && !aString.isEmpty()) {
            Specification<Account> localSpec = Specification.where(predicateMethod.apply(aString));
            spec = (spec == null) ? localSpec : spec.and(localSpec);
            return spec;
        }
        return spec;
    }

    private Specification<Account> addToNarrationSpec(final List<Narration> narrationList, Specification<Account> spec,
                                                      Function<List<Narration>, Specification<Account>> predicateMethod) {
        if (narrationList != null && !narrationList.isEmpty()) {
            Specification<Account> localSpec = Specification.where(predicateMethod.apply(narrationList));
            spec = (spec == null) ? localSpec : spec.and(localSpec);
            return spec;
        }
        return spec;
    }

    private Specification<Account> addToCurrencySpec(final List<Currency> currencyList, Specification<Account> spec,
                                                     Function<List<Currency>, Specification<Account>> predicateMethod) {
        if (currencyList != null && !currencyList.isEmpty()) {
            Specification<Account> localSpec = Specification.where(predicateMethod.apply(currencyList));
            spec = (spec == null) ? localSpec : spec.and(localSpec);
            return spec;
        }
        return spec;
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
