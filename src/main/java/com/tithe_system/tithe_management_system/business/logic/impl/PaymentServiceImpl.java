package com.tithe_system.tithe_management_system.business.logic.impl;

import com.tithe_system.tithe_management_system.business.auditables.api.PaymentServiceAuditable;
import com.tithe_system.tithe_management_system.business.logic.api.PaymentService;
import com.tithe_system.tithe_management_system.business.validations.api.PaymentServiceValidator;
import com.tithe_system.tithe_management_system.domain.Assembly;
import com.tithe_system.tithe_management_system.domain.EntityStatus;
import com.tithe_system.tithe_management_system.domain.Payment;
import com.tithe_system.tithe_management_system.domain.PaymentStatus;
import com.tithe_system.tithe_management_system.domain.UserAccount;
import com.tithe_system.tithe_management_system.repository.AssemblyRepository;
import com.tithe_system.tithe_management_system.repository.PaymentRepository;
import com.tithe_system.tithe_management_system.repository.RegionRepository;
import com.tithe_system.tithe_management_system.repository.UserAccountRepository;
import com.tithe_system.tithe_management_system.utils.dtos.AssemblyDto;
import com.tithe_system.tithe_management_system.utils.dtos.PaymentDto;
import com.tithe_system.tithe_management_system.utils.dtos.UserAccountDto;
import com.tithe_system.tithe_management_system.utils.enums.I18Code;
import com.tithe_system.tithe_management_system.utils.generators.UniqueCodesGenerator;
import com.tithe_system.tithe_management_system.utils.i18.api.MessageService;
import com.tithe_system.tithe_management_system.utils.requests.CreatePaymentRequest;
import com.tithe_system.tithe_management_system.utils.requests.ReversePaymentRequest;
import com.tithe_system.tithe_management_system.utils.responses.PaymentResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class PaymentServiceImpl implements PaymentService {

    private final PaymentServiceValidator paymentServiceValidator;
    private final PaymentRepository paymentRepository;
    private final AssemblyRepository assemblyRepository;
    private final UserAccountRepository userAccountRepository;
    private final RegionRepository regionRepository;
    private final ModelMapper modelMapper;
    private final PaymentServiceAuditable paymentServiceAuditable;
    private final MessageService messageService;

    public PaymentServiceImpl(PaymentServiceValidator paymentServiceValidator, PaymentRepository paymentRepository, AssemblyRepository assemblyRepository, UserAccountRepository userAccountRepository, RegionRepository regionRepository, ModelMapper modelMapper, PaymentServiceAuditable paymentServiceAuditable, MessageService messageService) {
        this.paymentServiceValidator = paymentServiceValidator;
        this.paymentRepository = paymentRepository;
        this.assemblyRepository = assemblyRepository;
        this.userAccountRepository = userAccountRepository;
        this.regionRepository = regionRepository;
        this.modelMapper = modelMapper;
        this.paymentServiceAuditable = paymentServiceAuditable;
        this.messageService = messageService;
    }

    @Override
    public PaymentResponse createPayment(CreatePaymentRequest createPaymentRequest, String username, Locale locale) {

        String message = "";

        boolean isRequestValid = paymentServiceValidator.isRequestValidForCreation(createPaymentRequest);

        if (!isRequestValid) {
            message = messageService.getMessage(I18Code.MESSAGE_CREATE_PAYMENT_INVALID_REQUEST.getCode(), new String[]{},
                    locale);

            return buildPaymentResponse(400, false, message, null, null,
                    null);
        }

        Optional<Assembly> assemblyRetrieved = assemblyRepository.findByIdAndEntityStatusNot(
                createPaymentRequest.getAssemblyId(), EntityStatus.DELETED);

        if (assemblyRetrieved.isEmpty()) {

            message = messageService.getMessage(I18Code.MESSAGE_ASSEMBLY_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildPaymentResponse(400, false, message, null, null,
                    null);
        }

        Optional<UserAccount> userAccountRetrieved = userAccountRepository.findByIdAndEntityStatusNot(
                createPaymentRequest.getUserAccountId(), EntityStatus.DELETED);

        if (userAccountRetrieved.isEmpty()) {

            message = messageService.getMessage(I18Code.MESSAGE_USER_ACCOUNT_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildPaymentResponse(400, false, message, null, null,
                    null);
        }

        createPaymentRequest.setTransactionReference("CFM" + UniqueCodesGenerator.getUniqueId().toString());


        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Payment paymentToBeSaved = modelMapper.map(createPaymentRequest, Payment.class);
        paymentToBeSaved.setAssembly(assemblyRetrieved.get());
        paymentToBeSaved.setUserAccount(userAccountRetrieved.get());
        paymentToBeSaved.setPaymentStatus(PaymentStatus.INITIATED);

        Payment paymentSaved = paymentServiceAuditable.create(paymentToBeSaved, locale, username);

        AssemblyDto assemblyDto = modelMapper.map(assemblyRetrieved.get(), AssemblyDto.class);

        UserAccountDto userAccountDto = modelMapper.map(userAccountRetrieved.get(), UserAccountDto.class);

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        PaymentDto paymentDtoReturned = modelMapper.map(paymentSaved, PaymentDto.class);
        paymentDtoReturned.setAssemblyDto(assemblyDto);
        paymentDtoReturned.setUserAccountDto(userAccountDto);

        message = messageService.getMessage(I18Code.MESSAGE_PAYMENT_INITIATED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildPaymentResponse(201, true, message, paymentDtoReturned, null,
                null);
    }

    @Override
    public PaymentResponse reversePayment(ReversePaymentRequest reversePaymentRequest, String username, Locale locale) {
        return null;
    }

    @Override
    public PaymentResponse findByTransactionReference(String transactionReference, String username, Locale locale) {
        return null;
    }

    @Override
    public PaymentResponse findById(String transactionReference, Locale locale) {
        return null;
    }

    @Override
    public PaymentResponse findByPaymentStatus(String paymentStatus, String username, Locale locale) {
        return null;
    }

    @Override
    public PaymentResponse findPaymentsAsPages(int page, int size, Locale locale, String username) {
        return null;
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
