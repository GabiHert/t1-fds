package com.fds.siscaa.useCases.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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

public class CreateSubscriptionUseCaseTest {

    @Mock
    private SubscriptionRepositoryAdapter subscriptionRepository;

    @InjectMocks
    private CreateSubscriptionUseCase createSubscriptionUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("provideSubscriptionData")
    public void createSubscriptionSuccessfully(long clientCode, long applicationCode, int daysToExtend) {
        LocalDate startDate = CustomLocalDate.now();
        LocalDate endDate = startDate.plusDays(daysToExtend);

        SubscriptionEntity subscriptionEntity = new SubscriptionEntity(clientCode, applicationCode, startDate, endDate);
        when(subscriptionRepository.create(any(SubscriptionEntity.class))).thenReturn(subscriptionEntity);

        SubscriptionEntity result = createSubscriptionUseCase.create(clientCode, applicationCode);

        assertEquals(clientCode, result.getClient().getCode());
        assertEquals(applicationCode, result.getApplication().getCode());
        assertEquals(startDate, result.getStartDate());
        assertEquals(endDate, result.getEndDate());

        verify(subscriptionRepository, times(1)).create(any(SubscriptionEntity.class));
    }

    private static Stream<Arguments> provideSubscriptionData() {
        return Stream.of(
                Arguments.of(1L, 1L, 7),
                Arguments.of(2L, 2L, 7),
                Arguments.of(3L, 3L, 7)
        );
    }
}