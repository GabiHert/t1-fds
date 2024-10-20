package com.fds.siscaa.domain.rules;

import com.fds.siscaa.domain.entity.ApplicationEntity;

public class PaymentRules {

    public static float getDaysToExtendSubscription(ApplicationEntity applicationEntity, float paidAmount) {
        float remainingValue = (applicationEntity.getMonthlyFee() - paidAmount);

        

        return remainingValue;
    }
}
