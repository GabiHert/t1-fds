package com.fds.siscaa.useCases.adapters;

import com.fds.siscaa.domain.enums.*;
import java.time.LocalDate;

import com.fds.siscaa.domain.entity.SubscriptionEntity;
import com.fds.siscaa.domain.utils.CustomList;

public interface SubscriptionRepositoryAdapter {
    CustomList<SubscriptionEntity> listSubscriptions(long applicationCode);

    CustomList<SubscriptionEntity> listSubscriptions();

    CustomList<SubscriptionEntity> listSubscriptionsByEndDate(long applicationCode, LocalDate endDate);

    CustomList<SubscriptionEntity> listSubscriptionsByClientCode(long clientCode);

    CustomList<SubscriptionEntity> listSubscriptionEntityByApplicationCode(long applicationCode);

    CustomList<SubscriptionEntity> listSubscriptionByType(SubscriptionType subscriptionType);

    SubscriptionEntity updateSubscriptionStartDateAndEndDateByCode(LocalDate startDate, LocalDate endDate,
            long subscriptionCode);

    SubscriptionEntity getSubscriptionEntityByCode(long subscriptionCode);

}