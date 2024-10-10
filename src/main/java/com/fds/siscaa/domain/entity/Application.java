package com.fds.siscaa.domain.entity;

public class Application {
    public long code;
    public String name;
    public long  monthlyPrice;

    public Application(long code, String name, long monthlyPrice) {
        this.code = code;
        this.name = name;
        this.monthlyPrice = monthlyPrice;
    }
}
