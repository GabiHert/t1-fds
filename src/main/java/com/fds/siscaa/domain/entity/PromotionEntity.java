package com.fds.siscaa.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
@AllArgsConstructor()
public class PromotionEntity {
    private Long code;
    private float discountPercentage;
    private int extraDays;
    private int requiredMonths;

    public PromotionEntity() {
    }
}
