package com.fds.siscaa.interfaceAdapters.controller.dto;

import com.fds.siscaa.domain.entity.ApplicationEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
@AllArgsConstructor()
public class ApplicationDto {

    private long code;
    private String name;
    private float monthlyFee;

    public ApplicationDto() {

    }

    public ApplicationDto(ApplicationEntity applicationEntity) {
        this.code = applicationEntity.getCode();
        this.name = applicationEntity.getName();
        this.monthlyFee = applicationEntity.getMonthlyFee();
    }

}