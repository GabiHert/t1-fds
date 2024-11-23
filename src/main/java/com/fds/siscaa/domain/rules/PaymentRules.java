package com.fds.siscaa.domain.rules;

import org.springframework.stereotype.Component;

import com.fds.siscaa.domain.enums.PaymentStatus;

@Component
public class PaymentRules {

    public float calculateRefund(float monthlyFee, float receivedAmount, int daysToExtend) {
        return receivedAmount - ((daysToExtend/30) * monthlyFee);
    }

    public int calculateDaysToExtend(
            float monthlyFee, float receivedAmount) {

        int monthsToExtend = (int) (receivedAmount / monthlyFee);

        return monthsToExtend * 30;
    }

    public PaymentStatus calculatePaymentStatus(float monthlyFee, float receivedAmount) {
        if (receivedAmount < monthlyFee) {
            return PaymentStatus.VALOR_INCORRETO;
        }

        return PaymentStatus.PAGAMENTO_OK;
    }

}