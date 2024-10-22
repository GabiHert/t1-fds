package com.fds.siscaa.interfaceAdapters.repository.model;

import com.fds.siscaa.domain.entity.PromotionEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity()
@Table(name = "promotion")
@Getter()
@Setter()
@AllArgsConstructor()
public class PromotionModel {
    @Id
    private long code;
    private float discountPercentage;
    private int extraDays;
    private int monthsRequired;

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
