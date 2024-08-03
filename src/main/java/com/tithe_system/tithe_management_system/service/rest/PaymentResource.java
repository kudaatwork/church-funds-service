package com.tithe_system.tithe_management_system.service.rest;

import com.tithe_system.tithe_management_system.service.processor.api.PaymentServiceProcessor;
import com.tithe_system.tithe_management_system.utils.constants.Constants;
import com.tithe_system.tithe_management_system.utils.requests.CreatePaymentRequest;
import com.tithe_system.tithe_management_system.utils.requests.ReversePaymentRequest;
import com.tithe_system.tithe_management_system.utils.responses.PaymentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Locale;

@RestController
@CrossOrigin
@RequestMapping("/tithe-management/v1/payment")
public class PaymentResource {
    private final PaymentServiceProcessor paymentServiceProcessor;

    public PaymentResource(PaymentServiceProcessor paymentServiceProcessor) {
        this.paymentServiceProcessor = paymentServiceProcessor;
    }

    @Operation(summary = "Create a payment")
    @PostMapping(value = "/create")
    public PaymentResponse create(@Valid @ModelAttribute final CreatePaymentRequest createPaymentRequest,
                                  @Parameter(name = "Authorization", in = ParameterIn.HEADER,
                                           description = "Bearer token", required = true)
                                   String authenticationToken,
                                  @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                   @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                           defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return paymentServiceProcessor.create(createPaymentRequest, authenticationToken, locale);
    }

    @Operation(summary = "Reverse a payment")
    @PostMapping(value = "/reverse")
    public PaymentResponse reverse(@Valid @RequestBody final ReversePaymentRequest reversePaymentRequest,
                                  @Parameter(name = "Authorization", in = ParameterIn.HEADER,
                                          description = "Bearer token", required = true)
                                  String authenticationToken,
                                  @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                  @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                          defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return paymentServiceProcessor.reverse(reversePaymentRequest, authenticationToken, locale);
    }

    @Operation(summary = "Find a payment by id")
    @GetMapping(value = "/id/{id}")
    public PaymentResponse findById(@PathVariable("id") final Long id,
                                     @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                     @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                             defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return paymentServiceProcessor.findById(id, locale);
    }

    @Operation(summary = "Find all payments as pages")
    @GetMapping(value = "/pages")
    public PaymentResponse findAllAsPages(@Parameter(name = "Authorization", in = ParameterIn.HEADER,
            description = "Bearer token", required = true)
                                           String authenticationToken,
                                           @RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "10") int size,
                                           @Parameter(description = Constants.LOCALE_LANGUAGE_NARRATIVE)
                                           @RequestHeader(value = Constants.LOCALE_LANGUAGE,
                                                   defaultValue = Constants.DEFAULT_LOCALE) final Locale locale)
    {
        return paymentServiceProcessor.findPaymentsAsPages(page, size, locale, authenticationToken);
    }
}
