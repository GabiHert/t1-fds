package com.fds.siscaa.interfaceAdapters.controller.dto;

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

}