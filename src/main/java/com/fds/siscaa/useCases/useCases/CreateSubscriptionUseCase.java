package com.fds.siscaa.useCases.useCases;

import com.fds.siscaa.domain.entity.SubscriptionEntity;

/*
 * Sempre que uma assinatura for cadastrada, o cliente ganha 7 dias grátis. 
A extensão do período de validade da assinatura se dá mediante o pagamento da mensalidade dentro desse período.
*/

public class CreateSubscriptionUseCase {
    public SubscriptionEntity create(long clientCode, long applicationCode) {
        return new SubscriptionEntity(0, 0, 0, null, null, 0);
    }
}
