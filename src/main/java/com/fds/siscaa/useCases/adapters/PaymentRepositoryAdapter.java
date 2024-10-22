package com.fds.siscaa.useCases.adapters;

import com.fds.siscaa.domain.entity.PaymentEntity;

public interface PaymentRepositoryAdapter {
    PaymentEntity create(PaymentEntity paymentEntity);
}