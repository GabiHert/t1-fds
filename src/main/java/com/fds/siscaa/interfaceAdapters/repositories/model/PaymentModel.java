package com.fds.siscaa.interfaceAdapters.repositories.model;

import java.util.Date;
import java.util.concurrent.Flow.Subscription;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

public class PaymentModel {
    @Id
    public long code;
    public double amount;
    public Date paymentDate;
    public String dealCode;
    
    @ManyToOne(cascade = CascadeType.REFRESH)
    public Subscription subscription;
    
    protected PaymentModel() {
    }

    public PaymentModel(long code, double amount, Date paymentDate,String dealCode ) {
        this.code = code;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.dealCode = dealCode;
    }
}
