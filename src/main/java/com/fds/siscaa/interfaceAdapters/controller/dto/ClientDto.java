package com.fds.siscaa.interfaceAdapters.controller.dto;

public class ClientDto {
    
    public long code;
    public String name;
    public String email;
    
    public ClientDto(long code, String name, String email) {
        this.code = code;
        this.name = name;
        this.email = email;
    }
    
    
}