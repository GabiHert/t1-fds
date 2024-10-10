package com.fds.siscaa.interfaceAdapters.repository.model;

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
}
