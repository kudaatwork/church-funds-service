package com.tithe_system.tithe_management_system.business.logic.impl;

import com.tithe_system.tithe_management_system.business.auditables.api.PaymentServiceAuditable;
import com.tithe_system.tithe_management_system.business.logic.api.AccountService;
import com.tithe_system.tithe_management_system.business.logic.api.PaymentService;
import com.tithe_system.tithe_management_system.business.validations.api.PaymentServiceValidator;
import com.tithe_system.tithe_management_system.domain.Account;
import com.tithe_system.tithe_management_system.domain.Assembly;
import com.tithe_system.tithe_management_system.domain.EntityStatus;
import com.tithe_system.tithe_management_system.domain.Narration;
import com.tithe_system.tithe_management_system.domain.Payment;
import com.tithe_system.tithe_management_system.domain.PaymentStatus;
import com.tithe_system.tithe_management_system.domain.UserAccount;
import com.tithe_system.tithe_management_system.repository.AccountRepository;
import com.tithe_system.tithe_management_system.repository.AssemblyRepository;
import com.tithe_system.tithe_management_system.repository.PaymentRepository;
import com.tithe_system.tithe_management_system.repository.UserAccountRepository;
import com.tithe_system.tithe_management_system.utils.dtos.AccountDto;
import com.tithe_system.tithe_management_system.utils.dtos.AssemblyDto;
import com.tithe_system.tithe_management_system.utils.dtos.PaymentDto;
import com.tithe_system.tithe_management_system.utils.dtos.UserAccountDto;
import com.tithe_system.tithe_management_system.utils.enums.I18Code;
import com.tithe_system.tithe_management_system.utils.generators.AccountAndReferencesGenerator;
import com.tithe_system.tithe_management_system.utils.i18.api.ApplicationMessagesService;
import com.tithe_system.tithe_management_system.utils.requests.CreatePaymentRequest;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

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
            message = applicationMessagesService.getMessage(I18Code.MESSAGE_CREATE_PAYMENT_INVALID_REQUEST.getCode(), new String[]{},
                    locale);

            return buildPaymentResponse(400, false, message, null, null,
                    null);
        }

        Optional<Assembly> assemblyRetrieved = assemblyRepository.findByIdAndEntityStatusNot(
                createPaymentRequest.getAssemblyId(), EntityStatus.DELETED);

        if (assemblyRetrieved.isEmpty()) {

            message = applicationMessagesService.getMessage(I18Code.MESSAGE_ASSEMBLY_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildPaymentResponse(400, false, message, null, null,
                    null);
        }

        Optional<UserAccount> userAccountRetrieved = userAccountRepository.findByIdAndEntityStatusNot(
                createPaymentRequest.getUserAccountId(), EntityStatus.DELETED);

        if (userAccountRetrieved.isEmpty()) {

            message = applicationMessagesService.getMessage(I18Code.MESSAGE_USER_ACCOUNT_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildPaymentResponse(400, false, message, null, null,
                    null);
        }

        Optional<Account> accountRetrieved = accountRepository.findByIdAndEntityStatusNot(
                createPaymentRequest.getUserAccountId(), EntityStatus.DELETED);

        if (accountRetrieved.isEmpty()) {

            message = applicationMessagesService.getMessage(I18Code.MESSAGE_ACCOUNT_NOT_FOUND.getCode(), new String[]{},
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
        paymentToBeSaved.setNarration(Narration.PAYMENT.getAccountNarration());

        Payment paymentSaved = paymentServiceAuditable.create(paymentToBeSaved, locale, username);

        UpdateAccountRequest updateAccountRequest = buildUpdateAccountRequest(paymentSaved, accountRetrieved.get());

        logger.info("Incoming request to update an account : {}", updateAccountRequest);

        AccountResponse response = accountService.updateAccount(updateAccountRequest, username, locale);

        logger.info("Outgoing response after updating an account : {}", response);

        AccountDto accountDto = modelMapper.map(accountRetrieved.get(), AccountDto.class);

        AssemblyDto assemblyDto = modelMapper.map(assemblyRetrieved.get(), AssemblyDto.class);
        //assemblyDto.setAccountDto(accountDto);

        UserAccountDto userAccountDto = modelMapper.map(userAccountRetrieved.get(), UserAccountDto.class);

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        PaymentDto paymentDtoReturned = modelMapper.map(paymentSaved, PaymentDto.class);
        paymentDtoReturned.setAssemblyDto(assemblyDto);
        paymentDtoReturned.setUserAccountDto(userAccountDto);

        message = applicationMessagesService.getMessage(I18Code.MESSAGE_PAYMENT_INITIATED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildPaymentResponse(201, true, message, paymentDtoReturned, null,
                null);
    }

    @Override
    public PaymentResponse reverse(ReversePaymentRequest reversePaymentRequest, String username, Locale locale) {

        String message = "";

        boolean isRequestValid = paymentServiceValidator.isRequestValidForReversal(reversePaymentRequest);

        if (!isRequestValid) {
            message = applicationMessagesService.getMessage(I18Code.MESSAGE_REVERSE_PAYMENT_INVALID_REQUEST.getCode(), new String[]{},
                    locale);

            return buildPaymentResponse(400, false, message, null, null,
                    null);
        }

        Optional<Payment> paymentRetrieved = paymentRepository.findByTransactionReferenceAndEntityStatusNot(
                reversePaymentRequest.getTransactionReference(), EntityStatus.DELETED);

        if (paymentRetrieved.isEmpty()) {

            message = applicationMessagesService.getMessage(I18Code.MESSAGE_PAYMENT_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildPaymentResponse(400, false, message, null, null,
                    null);
        }

        Optional<Account> accountRetrieved = accountRepository.findByTransactionReferenceAndEntityStatusNot(
                reversePaymentRequest.getTransactionReference(), EntityStatus.DELETED);

        if (accountRetrieved.isEmpty()) {

            message = applicationMessagesService.getMessage(I18Code.MESSAGE_ACCOUNT_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildPaymentResponse(400, false, message, null, null,
                    null);
        }

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Payment paymentToBeSaved = modelMapper.map(reversePaymentRequest, Payment.class);
        paymentToBeSaved.setTransactionReference(AccountAndReferencesGenerator.getTransactionReference().toString());
        paymentToBeSaved.setAssembly(paymentRetrieved.get().getAssembly());
        paymentToBeSaved.setUserAccount(paymentRetrieved.get().getUserAccount());
        paymentToBeSaved.setPaymentStatus(PaymentStatus.INITIATED);
        paymentToBeSaved.setNarration(Narration.REVERSAL.getAccountNarration());

        Payment paymentSaved = paymentServiceAuditable.create(paymentToBeSaved, locale, username);

        UpdateAccountRequest updateAccountRequest = buildUpdateAccountRequest(reversePaymentRequest, accountRetrieved.get());

        logger.info("Incoming request to update an account : {}", updateAccountRequest);

        AccountResponse response = accountService.updateAccount(updateAccountRequest, username, locale);

        logger.info("Outgoing response after updating an account : {}", response);

        AccountDto accountDto = modelMapper.map(accountRetrieved.get(), AccountDto.class);

        AssemblyDto assemblyDto = modelMapper.map(accountRetrieved.get().getAssembly(), AssemblyDto.class);
        //assemblyDto.setAccountDto(accountDto);

        //UserAccountDto userAccountDto = modelMapper.map(accountRetrieved.get().getUserAccount(), UserAccountDto.class);

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        PaymentDto paymentDtoReturned = modelMapper.map(paymentSaved, PaymentDto.class);
        paymentDtoReturned.setAssemblyDto(assemblyDto);
      //  paymentDtoReturned.setUserAccountDto(userAccountDto);

        message = applicationMessagesService.getMessage(I18Code.MESSAGE_PAYMENT_REVERSED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildPaymentResponse(201, true, message, paymentDtoReturned, null,
                null);
    }

    @Override
    public PaymentResponse findById(Long id, Locale locale) {

        String message = "";

        boolean isIdValid = paymentServiceValidator.isIdValid(id);

        if(!isIdValid) {

            message = applicationMessagesService.getMessage(I18Code.MESSAGE_INVALID_PAYMENT_ID_SUPPLIED.getCode(), new String[]
                    {}, locale);

            return buildPaymentResponse(400, false, message, null, null,
                    null);
        }

        Optional<Payment> paymentRetrieved = paymentRepository.findByIdAndEntityStatusNot(id, EntityStatus.DELETED);

        if (paymentRetrieved.isEmpty()) {

            message = applicationMessagesService.getMessage(I18Code.MESSAGE_PAYMENT_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildPaymentResponse(404, false, message, null, null,
                    null);
        }

        Payment paymentReturned = paymentRetrieved.get();

        PaymentDto paymentDto = modelMapper.map(paymentReturned, PaymentDto.class);

        message = applicationMessagesService.getMessage(I18Code.MESSAGE_PAYMENT_RETRIEVED_SUCCESSFULLY.getCode(), new String[]{},
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

            message =  applicationMessagesService.getMessage(I18Code.MESSAGE_PAYMENT_NOT_FOUND.getCode(),
                    new String[]{}, locale);

            return buildPaymentResponse(404, false, message, null, null,
                    paymentDtoPage);
        }

        message =  applicationMessagesService.getMessage(I18Code.MESSAGE_PAYMENT_RETRIEVED_SUCCESSFULLY.getCode(),
                new String[]{}, locale);

        return buildPaymentResponse(200, true, message, null,
                null, paymentDtoPage);
    }

    private static UpdateAccountRequest buildUpdateAccountRequest(Payment payment, Account accountRetrieved) {

        UpdateAccountRequest updateAccountRequest = new UpdateAccountRequest();
        updateAccountRequest.setAccountNumber(accountRetrieved.getAccountNumber());
        updateAccountRequest.setAmount(payment.getAmount());
        updateAccountRequest.setCurrency(payment.getCurrency().toString());
        updateAccountRequest.setNarration(payment.getNarration());

        return updateAccountRequest;
    }

    private static UpdateAccountRequest buildUpdateAccountRequest(ReversePaymentRequest reversePaymentRequest, Account accountRetrieved) {

        UpdateAccountRequest updateAccountRequest = new UpdateAccountRequest();
        updateAccountRequest.setAccountNumber(accountRetrieved.getAccountNumber());
        updateAccountRequest.setAmount(accountRetrieved.getDebitBalance());
        updateAccountRequest.setCurrency(updateAccountRequest.getCurrency());
        updateAccountRequest.setNarration(reversePaymentRequest.getNarration());
        updateAccountRequest.setTransactionReference(reversePaymentRequest.getTransactionReference());

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
