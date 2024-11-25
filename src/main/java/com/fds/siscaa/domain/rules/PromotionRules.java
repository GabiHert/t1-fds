package com.fds.siscaa.domain.rules;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.fds.siscaa.domain.entity.PromotionEntity;
import com.fds.siscaa.domain.utils.CustomList;

@Component
public class PromotionRules {
    public float applyDiscount(int daysToExtend, float monthlyFee, float discountPercentage) {

        if(discountPercentage < 0)
            return (monthlyFee * (daysToExtend/30));

        if(daysToExtend < 0 || monthlyFee < 0)
            return 0;

        return (monthlyFee * (daysToExtend / 30)) - ((monthlyFee * (daysToExtend / 30)) * discountPercentage / 100);
    }

    public int applyExtraDays(int daysToExtend, int extraDays) {
        if(daysToExtend < 0 || extraDays < 0)
            return 0;
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
