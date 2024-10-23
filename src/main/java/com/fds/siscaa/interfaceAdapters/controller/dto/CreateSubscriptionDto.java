package com.fds.siscaa.interfaceAdapters.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor()
@Getter()
@Setter()
public class CreateSubscriptionDto {

    @NotNull(message = "o campo 'codigoDoCliente' não pode ser nulo")
    @Positive(message = "o campo 'codigoDoCliente' deve ser um número positivo")
    private long codigoDoCliente;

    @NotNull(message = "o campo 'codigoDoAplicativo' não pode ser nulo")
    @Positive(message = "o campo 'codigoDoAplicativo' deve ser um número positivo")
    private long codigoDoAplicativo;

    public CreateSubscriptionDto() {
    }

}
