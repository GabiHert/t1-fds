package com.fds.siscaa.domain.rules;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.fds.siscaa.domain.entity.PromotionEntity;
import com.fds.siscaa.domain.utils.CustomList;

@Component
public class PromotionRules {
    public float applyDiscount(int daysToExtend, float monthlyFee, float discountPercentage) {
        return (monthlyFee * (daysToExtend / 30)) - ((monthlyFee * (daysToExtend / 30)) * discountPercentage / 100);
    }

    public int applyExtraDays(int daysToExtend, int extraDays) {
        return daysToExtend + extraDays;
    }

    public Optional<PromotionEntity> getValidPromotion(int monthsToExtend,
            CustomList<PromotionEntity> promotions) {
        for (PromotionEntity promotion : promotions) {
            if (promotion.getRequiredDays() == monthsToExtend) {
                return Optional.of(promotion);
            }
        }
        return Optional.empty();
    }
}
