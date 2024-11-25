package com.fds.siscaa.domain.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.fds.siscaa.domain.entity.PromotionEntity;
import com.fds.siscaa.domain.utils.CustomList;

public class PromotionRulesTest {

    private PromotionRules promotionRules;

    @BeforeEach
    public void setUp() {
        promotionRules = new PromotionRules();
    }

    @ParameterizedTest
    @MethodSource("provideDiscountData")
    public void testApplyDiscount(int daysToExtend, float monthlyFee, float discountPercentage, float expectedResult) {
        float result = promotionRules.applyDiscount(daysToExtend, monthlyFee, discountPercentage);
        assertEquals(expectedResult, result);
    }

    private static Stream<Arguments> provideDiscountData() {
        return Stream.of(
                Arguments.of(180, 100.0f, 10.0f, 540.0f),
                Arguments.of(180, 100.0f, 0.0f, 600.0f),
                Arguments.of(0, 100.0f, 10.0f, 0.0f),
                Arguments.of(180, 100.0f, -10.0f, 600.0f),
                Arguments.of(180, -100.0f, 10.0f, 0.0f),
                Arguments.of(-180, 100.0f, 10.0f, 0.0f)
        );
    }

    @ParameterizedTest
    @MethodSource("provideExtraDaysData")
    public void testApplyExtraDays(int daysToExtend, int extraDays, int expectedResult) {
        int result = promotionRules.applyExtraDays(daysToExtend, extraDays);
        assertEquals(expectedResult, result);
    }

    private static Stream<Arguments> provideExtraDaysData() {
        return Stream.of(
                Arguments.of(30, 5, 35),
                Arguments.of(30, 0, 30),
                Arguments.of(0, 5, 5),
                Arguments.of(30, -5, 0),
                Arguments.of(-30, 5, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("provideValidPromotionData")
    public void testGetValidPromotion(int monthsToExtend, CustomList<PromotionEntity> promotions, boolean expectedResult) {
        Optional<PromotionEntity> result = promotionRules.getValidPromotion(monthsToExtend, promotions);
        assertEquals(expectedResult, result.isPresent());
    }

    private static Stream<Arguments> provideValidPromotionData() {
        CustomList<PromotionEntity> promotions = new CustomList<>();
        PromotionEntity promotion = new PromotionEntity();
        promotion.setRequiredDays(6);
        promotions.add(promotion);

        return Stream.of(
                Arguments.of(6, promotions, true),
                Arguments.of(5, promotions, false),
                Arguments.of(0, promotions, false),
                Arguments.of(-6, promotions, false)
        );
    }
}