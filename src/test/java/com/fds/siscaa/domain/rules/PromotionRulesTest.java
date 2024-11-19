package com.fds.siscaa.domain.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    public void testApplyDiscount() {
        float result = promotionRules.applyDiscount(180, 100.0f, 10.0f);
        assertEquals(540.0f, result);
    }

    @Test
    public void testApplyExtraDays() {
        int result = promotionRules.applyExtraDays(30, 5);
        assertEquals(35, result);
    }

    @Test
    public void testGetValidPromotion() {
        CustomList<PromotionEntity> promotions = new CustomList<>();
        PromotionEntity promotion = new PromotionEntity();
        promotion.setRequiredDays(6);
        promotions.add(promotion);

        Optional<PromotionEntity> result = promotionRules.getValidPromotion(6, promotions);
        assertTrue(result.isPresent());
        assertEquals(promotion, result.get());
    }
}