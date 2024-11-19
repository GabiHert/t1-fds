package com.fds.siscaa.domain.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fds.siscaa.domain.enums.PaymentStatus;

public class PaymentRulesTest {

    private PaymentRules paymentRules;

    @BeforeEach
    public void setUp() {
        paymentRules = new PaymentRules();
    }

    @Test
    public void testCalculateMonthsToExtend_ExactDivision() {
        float monthlyFee = 100.0f;
        float receivedAmount = 300.0f;
        int expectedMonths = 3;

        int actualMonths = paymentRules.calculateDaysToExtend(monthlyFee, receivedAmount);

        assertEquals(expectedMonths, actualMonths);
    }

    @Test
    public void testCalculateMonthsToExtend_NotExactDivision() {
        float monthlyFee = 100.0f;
        float receivedAmount = 350.0f;
        int expectedMonths = 3;

        int actualMonths = paymentRules.calculateDaysToExtend(monthlyFee, receivedAmount);

        assertEquals(expectedMonths, actualMonths);
    }

    @Test
    public void testCalculateMonthsToExtend_ZeroReceivedAmount() {
        float monthlyFee = 100.0f;
        float receivedAmount = 0.0f;
        int expectedMonths = 0;

        int actualMonths = paymentRules.calculateDaysToExtend(monthlyFee, receivedAmount);

        assertEquals(expectedMonths, actualMonths);
    }

    @Test
    public void testCalculateMonthsToExtend_ReceivedAmountLessThanMonthlyFee() {
        float monthlyFee = 100.0f;
        float receivedAmount = 50.0f;
        int expectedMonths = 0;

        int actualMonths = paymentRules.calculateDaysToExtend(monthlyFee, receivedAmount);

        assertEquals(expectedMonths, actualMonths);
    }

    @Test
    public void testCalculatePaymentStatus_ReceivedAmountLessThanMonthlyFee() {
        float monthlyFee = 100.0f;
        float receivedAmount = 50.0f;
        PaymentStatus expectedStatus = PaymentStatus.VALOR_INCORRETO;

        PaymentStatus actualStatus = paymentRules.calculatePaymentStatus(monthlyFee, receivedAmount);

        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void testCalculatePaymentStatus_ReceivedAmountEqualToMonthlyFee() {
        float monthlyFee = 100.0f;
        float receivedAmount = 100.0f;
        PaymentStatus expectedStatus = PaymentStatus.PAGAMENTO_OK;

        PaymentStatus actualStatus = paymentRules.calculatePaymentStatus(monthlyFee, receivedAmount);

        assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void testCalculatePaymentStatus_ReceivedAmountGreaterThanMonthlyFee() {
        float monthlyFee = 100.0f;
        float receivedAmount = 150.0f;
        PaymentStatus expectedStatus = PaymentStatus.PAGAMENTO_OK;

        PaymentStatus actualStatus = paymentRules.calculatePaymentStatus(monthlyFee, receivedAmount);

        assertEquals(expectedStatus, actualStatus);
    }

}