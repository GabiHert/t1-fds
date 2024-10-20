package com.fds.siscaa.domain.entity;

import java.util.Date;

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
    private Date paymentDate;
    private String dealCode;

    public PaymentEntity() {
    }
}
