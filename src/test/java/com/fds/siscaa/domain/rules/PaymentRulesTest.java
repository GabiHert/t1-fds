package com.fds.siscaa.domain.rules;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.fds.siscaa.domain.enums.PaymentStatus;

public class PaymentRulesTest {

    private PaymentRules paymentRules;

    @BeforeEach
    public void setUp() {
        paymentRules = new PaymentRules();
    }

    @ParameterizedTest
    @MethodSource("provideDaysToExtendData")
    public void testCalculateDaysToExtend(float monthlyFee, float receivedAmount, int expectedDays) {
        int actualDays = paymentRules.calculateDaysToExtend(monthlyFee, receivedAmount);
        assertEquals(expectedDays, actualDays);
    }

    private static Stream<Arguments> provideDaysToExtendData() {
        return Stream.of(
                Arguments.of(100.0f, 300.0f, 90),
                Arguments.of(100.0f, 350.0f, 90),
                Arguments.of(100.0f, 0.0f, 0),
                Arguments.of(100.0f, 50.0f, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("providePaymentStatusData")
    public void testCalculatePaymentStatus(float monthlyFee, float receivedAmount, PaymentStatus expectedStatus) {
        PaymentStatus actualStatus = paymentRules.calculatePaymentStatus(monthlyFee, receivedAmount);
        assertEquals(expectedStatus, actualStatus);
    }

    private static Stream<Arguments> providePaymentStatusData() {
        return Stream.of(
                Arguments.of(100.0f, 50.0f, PaymentStatus.VALOR_INCORRETO),
                Arguments.of(100.0f, 100.0f, PaymentStatus.PAGAMENTO_OK),
                Arguments.of(100.0f, 150.0f, PaymentStatus.PAGAMENTO_OK)
        );
    }

    @ParameterizedTest
    @MethodSource("provideRefundData")
    public void testCalculateRefund(float monthlyFee, float receivedAmount, int daysToExtend, float expectedRefund) {
        float actualRefund = paymentRules.calculateRefund(monthlyFee, receivedAmount, daysToExtend);
        assertEquals(expectedRefund, actualRefund);
    }

    private static Stream<Arguments> provideRefundData() {
        return Stream.of(
                Arguments.of(100.0f, 300.0f, 90, 0.0f),
                Arguments.of(100.0f, 350.0f, 90, 50.0f),
                Arguments.of(100.0f, 0.0f, 0, 0.0f),
                Arguments.of(100.0f, 50.0f, 0, 50.0f)
        );
    }
}