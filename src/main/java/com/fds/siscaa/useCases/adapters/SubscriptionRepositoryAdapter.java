package com.fds.siscaa.useCases.adapters;

import com.fds.siscaa.domain.enums.*;
import java.util.Date;
import java.util.List;

import com.fds.siscaa.domain.entity.SubscriptionEntity;
import com.fds.siscaa.domain.utils.CustomList;

public interface SubscriptionRepositoryAdapter {
    CustomList<SubscriptionEntity> listSubscriptions(long applicationCode);

    CustomList<SubscriptionEntity> listSubscriptions();

    CustomList<SubscriptionEntity> listSubscriptionsByEndDate(long applicationCode, Date endDate);

    CustomList<SubscriptionEntity> listSubscriptionsByClientCode(long clientCode);

    CustomList<SubscriptionEntity> listSubscriptionEntityByApplicationCode(long applicationCode);

    CustomList<SubscriptionEntity> listSubscriptionByType(SubscriptionType subscriptionType);

    SubscriptionEntity updateSubscriptionStartDateAndEndDateByCode(Date startDate, Date endDate, long subscriptionCode);

    SubscriptionEntity getSubscriptionEntityByCode(long subscriptionCode);

}