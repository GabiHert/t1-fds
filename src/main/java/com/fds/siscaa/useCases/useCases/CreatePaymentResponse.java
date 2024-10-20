package com.fds.siscaa.useCases.useCases;

import java.util.Date;

import com.fds.siscaa.domain.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
@AllArgsConstructor()
public class CreatePaymentResponse {
    private PaymentStatus status;
    private Date date;
    private float refundedValue;

    public CreatePaymentResponse() {
    }

}
