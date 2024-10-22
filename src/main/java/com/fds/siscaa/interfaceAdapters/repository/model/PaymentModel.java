package com.fds.siscaa.interfaceAdapters.repository.model;

import com.fds.siscaa.domain.entity.PaymentEntity;

import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity()
@Table(name = "Payment")
@Getter()
@Setter()
@AllArgsConstructor()
public class PaymentModel {
    @Id
    private long code;

    @Column(name = "amount")
    private double amount;

    @Column(name = "paymentDate")
    private Date paymentDate;

    @Column(name = "dealCode")
    private String dealCode;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "subscription_code", referencedColumnName = "code")
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
