package com.tithe_system.tithe_management_system.utils.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tithe_system.tithe_management_system.utils.dtos.PaymentDto;
import org.springframework.data.domain.Page;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentResponse extends CommonResponse{
    private PaymentDto paymentDto;
    private List<PaymentDto> paymentDtoList;
    private Page<PaymentDto> paymentDtoPage;

    public PaymentDto getPaymentDto() {
        return paymentDto;
    }

    public void setPaymentDto(PaymentDto paymentDto) {
        this.paymentDto = paymentDto;
    }

    public List<PaymentDto> getPaymentDtoList() {
        return paymentDtoList;
    }

    public void setPaymentDtoList(List<PaymentDto> paymentDtoList) {
        this.paymentDtoList = paymentDtoList;
    }

    public Page<PaymentDto> getPaymentDtoPage() {
        return paymentDtoPage;
    }

    public void setPaymentDtoPage(Page<PaymentDto> paymentDtoPage) {
        this.paymentDtoPage = paymentDtoPage;
    }

    @Override
    public String toString() {
        return "PaymentResponse{" +
                "paymentDto=" + paymentDto +
                ", paymentDtoList=" + paymentDtoList +
                ", paymentDtoPage=" + paymentDtoPage +
                '}';
    }
}
