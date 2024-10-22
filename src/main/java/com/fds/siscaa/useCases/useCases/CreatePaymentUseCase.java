package com.fds.siscaa.useCases.useCases;

import java.time.LocalDate;

import com.fds.siscaa.domain.entity.CreatePaymentResponseEntity;
import com.fds.siscaa.domain.entity.SubscriptionEntity;
import com.fds.siscaa.domain.service.CreatePaymentService;
import com.fds.siscaa.useCases.adapters.SubscriptionRepositoryAdapter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CreatePaymentUseCase {

        private final SubscriptionRepositoryAdapter subscriptionRepository;
        private final CreatePaymentService createPaymentService;

        public CreatePaymentResponseEntity create(LocalDate paymentDate, int subscriptionCode, float receivedAmount) {
                System.out.println(
                                String.format("CreatePaymentUseCase - STARTED - paymentDate: %s subscriptionCode: %d receivedAmount: %.2f",
                                                paymentDate, subscriptionCode, receivedAmount));
                SubscriptionEntity subscription = subscriptionRepository.getSubscriptionEntityByCode(subscriptionCode);

                CreatePaymentResponseEntity createPaymentResponseEntity = this.createPaymentService.create(subscription,
                                receivedAmount);

                updateSubscriptionDates(createPaymentResponseEntity, subscriptionCode);

                return createPaymentResponseEntity;
        }

        private void updateSubscriptionDates(CreatePaymentResponseEntity createPaymentResponseEntity,
                        long subscriptionCode) {
                LocalDate currentDate = LocalDate.now();
                subscriptionRepository.updateSubscriptionStartDateAndEndDateByCode(
                                currentDate,
                                currentDate.plusDays(createPaymentResponseEntity.getDaysToExtend()),
                                subscriptionCode);
        }
}