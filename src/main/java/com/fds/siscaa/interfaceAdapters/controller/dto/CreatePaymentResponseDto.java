package com.fds.siscaa.interfaceAdapters.controller.dto;

import com.fds.siscaa.domain.enums.PaymentStatus;
import com.fds.siscaa.useCases.useCases.CreatePaymentResponse;
import java.text.SimpleDateFormat;

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

    public CreatePaymentResponseDto(CreatePaymentResponse createPaymentResponse) {
        this.status = createPaymentResponse.getStatus();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.date = dateFormat.format(createPaymentResponse.getDate());
        this.refundedValue = createPaymentResponse.getRefundedValue();

    }

}
