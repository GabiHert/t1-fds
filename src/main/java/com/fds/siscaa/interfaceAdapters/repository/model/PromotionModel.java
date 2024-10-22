package com.fds.siscaa.interfaceAdapters.repository.model;

import com.fds.siscaa.domain.entity.PromotionEntity;

import com.fds.siscaa.domain.utils.CustomList;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity()
@Table(name = "Promotion")
@Getter()
@Setter()
@AllArgsConstructor()
public class PromotionModel {
    @Id
    private long code;
    private float discountPercentage;
    private int extraDays;
    private int monthsRequired;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "subscription_code", referencedColumnName = "code")
    public SubscriptionModel subscription;

    protected PromotionModel() {
    }

    public PromotionModel(PromotionEntity promotionEntity) {
        this.code = promotionEntity.getCode();
        this.discountPercentage = promotionEntity.getDiscountPercentage();
        this.extraDays = promotionEntity.getExtraDays();
        this.monthsRequired = promotionEntity.getRequiredMonths();
    }

    public PromotionEntity toEntity() {
        return new PromotionEntity(code, discountPercentage, extraDays, monthsRequired);
    }

}
