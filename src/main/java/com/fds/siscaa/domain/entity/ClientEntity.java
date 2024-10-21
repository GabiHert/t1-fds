package com.fds.siscaa.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
@AllArgsConstructor()
public class ClientEntity {
    private long code;
    private String name;
    private String email;

    public ClientEntity() {
    }

    public ClientEntity(long code) {
        this.code = code;
    }
}
