package com.fds.siscaa.interfaceAdapters.repository.model;

import java.util.Date;

import com.fds.siscaa.domain.entity.SubscriptionEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

public class SubscriptionModel {
    @Id
    public long code;
    public Date startDate;
    public Date endDate;

    @ManyToOne(cascade = CascadeType.REFRESH)
    public ClientModel client;

    @ManyToOne(cascade = CascadeType.REFRESH)
    public ApplicationModel application;

    protected SubscriptionModel() {
    }

    public SubscriptionModel(SubscriptionEntity subscriptionEntity) {
        this.code = subscriptionEntity.code;
        this.startDate = subscriptionEntity.startDate;
        this.endDate = subscriptionEntity.endDate;

        this.client = new ClientModel();
        this.client.code = subscriptionEntity.clientCode;
    }
}
