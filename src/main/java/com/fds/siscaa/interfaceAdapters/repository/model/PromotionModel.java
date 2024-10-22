package com.fds.siscaa.interfaceAdapters.repository.model;

import com.fds.siscaa.domain.entity.PromotionEntity;

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
    private String code;
    private float discountPercentage;
    private int extraDays;
    private int monthsRequired;

    @JoinColumn(name = "application_code", referencedColumnName = "code")
    @ManyToOne(cascade = CascadeType.REFRESH)
    private ApplicationModel application;

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
