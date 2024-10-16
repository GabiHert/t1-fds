package com.fds.siscaa.interfaceAdapters.repository.model;

import com.fds.siscaa.domain.entity.ClientEntity;

import jakarta.persistence.Id;

public class ClientModel {
    @Id
    public long code;
    public String name;
    public String email;

    protected ClientModel() {
    }
    
    public ClientModel(long code, String name, String email) {
        this.code = code;
        this.name = name;
        this.email = email;
    }
    
    public ClientModel(ClientEntity clientEntity) {
        this.code = clientEntity.code;
        this.name = clientEntity.name;
        this.email = clientEntity.email;
    }

    public ClientEntity toEntity() {
        return new ClientEntity(code, name, email);
    }
}
