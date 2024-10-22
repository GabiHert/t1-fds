package com.fds.siscaa.interfaceAdapters.controller.dto;

import com.fds.siscaa.domain.entity.CalculatePaymentResponseEntity;
import com.fds.siscaa.domain.enums.PaymentStatus;

import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
public class CreatePaymentResponseDto {
    private PaymentStatus status;
    private String date;
    private float refundedValue;

    public CreatePaymentResponseDto() {
    }

    public CreatePaymentResponseDto(CalculatePaymentResponseEntity createPaymentResponse) {
        this.status = createPaymentResponse.getStatus();
        // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.date = createPaymentResponse.getDate().toString();
        this.refundedValue = createPaymentResponse.getRefundedValue();

    }

}
