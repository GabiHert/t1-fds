package com.fds.siscaa.domain.rules;

import com.fds.siscaa.domain.enums.PaymentStatus;

public class PaymentRules {

    public static float getRefundValue(float monthlyFee, float receivedAmount) {
        return receivedAmount - monthlyFee;
    }

    public static float getAmountToBePaid(float monthlyFee, int monthsToExtend) {
        return monthlyFee * monthsToExtend;
    }

    public static int getMonthsToExtendSubscription(
            float monthlyFee, float receivedAmount) {

        int monthsToExtend = (int) (receivedAmount / monthlyFee);

        return monthsToExtend;
    }

    public static PaymentStatus getPaymentStatus(float monthlyFee, float receivedAmount) {
        if (receivedAmount < monthlyFee) {
            return PaymentStatus.VALOR_INCORRETO;
        }

        return PaymentStatus.PAGAMENTO_OK;
    }

}