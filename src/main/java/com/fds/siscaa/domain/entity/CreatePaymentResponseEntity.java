package com.fds.siscaa.domain.entity;

import java.time.LocalDate;

import com.fds.siscaa.domain.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
@AllArgsConstructor()
public class CreatePaymentResponseEntity {
    private PaymentStatus status;
    private LocalDate date;
    private float refundedValue;
    private int daysToExtend;

    public CreatePaymentResponseEntity() {
    }

}
