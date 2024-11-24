package com.fds.siscaa.useCases.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.stream.Stream;

import com.fds.siscaa.domain.entity.SubscriptionEntity;
import com.fds.siscaa.domain.utils.CustomLocalDate;
import com.fds.siscaa.useCases.adapters.SubscriptionRepositoryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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

    @ParameterizedTest
    @MethodSource("provideSubscriptionData")
    public void testIsValid(long subscriptionCode, LocalDate startDate, LocalDate endDate, boolean expectedResult) {
        SubscriptionEntity subscriptionEntity = new SubscriptionEntity(subscriptionCode, null, startDate, endDate, null, null);
        when(subscriptionRepository.getSubscriptionEntityByCode(subscriptionCode)).thenReturn(subscriptionEntity);

        boolean result = validSubscriptionUseCase.isValid(subscriptionCode);

        assertEquals(expectedResult, result);
    }

    private static Stream<Arguments> provideSubscriptionData() {
        return Stream.of(
                Arguments.of(1L, CustomLocalDate.now(), CustomLocalDate.now().plusDays(10), true),
                Arguments.of(1L, CustomLocalDate.now().minusDays(20), CustomLocalDate.now().minusDays(10), false),
                Arguments.of(1L, CustomLocalDate.now().minusDays(10), CustomLocalDate.now(), false),
                Arguments.of(1L, CustomLocalDate.now().plusDays(10), CustomLocalDate.now().plusDays(20), true),
                Arguments.of(1L, CustomLocalDate.now(), null, false),
                Arguments.of(1L, null, CustomLocalDate.now().plusDays(10), true)
        );
    }
}