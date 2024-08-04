package com.tithe_system.tithe_management_system.business.logic.impl;

import com.tithe_system.tithe_management_system.business.auditables.api.PaymentServiceAuditable;
import com.tithe_system.tithe_management_system.business.logic.api.AccountService;
import com.tithe_system.tithe_management_system.business.logic.api.PaymentService;
import com.tithe_system.tithe_management_system.business.validations.api.PaymentServiceValidator;
import com.tithe_system.tithe_management_system.domain.Account;
import com.tithe_system.tithe_management_system.domain.Assembly;
import com.tithe_system.tithe_management_system.domain.Currency;
import com.tithe_system.tithe_management_system.domain.EntityStatus;
import com.tithe_system.tithe_management_system.domain.Narration;
import com.tithe_system.tithe_management_system.domain.Payment;
import com.tithe_system.tithe_management_system.domain.PaymentChannel;
import com.tithe_system.tithe_management_system.domain.PaymentMethod;
import com.tithe_system.tithe_management_system.domain.PaymentStatus;
import com.tithe_system.tithe_management_system.domain.PaymentType;
import com.tithe_system.tithe_management_system.domain.UserAccount;
import com.tithe_system.tithe_management_system.repository.AccountRepository;
import com.tithe_system.tithe_management_system.repository.AssemblyRepository;
import com.tithe_system.tithe_management_system.repository.PaymentRepository;
import com.tithe_system.tithe_management_system.repository.UserAccountRepository;
import com.tithe_system.tithe_management_system.repository.specification.PaymentSpecification;
import com.tithe_system.tithe_management_system.utils.dtos.AccountDto;
import com.tithe_system.tithe_management_system.utils.dtos.AssemblyDto;
import com.tithe_system.tithe_management_system.utils.dtos.PaymentDto;
import com.tithe_system.tithe_management_system.utils.enums.I18Code;
import com.tithe_system.tithe_management_system.utils.generators.AccountAndReferencesGenerator;
import com.tithe_system.tithe_management_system.utils.i18.api.ApplicationMessagesService;
import com.tithe_system.tithe_management_system.utils.requests.ChangePaymentStatusRequest;
import com.tithe_system.tithe_management_system.utils.requests.CreatePaymentRequest;
import com.tithe_system.tithe_management_system.utils.requests.PaymentMultipleFilterRequest;
import com.tithe_system.tithe_management_system.utils.requests.ReversePaymentRequest;
import com.tithe_system.tithe_management_system.utils.requests.UpdateAccountRequest;
import com.tithe_system.tithe_management_system.utils.responses.AccountResponse;
import com.tithe_system.tithe_management_system.utils.responses.PaymentResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class PaymentServiceImpl implements PaymentService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final PaymentServiceValidator paymentServiceValidator;
    private final PaymentRepository paymentRepository;
    private final AssemblyRepository assemblyRepository;
    private final UserAccountRepository userAccountRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final PaymentServiceAuditable paymentServiceAuditable;
    private final ApplicationMessagesService applicationMessagesService;
    private final AccountService accountService;

    public PaymentServiceImpl(PaymentServiceValidator paymentServiceValidator, PaymentRepository paymentRepository,
                              AssemblyRepository assemblyRepository, UserAccountRepository userAccountRepository,
                              AccountRepository accountRepository, ModelMapper modelMapper, PaymentServiceAuditable
                                      paymentServiceAuditable, ApplicationMessagesService applicationMessagesService, AccountService
                                      accountService) {
        this.paymentServiceValidator = paymentServiceValidator;
        this.paymentRepository = paymentRepository;
        this.assemblyRepository = assemblyRepository;
        this.userAccountRepository = userAccountRepository;
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
        this.paymentServiceAuditable = paymentServiceAuditable;
        this.applicationMessagesService = applicationMessagesService;
        this.accountService = accountService;
    }

    @Override
    public PaymentResponse create(CreatePaymentRequest createPaymentRequest, String username, Locale locale) {

        String message = "";

        boolean isRequestValid = paymentServiceValidator.isRequestValidForCreation(createPaymentRequest);

        if (!isRequestValid) {
            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_CREATE_PAYMENT_INVALID_REQUEST.getCode(), new String[]{},
                    locale);

            return buildPaymentResponse(400, false, message, null, null,
                    null);
        }

        Optional<Assembly> assemblyRetrieved = assemblyRepository.findByIdAndEntityStatusNot(
                createPaymentRequest.getAssemblyId(), EntityStatus.DELETED);

        if (assemblyRetrieved.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_ASSEMBLY_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildPaymentResponse(400, false, message, null, null,
                    null);
        }

        Optional<UserAccount> userAccountRetrieved = userAccountRepository.findByIdAndEntityStatusNot(
                createPaymentRequest.getUserAccountId(), EntityStatus.DELETED);

        if (userAccountRetrieved.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ACCOUNT_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildPaymentResponse(400, false, message, null, null,
                    null);
        }

        Optional<Account> accountRetrieved = accountRepository.findByIdAndEntityStatusNot(
                createPaymentRequest.getUserAccountId(), EntityStatus.DELETED);

        if (accountRetrieved.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_ACCOUNT_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildPaymentResponse(400, false, message, null, null,
                    null);
        }

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Payment paymentToBeSaved = modelMapper.map(createPaymentRequest, Payment.class);

        paymentToBeSaved.setTransactionReference(AccountAndReferencesGenerator.getTransactionReference().toString());
        paymentToBeSaved.setAssembly(assemblyRetrieved.get());
        paymentToBeSaved.setUserAccount(userAccountRetrieved.get());
        paymentToBeSaved.setPaymentStatus(PaymentStatus.INITIATED);
        paymentToBeSaved.setNarration(Narration.PAYMENT);
        paymentToBeSaved.setAccountNumber(accountRetrieved.get().getAccountNumber());

        try {

            paymentToBeSaved.setPopUrl(createPaymentRequest.getProofOfPayment().getBytes());
            paymentToBeSaved.setPopName(createPaymentRequest.getProofOfPayment().getOriginalFilename());

        } catch (IOException e) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_CREATE_PAYMENT_INVALID_REQUEST.getCode(), new String[]{},
                    locale);

            return buildPaymentResponse(400, false, message, null, null,
                    null);
        }

        Payment paymentSaved = paymentServiceAuditable.create(paymentToBeSaved, locale, username);

        UpdateAccountRequest updateAccountRequest = buildUpdateAccountRequest(paymentSaved, accountRetrieved.get());

        logger.info("Incoming request to persist payment to an account : {}", updateAccountRequest);

        AccountResponse response = accountService.updateAccountRecords(updateAccountRequest, username, locale);

        logger.info("Outgoing response after persisting a payment to an account : {}", response);

        AccountDto accountDto = modelMapper.map(accountRetrieved.get(), AccountDto.class);

        AssemblyDto assemblyDto = modelMapper.map(assemblyRetrieved.get(), AssemblyDto.class);

        if (paymentSaved.getCurrency() == Currency.USD) {
            assemblyDto.setUsdAccountDto(accountDto);
        } else {
            assemblyDto.setLocalCurrencyAccountDto(accountDto);
        }

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        PaymentDto paymentDtoReturned = modelMapper.map(paymentSaved, PaymentDto.class);
        paymentDtoReturned.setAssemblyDto(assemblyDto);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_PAYMENT_INITIATED_SUCCESSFULLY.getCode(),
                new String[]{}, locale);

        return buildPaymentResponse(201, true, message, paymentDtoReturned, null,
                null);
    }

    @Override
    public PaymentResponse reverse(ReversePaymentRequest reversePaymentRequest, String username, Locale locale) {

        String message = "";

        boolean isRequestValid = paymentServiceValidator.isRequestValidForReversal(reversePaymentRequest);

        if (!isRequestValid) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_REVERSE_PAYMENT_INVALID_REQUEST.getCode(), new String[]{},
                    locale);

            return buildPaymentResponse(400, false, message, null, null,
                    null);
        }

        Optional<Payment> paymentRetrieved = paymentRepository.findByTransactionReferenceAndPaymentStatusNotAndEntityStatusNot(
                reversePaymentRequest.getTransactionReference(), PaymentStatus.REVERSED, EntityStatus.DELETED);

        if (paymentRetrieved.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_PAYMENT_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildPaymentResponse(400, false, message, null, null,
                    null);
        }

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Payment paymentToBeSaved = modelMapper.map(reversePaymentRequest, Payment.class);
        paymentToBeSaved.setTransactionReference(AccountAndReferencesGenerator.getTransactionReference().toString());
        paymentToBeSaved.setAssembly(paymentRetrieved.get().getAssembly());
        paymentToBeSaved.setUserAccount(paymentRetrieved.get().getUserAccount());
        paymentToBeSaved.setPaymentStatus(PaymentStatus.REVERSAL);
        paymentToBeSaved.setNarration(Narration.REVERSAL);
        paymentToBeSaved.setAccountNumber(paymentRetrieved.get().getAccountNumber());
        paymentToBeSaved.setCurrency(paymentRetrieved.get().getCurrency());
        paymentToBeSaved.setAmount(paymentRetrieved.get().getAmount());
        paymentToBeSaved.setPaymentChannel(paymentRetrieved.get().getPaymentChannel());
        paymentToBeSaved.setPopUrl(paymentRetrieved.get().getPopUrl());
        paymentToBeSaved.setPopName(paymentRetrieved.get().getPopName());
        paymentToBeSaved.setPaymentMethod(paymentRetrieved.get().getPaymentMethod());
        paymentToBeSaved.setPaymentType(paymentRetrieved.get().getPaymentType());

        Payment paymentSaved = paymentServiceAuditable.create(paymentToBeSaved, locale, username);

        Payment paymentRecordToBeUpdated = paymentRetrieved.get();
        paymentRecordToBeUpdated.setPaymentStatus(PaymentStatus.REVERSED);

        Payment paymentUpdated = paymentServiceAuditable.update(paymentRecordToBeUpdated, locale, username);

        UpdateAccountRequest updateAccountRequest = buildUpdateAccountRequest(reversePaymentRequest, paymentSaved);

        logger.info("Incoming request to persist reversal to an account : {}", updateAccountRequest);

        AccountResponse response = accountService.updateAccountRecords(updateAccountRequest, username, locale);

        logger.info("Outgoing response after persisting a reversal to an account : {}", response);

        Optional<Account> accountRetrieved = accountRepository.findByAccountNumberAndEntityStatusNot(
                paymentSaved.getAccountNumber(), EntityStatus.DELETED);

        if (accountRetrieved.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_ACCOUNT_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildPaymentResponse(400, false, message, null, null,
                    null);
        }

        AccountDto accountDto = modelMapper.map(accountRetrieved.get(), AccountDto.class);

        AssemblyDto assemblyDto = modelMapper.map(accountRetrieved.get().getAssembly(), AssemblyDto.class);

        if (paymentSaved.getCurrency() == Currency.USD) {
            assemblyDto.setUsdAccountDto(accountDto);
        } else {
            assemblyDto.setLocalCurrencyAccountDto(accountDto);
        }

        PaymentDto paymentDtoReturned = modelMapper.map(paymentSaved, PaymentDto.class);
        paymentDtoReturned.setAssemblyDto(assemblyDto);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_PAYMENT_REVERSED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildPaymentResponse(201, true, message, paymentDtoReturned, null,
                null);
    }

    @Override
    public PaymentResponse findById(Long id, Locale locale) {

        String message = "";

        boolean isIdValid = paymentServiceValidator.isIdValid(id);

        if(!isIdValid) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_INVALID_PAYMENT_ID_SUPPLIED.getCode(), new String[]
                    {}, locale);

            return buildPaymentResponse(400, false, message, null, null,
                    null);
        }

        Optional<Payment> paymentRetrieved = paymentRepository.findByIdAndEntityStatusNot(id, EntityStatus.DELETED);

        if (paymentRetrieved.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_PAYMENT_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildPaymentResponse(404, false, message, null, null,
                    null);
        }

        Payment paymentReturned = paymentRetrieved.get();

        PaymentDto paymentDto = modelMapper.map(paymentReturned, PaymentDto.class);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_PAYMENT_RETRIEVED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildPaymentResponse(200, true, message, paymentDto, null,
                null);
    }

    @Override
    public PaymentResponse findPaymentsAsPages(int page, int size, Locale locale, String username) {

        String message ="";

        final Pageable pageable = PageRequest.of(page, size);

        Page<Payment> paymentPage = paymentRepository.findByEntityStatusNot(EntityStatus.DELETED, pageable);

        Page<PaymentDto> paymentDtoPage = convertPaymentEntityToPaymentDto(paymentPage);

        if(paymentDtoPage.getContent().isEmpty()){

            message =  applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_PAYMENT_NOT_FOUND.getCode(),
                    new String[]{}, locale);

            return buildPaymentResponse(404, false, message, null, null,
                    paymentDtoPage);
        }

        message =  applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_PAYMENT_RETRIEVED_SUCCESSFULLY.getCode(),
                new String[]{}, locale);

        return buildPaymentResponse(200, true, message, null,
                null, paymentDtoPage);
    }

    @Override
    public PaymentResponse changePaymentStatus(ChangePaymentStatusRequest changePaymentStatusRequest, String username, Locale locale) {

        String message = "";

        boolean isRequestValid = paymentServiceValidator.isRequestValidForChangingPaymentStatus(changePaymentStatusRequest);

        if (!isRequestValid) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_CHANGE_PAYMENT_STATUS_INVALID_REQUEST.getCode(), new String[]{},
                    locale);

            return buildPaymentResponse(400, false, message, null, null,
                    null);
        }

        Optional<Payment> paymentRetrieved = paymentRepository.findByIdAndEntityStatusNot(changePaymentStatusRequest.getPaymentId(),
                EntityStatus.DELETED);

        if (paymentRetrieved.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_PAYMENT_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildPaymentResponse(400, false, message, null, null,
                    null);
        }

        Payment paymentToBeUpdated = paymentRetrieved.get();

        if (changePaymentStatusRequest.getPaymentStatus().equals(PaymentStatus.REVERSED.getPaymentStatus()) ||
               changePaymentStatusRequest.getPaymentStatus().equals(PaymentStatus.REVERSAL.getPaymentStatus()) ||
        paymentToBeUpdated.getPaymentStatus().equals(PaymentStatus.REVERSED) ||
        paymentToBeUpdated.getPaymentStatus().equals(PaymentStatus.REVERSAL)) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_CANNOT_CHANGE_PAYMENT_STATUS.getCode(), new String[]{},
                    locale);

            return buildPaymentResponse(400, false, message, null, null,
                    null);
        }

        paymentToBeUpdated.setPaymentStatus(PaymentStatus.valueOf(changePaymentStatusRequest.getPaymentStatus()));

        Payment paymentSaved = paymentServiceAuditable.update(paymentToBeUpdated, locale, username);

        PaymentDto paymentDto = modelMapper.map(paymentSaved, PaymentDto.class);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_PAYMENT_STATUS_CHANGED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildPaymentResponse(200, true, message, paymentDto, null,
                null);
    }

    @Override
    public PaymentResponse findByMultipleFilters(PaymentMultipleFilterRequest paymentMultipleFilterRequest, Locale locale, String username) {

        String message = "";

        Specification<Payment> spec = null;
        spec = addToSpec(spec, PaymentSpecification::deleted);

        boolean isRequestValid = paymentServiceValidator.isRequestValidToRetrievePaymentsByMultipleFilters(
                paymentMultipleFilterRequest);

        if (!isRequestValid) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_INVALID_PAYMENTS_MULTIPLE_FILTERS_REQUEST.getCode(),
                    new String[]{}, locale);

            return buildPaymentResponse(400, false, message,null, null,
                    null);
        }

        Pageable pageable = PageRequest.of(paymentMultipleFilterRequest.getPage(),
                paymentMultipleFilterRequest.getSize());

        boolean isTransactionReferenceValid = paymentServiceValidator.isStringValid(
                paymentMultipleFilterRequest.getTransactionReference());

        if (isTransactionReferenceValid) {

            spec = addToSpec(paymentMultipleFilterRequest.getTransactionReference(), spec,
                    PaymentSpecification::transactionReferenceLike);
        }

        boolean isAccountNumberValid = paymentServiceValidator.isStringValid(
                paymentMultipleFilterRequest.getAccountNumber());

        if (isAccountNumberValid) {

            spec = addToSpec(paymentMultipleFilterRequest.getAccountNumber(), spec,
                    PaymentSpecification::accountNumberLike);
        }

        boolean isNarrationValid =
                paymentServiceValidator.isListValid(paymentMultipleFilterRequest.getNarration());

        if (isNarrationValid) {

            List<Narration> narrationList = new ArrayList<>();

            for (String tariffType: paymentMultipleFilterRequest.getNarration()
            ) {
                try{
                    narrationList.add(Narration.valueOf(tariffType));
                }catch (Exception e){}
            }

            spec = addToNarrationSpec(narrationList, spec, PaymentSpecification::narrationIn);
        }

        boolean isPaymentChannelValid =
                paymentServiceValidator.isListValid(paymentMultipleFilterRequest.getPaymentChannel());

        if (isPaymentChannelValid) {

            List<PaymentChannel> paymentChannelList = new ArrayList<>();

            for (String paymentChannel: paymentMultipleFilterRequest.getPaymentChannel()
            ) {
                try{
                    paymentChannelList.add(PaymentChannel.valueOf(paymentChannel));
                }catch (Exception e){}
            }

            spec = addToPaymentChannelSpec(paymentChannelList, spec, PaymentSpecification::paymentChannelIn);
        }

        boolean isPaymentTypeValid =
                paymentServiceValidator.isListValid(paymentMultipleFilterRequest.getPaymentType());

        if (isPaymentTypeValid) {

            List<PaymentType> paymentTypeList = new ArrayList<>();

            for (String paymentType: paymentMultipleFilterRequest.getPaymentType()
            ) {
                try{
                    paymentTypeList.add(PaymentType.valueOf(paymentType));
                }catch (Exception e){}
            }

            spec = addToPaymentTypeSpec(paymentTypeList, spec, PaymentSpecification::paymentTypeIn);
        }

        boolean isCurrencyValid =
                paymentServiceValidator.isListValid(paymentMultipleFilterRequest.getCurrency());

        if (isCurrencyValid) {

            List<Currency> currencyList = new ArrayList<>();

            for (String currency: paymentMultipleFilterRequest.getCurrency()
            ) {
                try{
                    currencyList.add(Currency.valueOf(currency));
                }catch (Exception e){}
            }

            spec = addToCurrencyTypeSpec(currencyList, spec, PaymentSpecification::currencyIn);
        }

        boolean isPaymentMethodValid =
                paymentServiceValidator.isListValid(paymentMultipleFilterRequest.getPaymentMethod());

        if (isPaymentMethodValid) {

            List<PaymentMethod> paymentMethodList = new ArrayList<>();

            for (String paymentMethod: paymentMultipleFilterRequest.getPaymentMethod()
            ) {
                try{
                    paymentMethodList.add(PaymentMethod.valueOf(paymentMethod));
                }catch (Exception e){}
            }

            spec = addToPaymentMethodSpec(paymentMethodList, spec, PaymentSpecification::paymentMethodIn);
        }

        boolean isPaymentStatusValid =
                paymentServiceValidator.isListValid(paymentMultipleFilterRequest.getPaymentStatus());

        if (isPaymentStatusValid) {

            List<PaymentStatus> paymentStatusList = new ArrayList<>();

            for (String paymentStatus: paymentMultipleFilterRequest.getPaymentStatus()
            ) {
                try{
                    paymentStatusList.add(PaymentStatus.valueOf(paymentStatus));
                }catch (Exception e){}
            }

            spec = addToPaymentStatusSpec(paymentStatusList, spec, PaymentSpecification::paymentStatusIn);
        }

        boolean isSearchValueValid = paymentServiceValidator.isStringValid(paymentMultipleFilterRequest.getSearchValue());

        if (isSearchValueValid) {

            spec = addToSpec(paymentMultipleFilterRequest.getSearchValue(), spec, PaymentSpecification::any);
        }

        Page<Payment> result = paymentRepository.findAll(spec, pageable);

        if (result.getContent().isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_PAYMENT_NOT_FOUND.getCode(),
                    new String[]{}, locale);

            return buildPaymentResponse(404, false, message,null, null,
                    null);
        }

        Page<PaymentDto> paymentDtoPage = convertPaymentEntityToPaymentDto(result);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_PAYMENT_RETRIEVED_SUCCESSFULLY.getCode(),
                new String[]{}, locale);

        return buildPaymentResponse(200, true, message,null,
                null, paymentDtoPage);
    }

    private Specification<Payment> addToSpec(Specification<Payment> spec,
                                                       Function<EntityStatus, Specification<Payment>> predicateMethod) {
        Specification<Payment> localSpec = Specification.where(predicateMethod.apply(EntityStatus.DELETED));
        spec = (spec == null) ? localSpec : spec.and(localSpec);
        return spec;
    }

    private Specification<Payment> addToSpec(final String aString, Specification<Payment> spec, Function<String,
            Specification<Payment>> predicateMethod) {
        if (aString != null && !aString.isEmpty()) {
            Specification<Payment> localSpec = Specification.where(predicateMethod.apply(aString));
            spec = (spec == null) ? localSpec : spec.and(localSpec);
            return spec;
        }
        return spec;
    }

    private Specification<Payment> addToNarrationSpec(final List<Narration> narrationList, Specification<Payment> spec,
                                                                 Function<List<Narration>, Specification<Payment>> predicateMethod) {
        if (narrationList != null && !narrationList.isEmpty()) {
            Specification<Payment> localSpec = Specification.where(predicateMethod.apply(narrationList));
            spec = (spec == null) ? localSpec : spec.and(localSpec);
            return spec;
        }
        return spec;
    }

    private Specification<Payment> addToPaymentChannelSpec(final List<PaymentChannel> paymentChannelList, Specification<Payment> spec,
                                             Function<List<PaymentChannel>, Specification<Payment>> predicateMethod) {
        if (paymentChannelList != null && !paymentChannelList.isEmpty()) {
            Specification<Payment> localSpec = Specification.where(predicateMethod.apply(paymentChannelList));
            spec = (spec == null) ? localSpec : spec.and(localSpec);
            return spec;
        }
        return spec;
    }

    private Specification<Payment> addToPaymentTypeSpec(final List<PaymentType> paymentTypeList, Specification<Payment> spec,
                                                        Function<List<PaymentType>, Specification<Payment>> predicateMethod) {
        if (paymentTypeList != null && !paymentTypeList.isEmpty()) {
            Specification<Payment> localSpec = Specification.where(predicateMethod.apply(paymentTypeList));
            spec = (spec == null) ? localSpec : spec.and(localSpec);
            return spec;
        }
        return spec;
    }

    private Specification<Payment> addToCurrencyTypeSpec(final List<Currency> currencyList, Specification<Payment> spec,
                                                        Function<List<Currency>, Specification<Payment>> predicateMethod) {
        if (currencyList != null && !currencyList.isEmpty()) {
            Specification<Payment> localSpec = Specification.where(predicateMethod.apply(currencyList));
            spec = (spec == null) ? localSpec : spec.and(localSpec);
            return spec;
        }
        return spec;
    }

    private Specification<Payment> addToPaymentMethodSpec(final List<PaymentMethod> paymentMethodList, Specification<Payment> spec,
                                                          Function<List<PaymentMethod>, Specification<Payment>> predicateMethod) {
        if (paymentMethodList != null && !paymentMethodList.isEmpty()) {
            Specification<Payment> localSpec = Specification.where(predicateMethod.apply(paymentMethodList));
            spec = (spec == null) ? localSpec : spec.and(localSpec);
            return spec;
        }
        return spec;
    }

    private Specification<Payment> addToPaymentStatusSpec(final List<PaymentStatus> paymentStatusList, Specification<Payment> spec,
                                                          Function<List<PaymentStatus>, Specification<Payment>> predicateMethod) {
        if (paymentStatusList != null && !paymentStatusList.isEmpty()) {
            Specification<Payment> localSpec = Specification.where(predicateMethod.apply(paymentStatusList));
            spec = (spec == null) ? localSpec : spec.and(localSpec);
            return spec;
        }
        return spec;
    }

    private static UpdateAccountRequest buildUpdateAccountRequest(Payment payment, Account accountRetrieved) {

        UpdateAccountRequest updateAccountRequest = new UpdateAccountRequest();
        updateAccountRequest.setAccountNumber(accountRetrieved.getAccountNumber());
        updateAccountRequest.setAmount(payment.getAmount());
        updateAccountRequest.setCurrency(payment.getCurrency().toString());
        updateAccountRequest.setNarration(payment.getNarration().getAccountNarration());
        updateAccountRequest.setTransactionReference(payment.getTransactionReference());

        return updateAccountRequest;
    }

    private static UpdateAccountRequest buildUpdateAccountRequest(ReversePaymentRequest reversePaymentRequest,
                                                                  Payment payment) {

        UpdateAccountRequest updateAccountRequest = new UpdateAccountRequest();
        updateAccountRequest.setAccountNumber(payment.getAccountNumber());
        updateAccountRequest.setAmount(payment.getAmount());
        updateAccountRequest.setCurrency(payment.getCurrency().toString());
        updateAccountRequest.setNarration(reversePaymentRequest.getNarration());
        updateAccountRequest.setTransactionReference(payment.getTransactionReference());

        return updateAccountRequest;
    }

    private Page<PaymentDto> convertPaymentEntityToPaymentDto(Page<Payment> paymentPage){

        List<Payment> paymentList = paymentPage.getContent();
        List<PaymentDto> paymentDtoList = new ArrayList<>();

        for (Payment payment : paymentPage) {

            PaymentDto paymentDto = modelMapper.map(payment, PaymentDto.class);
            paymentDtoList.add(paymentDto);
        }

        int page = paymentPage.getNumber();
        int size = paymentPage.getSize();

        size = size <= 0 ? 10 : size;

        Pageable pageablePayments = PageRequest.of(page, size);

        return new PageImpl<PaymentDto>(paymentDtoList, pageablePayments, paymentPage.getTotalElements());
    }

    private PaymentResponse buildPaymentResponse(int statusCode, boolean isSuccess, String message,
                                                 PaymentDto paymentDto, List<PaymentDto> paymentDtoList,
                                                 Page<PaymentDto> paymentDtoPage){

        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setStatusCode(statusCode);
        paymentResponse.setSuccess(isSuccess);
        paymentResponse.setMessage(message);
        paymentResponse.setPaymentDto(paymentDto);
        paymentResponse.setPaymentDtoList(paymentDtoList);
        paymentResponse.setPaymentDtoPage(paymentDtoPage);

        return paymentResponse;

    }
}
