package com.fds.siscaa.useCases.adapters;

import java.util.Date;
import java.util.List;

import com.fds.siscaa.domain.entity.ApplicationEntity;
import com.fds.siscaa.domain.entity.ClientEntity;
import com.fds.siscaa.domain.entity.SubscriptionEntity;

public interface RepositoryAdapter {
    List<ClientEntity> listClients();
    List<ApplicationEntity> listApplications();
    SubscriptionEntity createSubscription(SubscriptionEntity subscription);
    ApplicationEntity updateMonthlyCost(long applicationCode, long monthlyCost);
    List<SubscriptionEntity> listSubscriptions(long applicationCode);
    List<SubscriptionEntity> listSubscriptionsByEndDate(long applicationCode, Date endDate);
    List<SubscriptionEntity> listSubscriptionsByClientCode(long clientCode);
    List<SubscriptionEntity> listSubscriptionEntityByApplicationCode(long applicationCode);
    SubscriptionEntity updateSubscriptionStartDateAndEndDateByCode(Date startDate,Date endDate,long subscriptionCode);
    SubscriptionEntity getSubscriptionEntityByCode(long subscriptionCode);
}