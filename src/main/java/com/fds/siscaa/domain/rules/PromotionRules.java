package com.fds.siscaa.domain.rules;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.fds.siscaa.domain.entity.PromotionEntity;
import com.fds.siscaa.domain.utils.CustomList;

@Component
public class PromotionRules {
    public float applyDiscount(int monthsToExtend, float monthlyFee, float discountPercentage) {
        return (monthlyFee * monthsToExtend) - ((monthlyFee * monthsToExtend) * discountPercentage / 100);
    }

    public int applyExtraDays(int daysToExtend, int extraDays) {
        return daysToExtend + extraDays;
    }

    public Optional<PromotionEntity> getValidPromotion(int monthsToExtend,
            CustomList<PromotionEntity> promotions) {
        for (PromotionEntity promotion : promotions) {
            if (promotion.getRequiredMonths() == monthsToExtend) {
                return Optional.of(promotion);
            }
        }
        return Optional.empty();
    }
}
