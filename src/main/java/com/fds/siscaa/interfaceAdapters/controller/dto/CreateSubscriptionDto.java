package com.fds.siscaa.interfaceAdapters.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor()
@Getter()
@Setter()
public class CreateSubscriptionDto {
    private long codigoDoCliente;
    private long codigoDoAplicativo;

    public CreateSubscriptionDto() {
    }

}
