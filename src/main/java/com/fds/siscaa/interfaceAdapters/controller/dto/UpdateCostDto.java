package com.fds.siscaa.interfaceAdapters.controller.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor()
@Getter()
@Setter()
public class UpdateCostDto {

    @Positive(message = "'custo' deve ser um valor positivo")
    private float custo;

    public UpdateCostDto() {
    }

}
