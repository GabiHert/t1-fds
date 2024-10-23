package com.fds.siscaa.interfaceAdapters.controller.dto;

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

    @NotNull(message = "'day' não pode ser nulo")
    @Positive(message = "'day' deve ser positivo")
    @Min(value = 1, message = "O dia deve ser no mínimo 1")
    @Max(value = 31, message = "O dia deve ser no máximo 31")
    private int day;


    @Min(value = 1, message = "O mês deve ser no mínimo 1")
    @Max(value = 12, message = "O mês deve ser no máximo 12")
    @NotNull(message = "'month' não pode ser nulo")
    @Positive(message = "'month' deve ser positivo")
    private int month;

    @Min(value = 2024, message = "O ano deve ser no mínimo 2024")
    @Max(value = 2050, message = "O ano deve ser no máximo 2050")
    @NotNull(message = "'year' não pode ser nulo")
    @Positive(message = "'year' deve ser positivo")
    private int year;

    @NotNull(message = "codass' não pode ser nulo")
    @Positive(message = "'codass' deve ser positivo")
    private int codass;

    @NotNull(message = "'valorPago' não pode ser nulo")
    @Positive(message = "'valorPago' deve ser positivo")
    private float valorPago;

    public CreatePaymentDto() {
    }
}
