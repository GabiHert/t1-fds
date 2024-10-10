package com.fds.siscaa.domain.entity;

public class ClientEntity {
    public long code;
    public String name;
    public String email;
    
    public ClientEntity(long code, String name, String email) {
        this.code = code;
        this.name = name;
        this.email = email;
    }
}
