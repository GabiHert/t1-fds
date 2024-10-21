package com.fds.siscaa.domain.entity;

import java.util.Date;

import com.fds.siscaa.domain.utils.CustomList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
@AllArgsConstructor()
public class SubscriptionEntity {
    private long code;
    private ClientEntity clientEntity;
    private Date startDate;
    private Date endDate;
    private ApplicationEntity application;
    private CustomList<PromotionEntity> promotions;

    public SubscriptionEntity() {
    }

    public SubscriptionEntity(long code, ClientEntity clientEntity, Date sDate, Date eDate,
            ApplicationEntity applicationEntity) {
        this.code = code;
        this.clientEntity = clientEntity;
        this.startDate = sDate;
        this.endDate = eDate;
        this.application = applicationEntity;
    }
}
