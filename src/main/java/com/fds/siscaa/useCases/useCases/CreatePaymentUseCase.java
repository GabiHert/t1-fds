package com.fds.siscaa.useCases.useCases;

import java.time.LocalDate;

import com.fds.siscaa.domain.entity.CalculatePaymentResponseEntity;
import com.fds.siscaa.domain.entity.PaymentEntity;
import com.fds.siscaa.domain.entity.PromotionEntity;
import com.fds.siscaa.domain.entity.SubscriptionEntity;
import com.fds.siscaa.domain.enums.PaymentStatus;
import com.fds.siscaa.domain.service.CalculatePaymentService;
import com.fds.siscaa.domain.utils.CustomList;
import com.fds.siscaa.useCases.adapters.SubscriptionRepositoryAdapter;
import com.fds.siscaa.useCases.adapters.PaymentRepositoryAdapter;
import com.fds.siscaa.useCases.adapters.PromotionRepositoryAdapter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CreatePaymentUseCase {

        private final SubscriptionRepositoryAdapter subscriptionRepository;
        private final CalculatePaymentService calculatePaymentService;
        private final PaymentRepositoryAdapter paymentRepository;
        private final PromotionRepositoryAdapter promotionRepositoryAdapter;

        public CalculatePaymentResponseEntity create(LocalDate paymentDate, int subscriptionCode,
                        float receivedAmount) {
                System.out.println(
                                String.format("CreatePaymentUseCase - STARTED - paymentDate: %s subscriptionCode: %d receivedAmount: %.2f",
                                                paymentDate, subscriptionCode, receivedAmount));

                SubscriptionEntity subscription = subscriptionRepository.getSubscriptionEntityByCode(subscriptionCode);

                CustomList<PromotionEntity> promotionEntities = promotionRepositoryAdapter
                                .listByApplicationCode(subscription.getApplication().getCode());

                CalculatePaymentResponseEntity calculatePaymentResponseEntity = this.calculatePaymentService.calculate(
                                subscription,
                                promotionEntities,
                                receivedAmount);

                if (calculatePaymentResponseEntity.getStatus().equals(PaymentStatus.PAGAMENTO_OK)) {
                        persistPayment(subscriptionCode, calculatePaymentResponseEntity);
                        updateSubscriptionDates(calculatePaymentResponseEntity, subscriptionCode);
                }

                return calculatePaymentResponseEntity;
        }

        private void persistPayment(long subscriptionCode,
                        CalculatePaymentResponseEntity calculatePaymentResponseEntity) {
                PaymentEntity paymentEntity = createPaymentEntity(subscriptionCode, calculatePaymentResponseEntity);
                paymentRepository.create(paymentEntity);

        }

        private PaymentEntity createPaymentEntity(
                        long subscriptionCode,
                        CalculatePaymentResponseEntity createPaymentResponseEntity) {
                return new PaymentEntity(subscriptionCode, createPaymentResponseEntity.getPaidValue(),
                                LocalDate.now(), createPaymentResponseEntity.getPromotion().isPresent()
                                                ? createPaymentResponseEntity.getPromotion().get().getCode()
                                                : null);
        }

        private void updateSubscriptionDates(CalculatePaymentResponseEntity createPaymentResponseEntity,
                        long subscriptionCode) {
                LocalDate currentDate = LocalDate.now();
                subscriptionRepository.updateSubscriptionStartDateAndEndDateByCode(
                                currentDate,
                                currentDate.plusDays(createPaymentResponseEntity.getDaysToExtend()),
                                subscriptionCode);
        }
}