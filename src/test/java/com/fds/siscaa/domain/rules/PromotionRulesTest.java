package com.fds.siscaa.domain.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fds.siscaa.domain.entity.PromotionEntity;
import com.fds.siscaa.domain.utils.CustomList;

import java.util.Optional;

public class PromotionRulesTest {

    private PromotionRules promotionRules;

    @BeforeEach
    public void setUp() {
        promotionRules = new PromotionRules();
    }

    @Test
    public void applyDiscount_ValidDiscount() {
        float monthlyFee = 200.0f;
        float discountPercentage = 10.0f;
        float expectedFee = 180.0f;

        float actualFee = promotionRules.applyDiscount(monthlyFee, discountPercentage);

        assertEquals(expectedFee, actualFee);
    }

    @Test
    public void applyDiscount_NoDiscount() {
        float monthlyFee = 200.0f;
        float discountPercentage = 0.0f;
        float expectedFee = 200.0f;

        float actualFee = promotionRules.applyDiscount(monthlyFee, discountPercentage);

        assertEquals(expectedFee, actualFee);
    }

    @Test
    public void applyDiscount_FullDiscount() {
        float monthlyFee = 200.0f;
        float discountPercentage = 100.0f;
        float expectedFee = 0.0f;

        float actualFee = promotionRules.applyDiscount(monthlyFee, discountPercentage);

        assertEquals(expectedFee, actualFee);
    }

    @Test
    public void applyExtraDays_ValidExtraDays() {
        int monthsToExtend = 3;
        int extraDays = 5;
        int expectedDays = 35;

        int actualDays = promotionRules.applyExtraDays(monthsToExtend, extraDays);

        assertEquals(expectedDays, actualDays);
    }

    @Test
    public void applyExtraDays_NoExtraDays() {
        int monthsToExtend = 3;
        int extraDays = 0;
        int expectedDays = 3;

        int actualDays = promotionRules.applyExtraDays(monthsToExtend, extraDays);

        assertEquals(expectedDays, actualDays);
    }

    @Test
    public void getValidPromotion_ValidPromotion() {
        int monthsToExtend = 3;
        CustomList<PromotionEntity> promotions = new CustomList<>();
        PromotionEntity promotion = new PromotionEntity();
        promotion.setRequiredMonths(3);
        promotions.add(promotion);

        Optional<PromotionEntity> actualPromotion = promotionRules.getValidPromotion(monthsToExtend, promotions);

        assertEquals(Optional.of(promotion), actualPromotion);
    }

    @Test
    public void getValidPromotion_NoValidPromotion() {
        int monthsToExtend = 3;
        CustomList<PromotionEntity> promotions = new CustomList<>();
        PromotionEntity promotion = new PromotionEntity();
        promotion.setRequiredMonths(4);
        promotions.add(promotion);

        Optional<PromotionEntity> actualPromotion = promotionRules.getValidPromotion(monthsToExtend, promotions);

        assertEquals(Optional.empty(), actualPromotion);
    }

    @Test
    public void getValidPromotion_EmptyPromotionsList() {
        int monthsToExtend = 3;
        CustomList<PromotionEntity> promotions = new CustomList<>();

        Optional<PromotionEntity> actualPromotion = promotionRules.getValidPromotion(monthsToExtend, promotions);

        assertEquals(Optional.empty(), actualPromotion);
    }
}