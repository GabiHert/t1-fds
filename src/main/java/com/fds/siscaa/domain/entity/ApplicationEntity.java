package com.fds.siscaa.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
@AllArgsConstructor()
public class ApplicationEntity {
    private long code;
    private String name;
    private float monthlyFee;

    public ApplicationEntity() {
    }

    public ApplicationEntity(long code, String name, float monthlyFee) {
        this.code = code;
        this.name = name;
        this.monthlyFee = monthlyFee;
    }

}
