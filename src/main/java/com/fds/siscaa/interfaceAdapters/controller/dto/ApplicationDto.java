package com.fds.siscaa.interfaceAdapters.controller.dto;

import com.fds.siscaa.domain.entity.ApplicationEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
@AllArgsConstructor()
public class ApplicationDto {

    private long codigoAplicativo;
    private String nome;
    private float custoMensal;

    public ApplicationDto() {

    }

    public ApplicationDto(ApplicationEntity applicationEntity) {
        this.codigoAplicativo = applicationEntity.getCode();
        this.nome = applicationEntity.getName();
        this.custoMensal = applicationEntity.getMonthlyFee();
    }

}