package com.fds.siscaa.useCases.useCases;

import java.time.LocalDate;
import java.util.Optional;

import com.fds.siscaa.domain.entity.PromotionEntity;
import com.fds.siscaa.domain.entity.SubscriptionEntity;
import com.fds.siscaa.domain.enums.PaymentStatus;
import com.fds.siscaa.domain.rules.PaymentRules;
import com.fds.siscaa.domain.rules.PromotionRules;
import com.fds.siscaa.useCases.adapters.SubscriptionRepositoryAdapter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreatePaymentUseCase {

        private final SubscriptionRepositoryAdapter subscriptionRepository;
        private final PaymentRules paymentRules;
        private final PromotionRules promotionRules;

        public CreatePaymentResponse create(LocalDate paymentDate, int subscriptionCode, float receivedAmount) {
                System.out.println(
                                String.format("CreatePaymentUseCase - STARTED - paymentDate: %s subscriptionCode: %d receivedAmount: %.2f",
                                                paymentDate, subscriptionCode, receivedAmount));
                SubscriptionEntity subscription = subscriptionRepository.getSubscriptionEntityByCode(subscriptionCode);

                float monthlyFee = subscription.getApplication().getMonthlyFee();

                int monthsToExtend = paymentRules.calculateMonthsToExtend(monthlyFee, receivedAmount);
                PaymentStatus paymentStatus = paymentRules.calculatePaymentStatus(monthlyFee, receivedAmount);

                if (paymentStatus == PaymentStatus.VALOR_INCORRETO) {
                        return new CreatePaymentResponse(paymentStatus, subscription.getEndDate(), receivedAmount);
                }

                monthsToExtend = applyPromotionIfValid(subscription, monthsToExtend, monthlyFee);

                float refundValue = paymentRules.calculateRefund(monthlyFee, receivedAmount);

                updateSubscriptionDates(subscriptionCode, monthsToExtend);

                return new CreatePaymentResponse(paymentStatus, subscription.getEndDate(), refundValue);
        }

        private int applyPromotionIfValid(SubscriptionEntity subscription, int monthsToExtend, float monthlyFee) {
                Optional<PromotionEntity> validPromotion = promotionRules.getValidPromotion(monthsToExtend,
                                subscription.getPromotions());

                if (validPromotion.isPresent()) {
                        monthsToExtend = promotionRules.applyExtraDays(monthsToExtend,
                                        validPromotion.get().getExtraDays());
                        monthlyFee = promotionRules.applyDiscount(monthlyFee,
                                        validPromotion.get().getDiscountPercentage());
                }

                return monthsToExtend;
        }

        private void updateSubscriptionDates(int subscriptionCode, int monthsToExtend) {
                LocalDate currentDate = LocalDate.now();
                subscriptionRepository.updateSubscriptionStartDateAndEndDateByCode(
                                currentDate,
                                currentDate.plusDays(monthsToExtend * 30),
                                subscriptionCode);
        }
}