package com.fds.siscaa.useCases.useCases;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.fds.siscaa.domain.entity.SubscriptionEntity;
import com.fds.siscaa.domain.utils.CustomLocalDate;
import com.fds.siscaa.useCases.adapters.SubscriptionRepositoryAdapter;

import lombok.AllArgsConstructor;

@AllArgsConstructor()
@Service
public class InvalidSubscriptionUseCase {
    private final SubscriptionRepositoryAdapter subscriptionRepository;

    public boolean isInvalid(long subscriptionCode) {
        SubscriptionEntity subscriptionEntity = subscriptionRepository.getSubscriptionEntityByCode(subscriptionCode);
        if (subscriptionEntity.getEndDate().isAfter(CustomLocalDate.now())) {
            return true;
        }
        return false;
    }

}
