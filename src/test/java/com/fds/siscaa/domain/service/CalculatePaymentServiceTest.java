package com.fds.siscaa.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

import com.fds.siscaa.domain.entity.ApplicationEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fds.siscaa.domain.entity.CalculatePaymentResponseEntity;
import com.fds.siscaa.domain.entity.PromotionEntity;
import com.fds.siscaa.domain.entity.SubscriptionEntity;
import com.fds.siscaa.domain.enums.PaymentStatus;
import com.fds.siscaa.domain.rules.PaymentRules;
import com.fds.siscaa.domain.rules.PromotionRules;
import com.fds.siscaa.domain.utils.CustomList;

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

    @Test
    public void validPaymentWithPromotion() {
        SubscriptionEntity subscription = mock(SubscriptionEntity.class);
        ApplicationEntity application = mock(ApplicationEntity.class);
        LocalDate endDate = LocalDate.now().plusDays(30);
        when(subscription.getApplication()).thenReturn(application);
        when(subscription.getEndDate()).thenReturn(endDate);
        CustomList<PromotionEntity> promotions = new CustomList<>();
        PromotionEntity promotion = new PromotionEntity();
        promotion.setExtraDays(5);
        promotion.setDiscountPercentage(10.0f);
        promotions.add(promotion);
        float receivedAmount = 220.0f;
        float monthlyFee = 200.0f;
        int daysToExtend = 1;
        PaymentStatus paymentStatus = PaymentStatus.PAGAMENTO_OK;
        int totalDaysToExtend = (daysToExtend) + promotion.getExtraDays();

        when(application.getMonthlyFee()).thenReturn(monthlyFee);
        when(paymentRules.calculateDaysToExtend(monthlyFee, receivedAmount)).thenReturn(daysToExtend);
        when(paymentRules.calculatePaymentStatus(monthlyFee, receivedAmount)).thenReturn(paymentStatus);
        when(promotionRules.getValidPromotion(daysToExtend, promotions)).thenReturn(Optional.of(promotion));
        when(promotionRules.applyExtraDays(daysToExtend, promotion.getExtraDays()))
                .thenReturn(totalDaysToExtend);
        when(promotionRules.applyDiscount(daysToExtend, monthlyFee, promotion.getDiscountPercentage()))
                .thenReturn(180.0f);
        when(paymentRules.calculateRefund(monthlyFee, receivedAmount, totalDaysToExtend)).thenReturn(40.0f);

        CalculatePaymentResponseEntity response = calculatePaymentService.calculate(subscription, promotions,
                receivedAmount);

        assertEquals(paymentStatus, response.getStatus());
        assertEquals(40.0f, response.getRefundedValue());
        assertEquals(Optional.of(promotion), response.getPromotion());
    }

    @Test
    public void invalidPayment_Status() {
        SubscriptionEntity subscription = mock(SubscriptionEntity.class);
        ApplicationEntity application = mock(ApplicationEntity.class);
        when(subscription.getApplication()).thenReturn(application);
        CustomList<PromotionEntity> promotions = new CustomList<>();
        float receivedAmount = 150.0f;
        float monthlyFee = 200.0f;
        int daysToExtend = 0;
        PaymentStatus paymentStatus = PaymentStatus.VALOR_INCORRETO;

        when(application.getMonthlyFee()).thenReturn(monthlyFee);
        when(paymentRules.calculateDaysToExtend(monthlyFee, receivedAmount)).thenReturn(daysToExtend);
        when(paymentRules.calculatePaymentStatus(monthlyFee, receivedAmount)).thenReturn(paymentStatus);

        CalculatePaymentResponseEntity response = calculatePaymentService.calculate(subscription, promotions,
                receivedAmount);

        assertEquals(paymentStatus, response.getStatus());
        assertEquals(receivedAmount, response.getRefundedValue());
        assertEquals(Optional.empty(), response.getPromotion());
    }

    @Test
    public void validPaymentWithoutPromotion_Status() {
        SubscriptionEntity subscription = mock(SubscriptionEntity.class);
        ApplicationEntity application = mock(ApplicationEntity.class);
        LocalDate endDate = LocalDate.now().plusDays(30);
        when(subscription.getApplication()).thenReturn(application);
        when(subscription.getEndDate()).thenReturn(endDate);
        when(subscription.getApplication()).thenReturn(application);
        CustomList<PromotionEntity> promotions = new CustomList<>();
        float receivedAmount = 200.0f;
        float monthlyFee = 200.0f;
        int daysToExtend = 30;
        PaymentStatus paymentStatus = PaymentStatus.PAGAMENTO_OK;

        when(application.getMonthlyFee()).thenReturn(monthlyFee);
        when(paymentRules.calculateDaysToExtend(monthlyFee, receivedAmount)).thenReturn(daysToExtend);
        when(paymentRules.calculatePaymentStatus(monthlyFee, receivedAmount)).thenReturn(paymentStatus);
        when(promotionRules.getValidPromotion(daysToExtend, promotions)).thenReturn(Optional.empty());
        when(paymentRules.calculateRefund(monthlyFee, receivedAmount, daysToExtend)).thenReturn(0.0f);

        CalculatePaymentResponseEntity response = calculatePaymentService.calculate(subscription, promotions,
                receivedAmount);

        assertEquals(paymentStatus, response.getStatus());
        assertEquals(0.0f, response.getRefundedValue());
        assertEquals(Optional.empty(), response.getPromotion());
    }

}