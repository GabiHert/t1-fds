package com.fds.siscaa.domain.entity;

import java.time.LocalDate;
import java.util.Optional;

import com.fds.siscaa.domain.enums.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
@AllArgsConstructor()
public class CalculatePaymentResponseEntity {
    private PaymentStatus status;
    private LocalDate date;
    private float refundedValue;
    private float paidValue;
    private int daysToExtend;
    private Optional<PromotionEntity> promotion;

    public CalculatePaymentResponseEntity() {
    }

}