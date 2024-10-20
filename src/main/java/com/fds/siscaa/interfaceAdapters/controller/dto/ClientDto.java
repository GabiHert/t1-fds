package com.fds.siscaa.interfaceAdapters.controller.dto;

import com.fds.siscaa.domain.entity.ClientEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
@AllArgsConstructor()
public class ClientDto {

    private long code;
    private String name;
    private String email;

    public ClientDto() {
    }

    public ClientDto(ClientEntity clientEntity) {
        this.code = clientEntity.getCode();
        this.name = clientEntity.getName();
        this.email = clientEntity.getEmail();
    }

}