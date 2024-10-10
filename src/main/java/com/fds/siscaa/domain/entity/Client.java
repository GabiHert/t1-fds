package com.fds.siscaa.domain.entity;

public class Client {
    public long code;
    public String name;
    public String email;
    
    public Client(long code, String name, String email) {
        this.code = code;
        this.name = name;
        this.email = email;
    }
}
