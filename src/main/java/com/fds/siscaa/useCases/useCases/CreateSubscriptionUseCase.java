package com.fds.siscaa.useCases.useCases;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.fds.siscaa.domain.entity.SubscriptionEntity;
import com.fds.siscaa.useCases.adapters.ApplicationRepositoryAdapter;
import com.fds.siscaa.useCases.adapters.ClientRepositoryAdapter;
import com.fds.siscaa.useCases.adapters.SubscriptionRepositoryAdapter;

import lombok.AllArgsConstructor;

import com.fds.siscaa.domain.entity.ApplicationEntity;
import com.fds.siscaa.domain.entity.ClientEntity;

@AllArgsConstructor()
public class CreateSubscriptionUseCase {

    private final ClientRepositoryAdapter clientRepository;
    private final ApplicationRepositoryAdapter applicationRepository;
    private final SubscriptionRepositoryAdapter subscriptionRepository;

    public SubscriptionEntity create(long clientCode, long applicationCode) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(7);

        SubscriptionEntity subscriptionEntity = new SubscriptionEntity(
                clientCode,
                applicationCode,
                startDate,
                endDate);

        return subscriptionRepository.create(subscriptionEntity);
    }

}
