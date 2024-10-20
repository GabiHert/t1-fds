package com.fds.siscaa.domain.entity;

public class ApplicationEntity {
    public long code;
    public String name;
    public float monthlyFee;

    public ApplicationEntity(long code, String name, float monthlyFee) {
        this.code = code;
        this.name = name;
        this.monthlyFee = monthlyFee;
    }
}
