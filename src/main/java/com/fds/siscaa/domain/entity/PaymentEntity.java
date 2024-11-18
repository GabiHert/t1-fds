package com.fds.siscaa.domain.entity;

import java.time.LocalDate;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
@AllArgsConstructor()
public class PaymentEntity {
    private long code;
    private long subscriptionCode;
    private double amount;
    private LocalDate paymentDate;
    private Optional<Long> promotionCode;

    public PaymentEntity() {
    }

    public PaymentEntity(long subscriptionCode, double amount, LocalDate paymentDate, Optional<Long> promotionCode) {
        this.subscriptionCode = subscriptionCode;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.promotionCode = promotionCode;
    }
}
