package com.fds.siscaa.interfaceAdapters.repository.model;

import com.fds.siscaa.domain.entity.PaymentEntity;
import com.fds.siscaa.domain.entity.SubscriptionEntity;

import java.util.Date;


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
    public SubscriptionModel subscription;
    
    protected PaymentModel() {
    }

    public PaymentModel(long code, double amount, Date paymentDate,String dealCode, SubscriptionModel subscriptionModel) {
        this.code = code;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.dealCode = dealCode;
        this.subscription = subscriptionModel;
    }

    public PaymentModel(PaymentEntity paymentEntity) {
        this.code = paymentEntity.code;
        this.amount = paymentEntity.amount;
        this.paymentDate = paymentEntity.paymentDate;
        this.dealCode = paymentEntity.dealCode;
        this.subscription = new SubscriptionModel();
        this.subscription.code = paymentEntity.subscriptionCode;
    }

    public PaymentEntity toEntity() {
        return new PaymentEntity(code, subscription.code, amount, paymentDate, dealCode);
    }
}
