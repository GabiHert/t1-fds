package com.fds.siscaa.interfaceAdapters.repository.model;

import java.util.Date;

import com.fds.siscaa.domain.entity.ApplicationEntity;
import com.fds.siscaa.domain.entity.ClientEntity;
import com.fds.siscaa.domain.entity.PromotionEntity;
import com.fds.siscaa.domain.entity.SubscriptionEntity;
import com.fds.siscaa.domain.utils.CustomList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

public class SubscriptionModel {
    @Id
    public long code;
    public Date startDate;
    public Date endDate;

    @ManyToOne(cascade = CascadeType.REFRESH)
    public ClientModel client;

    @ManyToOne(cascade = CascadeType.REFRESH)
    public ApplicationModel application;

    @OneToMany(cascade = CascadeType.REFRESH)
    public CustomList<ApplicationModel> promotions;

    protected SubscriptionModel() {
    }

    public SubscriptionModel(SubscriptionEntity subscriptionEntity, ApplicationEntity applicationEntity,
            ClientEntity clientEntity) {
        this.code = subscriptionEntity.getCode();
        this.startDate = subscriptionEntity.getStartDate();
        this.endDate = subscriptionEntity.getEndDate();
        this.client = new ClientModel(clientEntity);
        this.application = new ApplicationModel(applicationEntity);
    }

    public SubscriptionEntity toEntity() {
        return new SubscriptionEntity(
                code, client.toEntity(),
                startDate, endDate,
                application.toEntity(),
                promotions.toEntities(PromotionEntity.class));
    }
}
