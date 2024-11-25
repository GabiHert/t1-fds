package com.fds.siscaa.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

import com.fds.siscaa.domain.entity.ApplicationEntity;
import com.fds.siscaa.domain.entity.CalculatePaymentResponseEntity;
import com.fds.siscaa.domain.entity.PromotionEntity;
import com.fds.siscaa.domain.entity.SubscriptionEntity;
import com.fds.siscaa.domain.enums.PaymentStatus;
import com.fds.siscaa.domain.rules.PaymentRules;
import com.fds.siscaa.domain.rules.PromotionRules;
import com.fds.siscaa.domain.utils.CustomList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CalculatePaymentServiceTest {

    @Mock
    private PaymentRules paymentRules;

    @Mock
    private PromotionRules promotionRules;

    @InjectMocks
    private CalculatePaymentService calculatePaymentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("provideParametersForCalculatePayment")
    public void calculatePaymentTest(SubscriptionEntity subscription, CustomList<PromotionEntity> promotions, float receivedAmount, float monthlyFee, int daysToExtend, PaymentStatus paymentStatus, Optional<PromotionEntity> validPromotion, float discount, float refundValue, CalculatePaymentResponseEntity expectedResponse) {
        ApplicationEntity application = mock(ApplicationEntity.class);
        when(subscription.getApplication()).thenReturn(application);
        when(subscription.getEndDate()).thenReturn(LocalDate.now().plusDays(30));
        when(application.getMonthlyFee()).thenReturn(monthlyFee);
        when(paymentRules.calculateDaysToExtend(monthlyFee, receivedAmount)).thenReturn(daysToExtend);
        when(paymentRules.calculatePaymentStatus(monthlyFee, receivedAmount)).thenReturn(paymentStatus);
        when(promotionRules.getValidPromotion(daysToExtend, promotions)).thenReturn(validPromotion);
        if (validPromotion.isPresent()) {
            when(promotionRules.applyExtraDays(daysToExtend, validPromotion.get().getExtraDays())).thenReturn(daysToExtend + validPromotion.get().getExtraDays());
            when(promotionRules.applyDiscount(daysToExtend, monthlyFee, validPromotion.get().getDiscountPercentage())).thenReturn(discount);
            when(paymentRules.calculateRefund(monthlyFee, receivedAmount, daysToExtend + validPromotion.get().getExtraDays())).thenReturn(refundValue);
        }
        else
            when(paymentRules.calculateRefund(monthlyFee, receivedAmount, daysToExtend)).thenReturn(refundValue);


        CalculatePaymentResponseEntity response = calculatePaymentService.calculate(subscription, promotions, receivedAmount);

        assertEquals(expectedResponse.getStatus(), response.getStatus());
        assertEquals(expectedResponse.getRefundedValue(), response.getRefundedValue());
        assertEquals(expectedResponse.getPromotion(), response.getPromotion());

        verify(paymentRules).calculateDaysToExtend(monthlyFee, receivedAmount);
        verify(paymentRules).calculatePaymentStatus(monthlyFee, receivedAmount);
        if (validPromotion.isPresent()) {
            verify(promotionRules).applyExtraDays(daysToExtend, validPromotion.get().getExtraDays());
            verify(promotionRules).applyDiscount(daysToExtend + validPromotion.get().getExtraDays(), monthlyFee, validPromotion.get().getDiscountPercentage());
        }
        if(response.getStatus()  != PaymentStatus.VALOR_INCORRETO && validPromotion.isPresent())
            verify(paymentRules).calculateRefund(monthlyFee, receivedAmount, daysToExtend + validPromotion.get().getExtraDays());
        else if(response.getStatus()  != PaymentStatus.VALOR_INCORRETO)
            verify(paymentRules).calculateRefund(monthlyFee, receivedAmount, daysToExtend);
    }

    private static Stream<Arguments> provideParametersForCalculatePayment() {
        SubscriptionEntity subscription = mock(SubscriptionEntity.class);
        CustomList<PromotionEntity> promotions = new CustomList<>();
        PromotionEntity promotion = new PromotionEntity();
        promotion.setExtraDays(5);
        promotion.setDiscountPercentage(10.0f);
        promotions.add(promotion);

        return Stream.of(
                Arguments.of(subscription, promotions, 220.0f, 200.0f, 30, PaymentStatus.PAGAMENTO_OK, Optional.of(promotion), 180.0f, 40.0f, new CalculatePaymentResponseEntity(PaymentStatus.PAGAMENTO_OK, LocalDate.now().plusDays(35), 40.0f, 180.0f, Optional.of(promotion))),
                Arguments.of(subscription, new CustomList<>(), 150.0f, 200.0f, 0, PaymentStatus.VALOR_INCORRETO, Optional.empty(), 0.0f, 150.0f, new CalculatePaymentResponseEntity(PaymentStatus.VALOR_INCORRETO, LocalDate.now().plusDays(30), 150.0f, 0.0f, Optional.empty())),
                Arguments.of(subscription, new CustomList<>(), 200.0f, 200.0f, 30, PaymentStatus.PAGAMENTO_OK, Optional.empty(), 0.0f, 0.0f, new CalculatePaymentResponseEntity(PaymentStatus.PAGAMENTO_OK, LocalDate.now().plusDays(30), 0.0f, 200.0f, Optional.empty()))
        );
    }
}