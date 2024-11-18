package com.fds.siscaa.interfaceAdapters.repository.model;

import com.fds.siscaa.domain.entity.PaymentEntity;

import java.time.LocalDate;
import java.util.Optional;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long code;

    private double amount;
    private LocalDate paymentDate;
    private Long dealCode;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "subscriptionCode", referencedColumnName = "code")
    public SubscriptionModel subscription;

    protected PaymentModel() {
    }

    public PaymentModel(PaymentEntity paymentEntity) {
        this.code = paymentEntity.getCode();
        this.amount = paymentEntity.getAmount();
        this.paymentDate = paymentEntity.getPaymentDate();
        this.dealCode = paymentEntity.getPromotionCode().isPresent() ? paymentEntity.getPromotionCode().get() : null;
        this.subscription = new SubscriptionModel();
        this.subscription.code = paymentEntity.getSubscriptionCode();
    }

    public PaymentEntity toEntity() {
        return new PaymentEntity(code, subscription.code, amount, paymentDate, Optional.ofNullable(dealCode));
    }
}
