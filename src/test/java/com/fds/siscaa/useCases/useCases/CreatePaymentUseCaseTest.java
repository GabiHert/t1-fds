package com.fds.siscaa.useCases.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

import com.fds.siscaa.domain.entity.ApplicationEntity;
import com.fds.siscaa.domain.entity.CalculatePaymentResponseEntity;
import com.fds.siscaa.domain.entity.PromotionEntity;
import com.fds.siscaa.domain.entity.SubscriptionEntity;
import com.fds.siscaa.domain.enums.PaymentStatus;
import com.fds.siscaa.domain.service.CalculatePaymentService;
import com.fds.siscaa.domain.utils.CustomList;
import com.fds.siscaa.useCases.adapters.SubscriptionRepositoryAdapter;
import com.fds.siscaa.useCases.adapters.PaymentRepositoryAdapter;
import com.fds.siscaa.useCases.adapters.PromotionRepositoryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CreatePaymentUseCaseTest {

        @Mock
        private SubscriptionRepositoryAdapter subscriptionRepository;

        @Mock
        private CalculatePaymentService calculatePaymentService;

        @Mock
        private PaymentRepositoryAdapter paymentRepository;

        @Mock
        private PromotionRepositoryAdapter promotionRepositoryAdapter;

        @InjectMocks
        private CreatePaymentUseCase createPaymentUseCase;

        @BeforeEach
        public void setUp() {
                MockitoAnnotations.openMocks(this);
        }

        @ParameterizedTest
        @MethodSource("provideParametersForCreatePayment")
        public void createPaymentTest(LocalDate paymentDate, int subscriptionCode, float receivedAmount, Optional<Long> promotionCode, PaymentStatus expectedStatus, float expectedRefundedValue, float expectedPaidValue, LocalDate expectedNewEndDate, Optional<PromotionEntity> expectedPromotion) {
                SubscriptionEntity subscription = mock(SubscriptionEntity.class);
                ApplicationEntity application = mock(ApplicationEntity.class);
                when(subscription.getApplication()).thenReturn(application);
                when(application.getCode()).thenReturn(1L);
                when(subscriptionRepository.getSubscriptionEntityByCode(subscriptionCode)).thenReturn(subscription);

                if (promotionCode.isPresent()) {
                        PromotionEntity promotion = new PromotionEntity(promotionCode.get(), 10.0f, 5, 6);
                        when(promotionRepositoryAdapter.getByCode(promotionCode.get())).thenReturn(promotion);
                } else {
                        CustomList<PromotionEntity> promotionEntities = new CustomList<>();
                        when(promotionRepositoryAdapter.listByApplicationCode(subscription.getApplication().getCode())).thenReturn(promotionEntities);
                }

                CalculatePaymentResponseEntity responseEntity = new CalculatePaymentResponseEntity(
                        expectedStatus, expectedNewEndDate, expectedRefundedValue, expectedPaidValue, expectedPromotion);
                when(calculatePaymentService.calculate(any(SubscriptionEntity.class), any(CustomList.class), eq(receivedAmount))).thenReturn(responseEntity);

                CalculatePaymentResponseEntity result = createPaymentUseCase.create(paymentDate, subscriptionCode, receivedAmount, promotionCode);

                assertEquals(expectedStatus, result.getStatus());
                assertEquals(expectedRefundedValue, result.getRefundedValue());
                assertEquals(expectedPaidValue, result.getPaidValue());
                assertEquals(expectedNewEndDate, result.getNewEndDate());
                assertEquals(expectedPromotion, result.getPromotion());
        }

        private static Stream<Arguments> provideParametersForCreatePayment() {
                return Stream.of(
                        Arguments.of(LocalDate.now(), 1, 100.0f, Optional.of(1L), PaymentStatus.PAGAMENTO_OK, 0.0f, 100.0f, LocalDate.now().plusDays(30), Optional.of(new PromotionEntity(1L, 10.0f, 5, 6))),
                        Arguments.of(LocalDate.now(), 1, 50.0f, Optional.empty(), PaymentStatus.VALOR_INCORRETO, 50.0f, 0.0f, LocalDate.now(), Optional.empty()),
                        Arguments.of(LocalDate.now(), 1, 100.0f, Optional.empty(), PaymentStatus.PAGAMENTO_OK, 0.0f, 100.0f, LocalDate.now().plusDays(30), Optional.empty()),
                        Arguments.of(LocalDate.now(), 1, 100.0f, Optional.of(999L), PaymentStatus.PAGAMENTO_OK, 0.0f, 100.0f, LocalDate.now().plusDays(30), Optional.empty())
                );
        }
}