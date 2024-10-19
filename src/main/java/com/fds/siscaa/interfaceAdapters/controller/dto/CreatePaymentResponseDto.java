package com.fds.siscaa.interfaceAdapters.controller.dto;

import com.fds.siscaa.domain.enums.PaymentStatus;
import com.fds.siscaa.useCases.useCases.CreatePaymentResponse;

import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
public class CreatePaymentResponseDto {
    private PaymentStatus status;
    private String date;
    private double value;
    private double refundedValue;

    public CreatePaymentResponseDto() {
    }

    public CreatePaymentResponseDto(CreatePaymentResponse createPaymentResponse) {
        this.status = createPaymentResponse.getStatus();
        this.date = createPaymentResponse.getDate();
        this.value = createPaymentResponse.getValue();
        this.refundedValue = createPaymentResponse.getRefundedValue();
    }

}
