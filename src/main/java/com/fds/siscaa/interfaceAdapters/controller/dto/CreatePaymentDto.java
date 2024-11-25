package com.fds.siscaa.interfaceAdapters.controller.dto;

import java.util.List;
import java.util.Optional;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
@AllArgsConstructor()
public class CreatePaymentDto {

    @Min(value = 1, message = "'day' deve ser maior que 0")
    @Max(value = 31, message = "'day' deve ser menor que 32")
    private int day;

    @Min(value = 1, message = "'month' deve ser maior que 0")
    @Max(value = 12, message = "'month' deve ser menor que 13")
    private int month;

    @Min(value = 2024, message = "'year' deve ser maior que 2024")
    private int year;

    @Positive(message = "'codass' deve ser positivo")
    private int codass;

    @Positive(message = "'valorPago' deve ser positivo")
    private float valorPago;

    private Optional<Long> codpromo;

    public CreatePaymentDto() {
    }
}
