package com.fds.siscaa.domain.service;

import java.util.Optional;

import com.fds.siscaa.domain.entity.CreatePaymentResponseEntity;
import com.fds.siscaa.domain.entity.PromotionEntity;
import com.fds.siscaa.domain.entity.SubscriptionEntity;
import com.fds.siscaa.domain.enums.PaymentStatus;
import com.fds.siscaa.domain.rules.PaymentRules;
import com.fds.siscaa.domain.rules.PromotionRules;

import lombok.AllArgsConstructor;

@AllArgsConstructor()
public class CreatePaymentService {
    private final PaymentRules paymentRules;
    private final PromotionRules promotionRules;

    public CreatePaymentResponseEntity create(SubscriptionEntity subscription, float receivedAmount) {
        float monthlyFee = subscription.getApplication().getMonthlyFee();

        int monthsToExtend = paymentRules.calculateMonthsToExtend(monthlyFee, receivedAmount);
        PaymentStatus paymentStatus = paymentRules.calculatePaymentStatus(monthlyFee, receivedAmount);

        if (paymentStatus == PaymentStatus.VALOR_INCORRETO) {
            return new CreatePaymentResponseEntity(paymentStatus, subscription.getEndDate(), receivedAmount, 0);
        }

        Optional<PromotionEntity> validPromotion = promotionRules.getValidPromotion(monthsToExtend,
                subscription.getPromotions());

        if (validPromotion.isPresent()) {
            monthsToExtend = promotionRules.applyExtraDays(monthsToExtend,
                    validPromotion.get().getExtraDays());
            monthlyFee = promotionRules.applyDiscount(monthlyFee,
                    validPromotion.get().getDiscountPercentage());
        }

        float refundValue = paymentRules.calculateRefund(monthlyFee, receivedAmount);

        return new CreatePaymentResponseEntity(paymentStatus, subscription.getEndDate(), refundValue,
                monthsToExtend * 30);
    }

}
