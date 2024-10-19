package com.fds.siscaa.useCases.useCases;

import com.fds.siscaa.domain.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
@AllArgsConstructor()
public class CreatePaymentResponse {
    private PaymentStatus status;
    private String date;
    private double value;
    private double refundedValue;

    public CreatePaymentResponse() {
    }

}
