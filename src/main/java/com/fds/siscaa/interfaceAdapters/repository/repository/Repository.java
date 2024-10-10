package com.fds.siscaa.interfaceAdapters.repository.repository;

import java.util.Date;
import java.util.List;

import com.fds.siscaa.domain.entity.ApplicationEntity;
import com.fds.siscaa.domain.entity.ClientEntity;
import com.fds.siscaa.domain.entity.SubscriptionEntity;
import com.fds.siscaa.interfaceAdapters.repository.model.SubscriptionModel;
import com.fds.siscaa.useCases.adapters.RepositoryAdapter;

public class Repository implements RepositoryAdapter {

    @Override
    public List<ClientEntity> listClients() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listClients'");
    }

    @Override
    public List<ApplicationEntity> listApplications() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listApplications'");
    }

    @Override
    public SubscriptionEntity createSubscription(SubscriptionEntity subscription) {
        SubscriptionModel subscriptionModel = new SubscriptionModel(subscription);

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createSubscription'");
    }

    @Override
    public ApplicationEntity updateMonthlyCost(long applicationCode, long monthlyCost) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateMonthlyCost'");
    }

    @Override
    public List<SubscriptionEntity> listSubscriptions(long applicationCode) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listSubscriptions'");
    }

    @Override
    public List<SubscriptionEntity> listSubscriptionsByEndDate(long applicationCode, Date endDate) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listSubscriptionsByEndDate'");
    }
    
}
