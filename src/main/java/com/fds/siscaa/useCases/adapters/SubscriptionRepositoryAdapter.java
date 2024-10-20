package com.fds.siscaa.useCases.adapters;

import com.fds.siscaa.domain.enums.*;
import java.util.Date;
import java.util.List;

import com.fds.siscaa.domain.entity.SubscriptionEntity;

public interface SubscriptionRepositoryAdapter {
    List<SubscriptionEntity> listSubscriptions(long applicationCode);

    List<SubscriptionEntity> listSubscriptions();

    List<SubscriptionEntity> listSubscriptionsByEndDate(long applicationCode, Date endDate);

    List<SubscriptionEntity> listSubscriptionsByClientCode(long clientCode);

    List<SubscriptionEntity> listSubscriptionEntityByApplicationCode(long applicationCode);

    List<SubscriptionEntity> listSubscriptionByType(SubscriptionType subscriptionType);

    SubscriptionEntity updateSubscriptionStartDateAndEndDateByCode(Date startDate, Date endDate, long subscriptionCode);

    SubscriptionEntity getSubscriptionEntityByCode(long subscriptionCode);

}