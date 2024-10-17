package com.fds.siscaa.useCases.useCases;

import com.fds.siscaa.domain.entity.SubscriptionEntity;

public class CreateSubscriptionUseCase {
    public SubscriptionEntity create(long clientCode, long applicationCode){
        return new SubscriptionEntity(0, 0, 0, null, null);
    }
}
