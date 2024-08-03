package com.tithe_system.tithe_management_system.business.validations.impl;

import com.tithe_system.tithe_management_system.business.validations.api.PaymentServiceValidator;
import com.tithe_system.tithe_management_system.domain.Narration;
import com.tithe_system.tithe_management_system.domain.Currency;
import com.tithe_system.tithe_management_system.domain.PaymentChannel;
import com.tithe_system.tithe_management_system.domain.PaymentMethod;
import com.tithe_system.tithe_management_system.domain.PaymentStatus;
import com.tithe_system.tithe_management_system.domain.PaymentType;
import com.tithe_system.tithe_management_system.utils.requests.CreatePaymentRequest;
import com.tithe_system.tithe_management_system.utils.requests.ReversePaymentRequest;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatch;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class PaymentServiceValidatorImpl implements PaymentServiceValidator {

    @Value("${constants.max-image-size}")
    private String maxImageSize;
    private static Logger logger = LoggerFactory.getLogger(PaymentServiceValidatorImpl.class);
    private final List<String> executableExtensions =
            Arrays.asList("application/x-executable", "application/x-msdos-program");
    @Override
    public boolean isRequestValidForCreation(CreatePaymentRequest createPaymentRequest) {

        if (createPaymentRequest == null){
            return false;
        }

        if (createPaymentRequest.getAssemblyId() < 1 || createPaymentRequest.getUserAccountId() < 1) {
            return false;
        }

        if (createPaymentRequest.getAmount().compareTo(BigDecimal.ZERO) < 1){
            return false;
        }

        if (createPaymentRequest.getProofOfPayment() == null || createPaymentRequest.getProofOfPayment().isEmpty() ||
        createPaymentRequest.getCurrency() == null || createPaymentRequest.getCurrency().isEmpty() ||
                createPaymentRequest.getPaymentChannel() == null || createPaymentRequest.getPaymentChannel().isEmpty() ||
        createPaymentRequest.getPaymentType() == null ||  createPaymentRequest.getPaymentType().isEmpty()) {
            return false;
        }

        if (!isCurrencyValid(createPaymentRequest.getCurrency())) {
            return false;
        }

        if (!isPaymentChannelValid(createPaymentRequest.getPaymentChannel())) {
            return false;
        }

        if (!isPaymentTypeValid(createPaymentRequest.getPaymentType())) {
            return false;
        }

        if (!isNarrationValid(createPaymentRequest.getNarration())) {
            return false;
        }

        if (!isPaymentMethodValid(createPaymentRequest.getPaymentMethod())) {
            return false;
        }

        if (createPaymentRequest.getProofOfPayment() != null
                && createPaymentRequest.getProofOfPayment().isEmpty()) {
            return false;
        }

        try {
            boolean imageValid = isImageValid(createPaymentRequest.getProofOfPayment());

            if (!imageValid) {
                return false;
            }

        } catch (IOException e) {
            logger.info("Error encountered while validating image : {}", e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean isRequestValidForReversal(ReversePaymentRequest reversePaymentRequest) {

        if (reversePaymentRequest == null){
            return false;
        }

        if (!isNarrationValid(reversePaymentRequest.getNarration())) {
            return false;
        }

        if (reversePaymentRequest.getAmount().compareTo(BigDecimal.ZERO) < 1){
            return false;
        }

        return true;
    }

    @Override
    public boolean isIdValid(Long id) {
        return id != null && id > 1;
    }

    private boolean isImageValid(MultipartFile multipartFile) throws IOException {

        if (multipartFile == null) {
            return false;
        }

        byte[] file = multipartFile.getBytes();

        if (checkImageSizeLimit(file) || checkIfFileTypeAndExtensionAreValid(file)) {
            return false;
        }

        return true;
    }

    private Boolean checkImageSizeLimit(byte[] fileData) {

        return convertKbToBytes(maxImageSize) <= fileData.length;
    }

    private Boolean checkIfFileTypeAndExtensionAreValid(byte[] fileData) {
        try {
            MagicMatch match = Magic.getMagicMatch(fileData, false);

            if (executableExtensions.contains(match.getExtension().toUpperCase())) {
                return true;
            }

        } catch (MagicParseException | MagicMatchNotFoundException | MagicException e) {
            return false;
        }
        return false;
    }

    private long convertKbToBytes(String maxFileSize) {
        String size = maxFileSize.substring(0, maxFileSize.length() - 2);
        return Long.valueOf(size) * 1000;
    }

    private boolean isCurrencyValid(String currencySupplied) {

        try {

            Currency[] currencies = Currency.values();

            for (Currency currency : currencies)

                if (currency.getCurrency().equals(currencySupplied)){
                    return true;
                }

            return false;
        } catch (Exception ex) {

            logger.info("Error encountered while converting currency value from request : {}", currencySupplied);

            return false;
        }
    }

    private boolean isNarrationValid(String narrationSupplied) {

        try {

            Narration[] narrations = Narration.values();

            for (Narration narration : narrations)

                if (narration.getAccountNarration().equals(narrationSupplied)){
                    return true;
                }

            return false;
        } catch (Exception ex) {

            logger.info("Error encountered while converting narration value from request : {}", narrationSupplied);

            return false;
        }
    }

    private boolean isPaymentMethodValid(String paymentMethodSupplied) {

        try {

            PaymentMethod[] paymentMethods = PaymentMethod.values();

            for (PaymentMethod paymentMethod : paymentMethods)

                if (paymentMethod.getPaymentMethod().equals(paymentMethodSupplied)){
                    return true;
                }

            return false;
        } catch (Exception ex) {

            logger.info("Error encountered while converting payment method value from request : {}", paymentMethodSupplied);

            return false;
        }
    }

    private boolean isPaymentStatusValid(String paymentStatusSupplied) {

        try {

            PaymentStatus[] paymentStatuses = PaymentStatus.values();

            for (PaymentStatus paymentStatus : paymentStatuses)

                if (paymentStatus.getPaymentStatus().equals(paymentStatusSupplied)){
                    return true;
                }

            return false;
        } catch (Exception ex) {

            logger.info("Error encountered while converting payment status value from request : {}", paymentStatusSupplied);

            return false;
        }
    }

    private boolean isPaymentChannelValid(String paymentChannelSupplied) {

        try {

            PaymentChannel[] paymentChannels = PaymentChannel.values();

            for (PaymentChannel paymentChannel : paymentChannels)

                if (paymentChannel.getPaymentChannel().equals(paymentChannelSupplied)){
                    return true;
                }

            return false;
        } catch (Exception ex) {

            logger.info("Error encountered while converting payment channel value from request : {}",
                    paymentChannelSupplied);

            return false;
        }
    }

    private boolean isPaymentTypeValid(String paymentTypeSupplied) {

        try {

            PaymentType[] paymentTypes = PaymentType.values();

            for (PaymentType paymentType : paymentTypes)

                if (paymentType.getPaymentType().equals(paymentTypeSupplied)){
                    return true;
                }

            return false;
        } catch (Exception ex) {

            logger.info("Error encountered while converting payment type value from request : {}",
                    paymentTypeSupplied);

            return false;
        }
    }
}
