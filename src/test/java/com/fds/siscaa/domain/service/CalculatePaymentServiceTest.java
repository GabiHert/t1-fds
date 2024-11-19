package com.fds.siscaa.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
        when(subscription.getApplication()).thenReturn(application);
        CustomList<PromotionEntity> promotions = new CustomList<>();
        PromotionEntity promotion = new PromotionEntity();
        promotion.setExtraDays(5);
        promotion.setDiscountPercentage(10.0f);
        promotions.add(promotion);
        float receivedAmount = 220.0f;
        float monthlyFee = 200.0f;
        int monthsToExtend = 1;
        PaymentStatus paymentStatus = PaymentStatus.PAGAMENTO_OK;

        when(application.getMonthlyFee()).thenReturn(monthlyFee);
        when(paymentRules.calculateDaysToExtend(monthlyFee, receivedAmount)).thenReturn(monthsToExtend);
        when(paymentRules.calculatePaymentStatus(monthlyFee, receivedAmount)).thenReturn(paymentStatus);
        when(promotionRules.getValidPromotion(monthsToExtend, promotions)).thenReturn(Optional.of(promotion));
        when(promotionRules.applyExtraDays(monthsToExtend, promotion.getExtraDays()))
                .thenReturn((monthsToExtend * 30) + promotion.getExtraDays());
        when(promotionRules.applyDiscount(monthlyFee, promotion.getDiscountPercentage())).thenReturn(180.0f);
        when(paymentRules.calculateRefund(180.0f, receivedAmount)).thenReturn(40.0f);

        CalculatePaymentResponseEntity response = calculatePaymentService.calculate(subscription, promotions,
                receivedAmount);

        assertEquals(paymentStatus, response.getStatus());
        assertEquals(40.0f, response.getRefundedValue());
        assertEquals(180.0f, response.getPaidValue());
        assertEquals(35, response.getDaysToExtend());
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
        int monthsToExtend = 0;
        PaymentStatus paymentStatus = PaymentStatus.VALOR_INCORRETO;

        when(application.getMonthlyFee()).thenReturn(monthlyFee);
        when(paymentRules.calculateDaysToExtend(monthlyFee, receivedAmount)).thenReturn(monthsToExtend);
        when(paymentRules.calculatePaymentStatus(monthlyFee, receivedAmount)).thenReturn(paymentStatus);

        CalculatePaymentResponseEntity response = calculatePaymentService.calculate(subscription, promotions,
                receivedAmount);

        assertEquals(paymentStatus, response.getStatus());
        assertEquals(receivedAmount, response.getRefundedValue());
        assertEquals(0.0f, response.getPaidValue());
        assertEquals(0, response.getDaysToExtend());
        assertEquals(Optional.empty(), response.getPromotion());
    }

    @Test
    public void validPaymentWithoutPromotion_Status() {
        SubscriptionEntity subscription = mock(SubscriptionEntity.class);
        ApplicationEntity application = mock(ApplicationEntity.class);
        when(subscription.getApplication()).thenReturn(application);
        CustomList<PromotionEntity> promotions = new CustomList<>();
        float receivedAmount = 200.0f;
        float monthlyFee = 200.0f;
        int monthsToExtend = 1;
        PaymentStatus paymentStatus = PaymentStatus.PAGAMENTO_OK;

        when(application.getMonthlyFee()).thenReturn(monthlyFee);
        when(paymentRules.calculateDaysToExtend(monthlyFee, receivedAmount)).thenReturn(monthsToExtend);
        when(paymentRules.calculatePaymentStatus(monthlyFee, receivedAmount)).thenReturn(paymentStatus);
        when(promotionRules.getValidPromotion(monthsToExtend, promotions)).thenReturn(Optional.empty());
        when(paymentRules.calculateRefund(monthlyFee, receivedAmount)).thenReturn(0.0f);

        CalculatePaymentResponseEntity response = calculatePaymentService.calculate(subscription, promotions,
                receivedAmount);

        assertEquals(paymentStatus, response.getStatus());
        assertEquals(0.0f, response.getRefundedValue());
        assertEquals(200.0f, response.getPaidValue());
        assertEquals(30, response.getDaysToExtend());
        assertEquals(Optional.empty(), response.getPromotion());
    }

}