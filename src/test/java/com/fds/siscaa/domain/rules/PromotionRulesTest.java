package com.fds.siscaa.domain.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fds.siscaa.domain.entity.PromotionEntity;
import com.fds.siscaa.domain.utils.CustomList;

public class PromotionRulesTest {

    private PromotionRules promotionRules;

    @BeforeEach
    public void setUp() {
        promotionRules = new PromotionRules();
    }

    @Test
    public void testApplyDiscount_PositiveValues() {
        float result = promotionRules.applyDiscount(180, 100.0f, 10.0f);
        assertEquals(540.0f, result);
    }

    @Test
    public void testApplyDiscount_ZeroDiscount() {
        float result = promotionRules.applyDiscount(180, 100.0f, 0.0f);
        assertEquals(600.0f, result);
    }

    @Test
    public void testApplyDiscount_ZeroDaysToExtend() {
        float result = promotionRules.applyDiscount(0, 100.0f, 10.0f);
        assertEquals(0.0f, result);
    }

    @Test
    public void testApplyDiscount_NegativeDiscount() {
        float result = promotionRules.applyDiscount(180, 100.0f, -10.0f);
        assertEquals(660.0f, result);
    }

    @Test
    public void testApplyDiscount_NegativeDaysToExtend() {
        float result = promotionRules.applyDiscount(-180, 100.0f, 10.0f);
        assertEquals(-540.0f, result);
    }

    @Test
    public void testApplyExtraDays_PositiveValues() {
        int result = promotionRules.applyExtraDays(30, 5);
        assertEquals(35, result);
    }

    @Test
    public void testApplyExtraDays_ZeroExtraDays() {
        int result = promotionRules.applyExtraDays(30, 0);
        assertEquals(30, result);
    }

    @Test
    public void testApplyExtraDays_ZeroDaysToExtend() {
        int result = promotionRules.applyExtraDays(0, 5);
        assertEquals(5, result);
    }

    @Test
    public void testApplyExtraDays_NegativeExtraDays() {
        int result = promotionRules.applyExtraDays(30, -5);
        assertEquals(25, result);
    }

    @Test
    public void testApplyExtraDays_NegativeDaysToExtend() {
        int result = promotionRules.applyExtraDays(-30, 5);
        assertEquals(-25, result);
    }

    @Test
    public void testGetValidPromotion_ValidPromotion() {
        CustomList<PromotionEntity> promotions = new CustomList<>();
        PromotionEntity promotion = new PromotionEntity();
        promotion.setRequiredDays(6);
        promotions.add(promotion);

        Optional<PromotionEntity> result = promotionRules.getValidPromotion(6, promotions);
        assertTrue(result.isPresent());
    }

    @Test
    public void testGetValidPromotion_NoValidPromotion() {
        CustomList<PromotionEntity> promotions = new CustomList<>();
        PromotionEntity promotion = new PromotionEntity();
        promotion.setRequiredDays(6);
        promotions.add(promotion);

        Optional<PromotionEntity> result = promotionRules.getValidPromotion(5, promotions);
        assertFalse(result.isPresent());
    }

    @Test
    public void testGetValidPromotion_ZeroMonthsToExtend() {
        CustomList<PromotionEntity> promotions = new CustomList<>();
        PromotionEntity promotion = new PromotionEntity();
        promotion.setRequiredDays(6);
        promotions.add(promotion);

        Optional<PromotionEntity> result = promotionRules.getValidPromotion(0, promotions);
        assertFalse(result.isPresent());
    }

    @Test
    public void testGetValidPromotion_NegativeMonthsToExtend() {
        CustomList<PromotionEntity> promotions = new CustomList<>();
        PromotionEntity promotion = new PromotionEntity();
        promotion.setRequiredDays(6);
        promotions.add(promotion);

        Optional<PromotionEntity> result = promotionRules.getValidPromotion(-6, promotions);
        assertFalse(result.isPresent());
    }
}