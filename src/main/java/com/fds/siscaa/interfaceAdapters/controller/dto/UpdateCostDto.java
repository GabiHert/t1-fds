package com.fds.siscaa.interfaceAdapters.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor()
@Getter()
@Setter()
public class UpdateCostDto {

    @NotNull(message = "O campo 'custo' não pode ser nulo")
    @Positive(message = "O campo 'custo' deve ser um valor positivo")
    private float custo;

    public UpdateCostDto() {
    }

}
