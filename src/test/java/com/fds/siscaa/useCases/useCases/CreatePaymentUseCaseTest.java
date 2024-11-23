package com.fds.siscaa.useCases.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import com.fds.siscaa.domain.entity.ApplicationEntity;
import com.fds.siscaa.domain.entity.CalculatePaymentResponseEntity;
import com.fds.siscaa.domain.entity.PaymentEntity;
import com.fds.siscaa.domain.entity.PromotionEntity;
import com.fds.siscaa.domain.entity.SubscriptionEntity;
import com.fds.siscaa.domain.enums.PaymentStatus;
import com.fds.siscaa.domain.service.CalculatePaymentService;
import com.fds.siscaa.domain.utils.CustomList;
import com.fds.siscaa.useCases.adapters.SubscriptionRepositoryAdapter;
import com.fds.siscaa.useCases.adapters.PaymentRepositoryAdapter;
import com.fds.siscaa.useCases.adapters.PromotionRepositoryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

        @Test
        public void createPaymentWithValidPromotion() {
                LocalDate paymentDate = LocalDate.now();
                int subscriptionCode = 1;
                float receivedAmount = 100.0f;
                Optional<Long> promotionCode = Optional.of(1L);

                SubscriptionEntity subscription = mock(SubscriptionEntity.class);
                ApplicationEntity application = mock(ApplicationEntity.class);
                when(subscription.getApplication()).thenReturn(application);
                when(application.getCode()).thenReturn(1L);
                when(subscriptionRepository.getSubscriptionEntityByCode(subscriptionCode)).thenReturn(subscription);

                PromotionEntity promotion = new PromotionEntity(1L, 10.0f, 5, 6);
                CustomList<PromotionEntity> promotionEntities = new CustomList<>();
                promotionEntities.add(promotion);
                when(promotionRepositoryAdapter.getByCode(1L)).thenReturn(promotion);

                CalculatePaymentResponseEntity responseEntity = new CalculatePaymentResponseEntity(
                        PaymentStatus.PAGAMENTO_OK, LocalDate.now().plusDays(30), 0.0f, receivedAmount, Optional.of(promotion));
                when(calculatePaymentService.calculate(any(SubscriptionEntity.class), any(CustomList.class), eq(receivedAmount))).thenReturn(responseEntity);

                CalculatePaymentResponseEntity result = createPaymentUseCase.create(paymentDate, subscriptionCode, receivedAmount, promotionCode);

                assertEquals(PaymentStatus.PAGAMENTO_OK, result.getStatus());
                assertEquals(0.0f, result.getRefundedValue());
                assertEquals(receivedAmount, result.getPaidValue());
                assertEquals(LocalDate.now().plusDays(30), result.getNewEndDate());
                assertEquals(Optional.of(promotion), result.getPromotion());
        }
        @Test
        public void createPaymentWithInvalidAmount() {
                LocalDate paymentDate = LocalDate.now();
                int subscriptionCode = 1;
                float receivedAmount = 50.0f;
                Optional<Long> promotionCode = Optional.empty();

                SubscriptionEntity subscription = mock(SubscriptionEntity.class);
                ApplicationEntity application = mock(ApplicationEntity.class);
                when(subscription.getApplication()).thenReturn(application);
                when(application.getCode()).thenReturn(1L);
                when(subscriptionRepository.getSubscriptionEntityByCode(subscriptionCode)).thenReturn(subscription);

                CustomList<PromotionEntity> promotionEntities = new CustomList<>();

                CalculatePaymentResponseEntity responseEntity = new CalculatePaymentResponseEntity(
                        PaymentStatus.VALOR_INCORRETO, LocalDate.now(), receivedAmount, 0.0f, Optional.empty());
                when(calculatePaymentService.calculate(eq(subscription), eq(promotionEntities), eq(receivedAmount))).thenReturn(responseEntity);

                CalculatePaymentResponseEntity result = createPaymentUseCase.create(paymentDate, subscriptionCode, receivedAmount, promotionCode);

                assertEquals(PaymentStatus.VALOR_INCORRETO, result.getStatus());
                assertEquals(0.0f, result.getRefundedValue());
                assertEquals(0.0f, result.getPaidValue());
                assertEquals(LocalDate.now(), result.getNewEndDate());
                assertEquals(Optional.empty(), result.getPromotion());
        }

        @Test
        public void createPaymentWithNoPromotion() {
                LocalDate paymentDate = LocalDate.now();
                int subscriptionCode = 1;
                float receivedAmount = 100.0f;
                Optional<Long> promotionCode = Optional.empty();

                SubscriptionEntity subscription = mock(SubscriptionEntity.class);
                ApplicationEntity application = mock(ApplicationEntity.class);
                when(subscription.getApplication()).thenReturn(application);
                when(application.getCode()).thenReturn(1L);
                when(subscriptionRepository.getSubscriptionEntityByCode(subscriptionCode)).thenReturn(subscription);

                CustomList<PromotionEntity> promotionEntities = new CustomList<>();
                when(promotionRepositoryAdapter.listByApplicationCode(1L)).thenReturn(promotionEntities);

                CalculatePaymentResponseEntity responseEntity = new CalculatePaymentResponseEntity(
                        PaymentStatus.PAGAMENTO_OK, LocalDate.now().plusDays(30), 0.0f, receivedAmount, Optional.empty());
                when(calculatePaymentService.calculate(any(SubscriptionEntity.class), any(CustomList.class), eq(receivedAmount))).thenReturn(responseEntity);

                CalculatePaymentResponseEntity result = createPaymentUseCase.create(paymentDate, subscriptionCode, receivedAmount, promotionCode);

                assertEquals(PaymentStatus.PAGAMENTO_OK, result.getStatus());
                assertEquals(0.0f, result.getRefundedValue());
                assertEquals(receivedAmount, result.getPaidValue());
                assertEquals(LocalDate.now().plusDays(30), result.getNewEndDate());
                assertEquals(Optional.empty(), result.getPromotion());
        }

        @Test
        public void createPaymentWithInvalidPromotion() {
                LocalDate paymentDate = LocalDate.now();
                int subscriptionCode = 1;
                float receivedAmount = 100.0f;
                Optional<Long> promotionCode = Optional.of(999L); // Invalid promotion code

                SubscriptionEntity subscription = mock(SubscriptionEntity.class);
                ApplicationEntity application = mock(ApplicationEntity.class);
                when(subscription.getApplication()).thenReturn(application);
                when(application.getCode()).thenReturn(1L);
                when(subscriptionRepository.getSubscriptionEntityByCode(subscriptionCode)).thenReturn(subscription);

                when(promotionRepositoryAdapter.getByCode(999L)).thenReturn(null); // Invalid promotion

                CustomList<PromotionEntity> promotionEntities = new CustomList<>();
                when(promotionRepositoryAdapter.listByApplicationCode(1L)).thenReturn(promotionEntities);

                CalculatePaymentResponseEntity responseEntity = new CalculatePaymentResponseEntity(
                        PaymentStatus.PAGAMENTO_OK, LocalDate.now().plusDays(30), 0.0f, receivedAmount, Optional.empty());
                when(calculatePaymentService.calculate(any(SubscriptionEntity.class), any(CustomList.class), eq(receivedAmount))).thenReturn(responseEntity);

                CalculatePaymentResponseEntity result = createPaymentUseCase.create(paymentDate, subscriptionCode, receivedAmount, promotionCode);

                assertEquals(PaymentStatus.PAGAMENTO_OK, result.getStatus());
                assertEquals(0.0f, result.getRefundedValue());
                assertEquals(receivedAmount, result.getPaidValue());
                assertEquals(LocalDate.now().plusDays(30), result.getNewEndDate());
                assertEquals(Optional.empty(), result.getPromotion());
        }
}