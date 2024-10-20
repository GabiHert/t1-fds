package com.fds.siscaa.domain.rules;

public class PaymentRules {

    public static float getDiscountValue(float monthlyFee, float receivedAmount) {
        return receivedAmount - monthlyFee;
    }


    public static float getRefundValue(float monthlyFee, float receivedAmount) {
        return receivedAmount - monthlyFee;
    }


    public static int getDaysToExtendSubscription(float monthlyFee, float receivedAmount) {
        int monthsToExtend = (int) (receivedAmount / monthlyFee);
        float amountToBePaid = monthsToExtend * monthlyFee;

        if (amountToBePaid > receivedAmount) {
            // throw exception
        }

        return monthsToExtend * 30;
    }

}