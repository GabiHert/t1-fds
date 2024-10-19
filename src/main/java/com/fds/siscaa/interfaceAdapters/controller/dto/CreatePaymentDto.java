package com.fds.siscaa.interfaceAdapters.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
@AllArgsConstructor()
public class CreatePaymentDto {
    private int day;
    private int month;
    private int year;
    private int codass;
    private double valorPago;

    public CreatePaymentDto() {
    }
}
