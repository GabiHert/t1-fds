package com.fds.siscaa.useCases.useCases;

import java.time.LocalDate;

import com.fds.siscaa.domain.entity.SubscriptionEntity;
import com.fds.siscaa.useCases.adapters.SubscriptionRepositoryAdapter;

import lombok.AllArgsConstructor;

@AllArgsConstructor()
public class CreateSubscriptionUseCase {
    private final int daysToExtend = 7;
    private final SubscriptionRepositoryAdapter subscriptionRepository;

    public SubscriptionEntity create(long clientCode, long applicationCode) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(daysToExtend);

        SubscriptionEntity subscriptionEntity = new SubscriptionEntity(
                clientCode,
                applicationCode,
                startDate,
                endDate);

        return subscriptionRepository.create(subscriptionEntity);
    }

}
