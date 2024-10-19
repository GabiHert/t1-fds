package com.fds.siscaa.useCases.useCases;

public class CreatePaymentUseCase {
    public CreatePaymentResponse create(int day, int month, int year, int codass, double valorPago) {
        System.out.println("CreatePaymentUseCase - STARTED - day: " + day + " month: " + month + " year: " + year
                + " codass: " + codass + " valorPago: " + valorPago);
        CreatePaymentResponse createPaymentResultDto = new CreatePaymentResponse();
        return createPaymentResultDto;
    }
}
