package com.fds.siscaa.domain.entity;

import java.util.Date;

public class PaymentEntity {
    public long code;
    public double amount;
    public Date paymentDate;
    public String dealCode;

    public PaymentEntity(long code, double amount, Date paymentDate,String dealCode ) {
        this.code = code;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.dealCode = dealCode;
    }
}
