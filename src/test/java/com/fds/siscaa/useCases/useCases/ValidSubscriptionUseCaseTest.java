package com.fds.siscaa.useCases.useCases;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

import com.fds.siscaa.domain.entity.SubscriptionEntity;
import com.fds.siscaa.domain.utils.CustomLocalDate;
import com.fds.siscaa.useCases.adapters.SubscriptionRepositoryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ValidSubscriptionUseCaseTest {

    @Mock
    private SubscriptionRepositoryAdapter subscriptionRepository;

    @InjectMocks
    private ValidSubscriptionUseCase validSubscriptionUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIsValidWithValidSubscription() {
        long subscriptionCode = 1;
        SubscriptionEntity subscriptionEntity = new SubscriptionEntity(subscriptionCode, null, CustomLocalDate.now(), CustomLocalDate.now().plusDays(10), null, null);
        when(subscriptionRepository.getSubscriptionEntityByCode(subscriptionCode)).thenReturn(subscriptionEntity);

        boolean result = validSubscriptionUseCase.isValid(subscriptionCode);

        assertTrue(result);
    }

    @Test
    public void testIsValidWithExpiredSubscription() {
        long subscriptionCode = 1;
        SubscriptionEntity subscriptionEntity = new SubscriptionEntity(subscriptionCode, null, CustomLocalDate.now().minusDays(20), CustomLocalDate.now().minusDays(10), null, null);
        when(subscriptionRepository.getSubscriptionEntityByCode(subscriptionCode)).thenReturn(subscriptionEntity);

        boolean result = validSubscriptionUseCase.isValid(subscriptionCode);

        assertFalse(result);
    }
}