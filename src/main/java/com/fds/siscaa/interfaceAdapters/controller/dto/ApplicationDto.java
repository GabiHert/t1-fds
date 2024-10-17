package com.fds.siscaa.interfaceAdapters.controller.dto;

public class ApplicationDto {
    
    public long code;
    public String name;
    public long  monthlyPrice;
    
    public ApplicationDto(long code, String name, long monthlyPrice) {
        this.code = code;
        this.name = name;
        this.monthlyPrice = monthlyPrice;
    }
    
    
}