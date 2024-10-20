package com.fds.siscaa.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
@AllArgsConstructor()
public class PromotionEntity {
    private long code;
    private float discountPercentage;
    private float extraDays;
    private int monthsRequired;

    public PromotionEntity() {
    }
}
