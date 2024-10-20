package com.fds.siscaa.domain.rules;

import java.util.Optional;

import com.fds.siscaa.domain.entity.PromotionEntity;
import com.fds.siscaa.domain.utils.CustomList;

public class PromotionRules {
    public static float applyFeePromotion(float monthlyFee, float discountPercentage) {
        return monthlyFee - (monthlyFee * discountPercentage / 100);
    }

    public static int applyExtraDaysPromotion(int monthsToExtend, int extraDays) {
        return monthsToExtend + extraDays;
    }

    public static Optional<PromotionEntity> getValidPromotion(int monthsToExtend,
            CustomList<PromotionEntity> promotions) {
        for (PromotionEntity promotion : promotions) {
            if (promotion.getRequiredMonths() == monthsToExtend) {
                return Optional.of(promotion);
            }
        }
        return Optional.empty();
    }
}
