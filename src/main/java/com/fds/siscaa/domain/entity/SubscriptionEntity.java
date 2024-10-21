package com.fds.siscaa.domain.entity;

import java.time.LocalDate;

import com.fds.siscaa.domain.utils.CustomList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
@AllArgsConstructor()
public class SubscriptionEntity {
    private long code;
    private ClientEntity client;
    private LocalDate startDate;
    private LocalDate endDate;
    private ApplicationEntity application;
    private CustomList<PromotionEntity> promotions;

    public SubscriptionEntity() {
    }

    public SubscriptionEntity(long code, ClientEntity clientEntity, LocalDate sDate, LocalDate eDate,
            ApplicationEntity applicationEntity) {
        this.code = code;
        this.client = clientEntity;
        this.startDate = sDate;
        this.endDate = eDate;
        this.application = applicationEntity;
    }

    public SubscriptionEntity(long clientCode, long applicationCode, LocalDate startDate, LocalDate endDate) {
        this.client = new ClientEntity(clientCode);
        this.application = new ApplicationEntity(applicationCode);
    }
}
