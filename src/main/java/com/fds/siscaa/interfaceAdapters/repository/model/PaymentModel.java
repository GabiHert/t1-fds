package com.fds.siscaa.interfaceAdapters.repository.model;

import com.fds.siscaa.domain.entity.PaymentEntity;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity()
@Getter()
@Setter()
@AllArgsConstructor()
public class PaymentModel {
    @Id
    private long code;
    private double amount;
    private Date paymentDate;
    private String dealCode;

    @ManyToOne(cascade = CascadeType.REFRESH)
    public SubscriptionModel subscription;

    protected PaymentModel() {
    }

    public PaymentModel(PaymentEntity paymentEntity) {
        this.code = paymentEntity.getCode();
        this.amount = paymentEntity.getAmount();
        this.paymentDate = paymentEntity.getPaymentDate();
        this.dealCode = paymentEntity.getDealCode();
        this.subscription = new SubscriptionModel();
        this.subscription.code = paymentEntity.getSubscriptionCode();
    }

    public PaymentEntity toEntity() {
        return new PaymentEntity(code, subscription.code, amount, paymentDate, dealCode);
    }
}
