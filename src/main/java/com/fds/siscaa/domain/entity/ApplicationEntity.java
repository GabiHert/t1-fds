package com.fds.siscaa.domain.entity;

import com.fds.siscaa.domain.utils.CustomList;
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
    private CustomList<PromotionEntity> promotions;

    public ApplicationEntity() {
    }

    public ApplicationEntity(long code) {
        this.code = code;
    }

}
