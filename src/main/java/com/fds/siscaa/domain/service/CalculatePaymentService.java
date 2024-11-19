package com.fds.siscaa.domain.service;

import java.util.Optional;

import com.fds.siscaa.domain.entity.CalculatePaymentResponseEntity;
import com.fds.siscaa.domain.entity.PromotionEntity;
import com.fds.siscaa.domain.entity.SubscriptionEntity;
import com.fds.siscaa.domain.enums.PaymentStatus;
import com.fds.siscaa.domain.rules.PaymentRules;
import com.fds.siscaa.domain.rules.PromotionRules;
import com.fds.siscaa.domain.utils.CustomList;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor()
@Service
public class CalculatePaymentService {
        private final PaymentRules paymentRules;
        private final PromotionRules promotionRules;

        public CalculatePaymentResponseEntity calculate(SubscriptionEntity subscription,
                        CustomList<PromotionEntity> promotionEntities, float receivedAmount) {
                float monthlyFee = subscription.getApplication().getMonthlyFee();

                int daysToExtend = paymentRules.calculateDaysToExtend(monthlyFee, receivedAmount);
                PaymentStatus paymentStatus = paymentRules.calculatePaymentStatus(monthlyFee, receivedAmount);

                if (paymentStatus == PaymentStatus.VALOR_INCORRETO) {
                        return new CalculatePaymentResponseEntity(
                                        paymentStatus,
                                        subscription.getEndDate(),
                                        receivedAmount,
                                        0,
                                        Optional.empty());
                }

                Optional<PromotionEntity> validPromotion = promotionRules.getValidPromotion(
                                daysToExtend,
                                promotionEntities);

                if (validPromotion.isPresent()) {
                        daysToExtend = promotionRules.applyExtraDays(daysToExtend,
                                        validPromotion.get().getExtraDays());
                        monthlyFee = promotionRules.applyDiscount(daysToExtend, monthlyFee,
                                        validPromotion.get().getDiscountPercentage());
                }

                float refundValue = paymentRules.calculateRefund(monthlyFee, receivedAmount);

                return new CalculatePaymentResponseEntity(
                                paymentStatus,
                                subscription.getEndDate().plusDays(daysToExtend),
                                refundValue,
                                receivedAmount - refundValue,
                                validPromotion);
        }

}
