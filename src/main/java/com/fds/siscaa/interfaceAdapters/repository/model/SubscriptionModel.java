package com.fds.siscaa.interfaceAdapters.repository.model;

import java.time.LocalDate;

import com.fds.siscaa.domain.entity.ApplicationEntity;
import com.fds.siscaa.domain.entity.ClientEntity;
import com.fds.siscaa.domain.entity.PromotionEntity;
import com.fds.siscaa.domain.entity.SubscriptionEntity;
import com.fds.siscaa.domain.utils.CustomList;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Entity()
@Getter()
@Setter()
@AllArgsConstructor()


public class SubscriptionModel {
    @Id
    public long code;
    public LocalDate startDate;
    public LocalDate endDate;

    @ManyToOne(cascade = CascadeType.REFRESH)
    public ClientModel client;

    @ManyToOne(cascade = CascadeType.REFRESH)
    public ApplicationModel application;

    @OneToMany(cascade = CascadeType.REFRESH)
    public CustomList<ApplicationModel> promotions;

    protected SubscriptionModel() {
    }

    public SubscriptionModel(SubscriptionEntity subscriptionEntity) {
        this.code = subscriptionEntity.getCode();
        this.startDate = subscriptionEntity.getStartDate();
        this.endDate = subscriptionEntity.getEndDate();
        this.client = new ClientModel(subscriptionEntity.getClient());
        this.application = new ApplicationModel(subscriptionEntity.getApplication());
    }

    public SubscriptionEntity toEntity() {
        return new SubscriptionEntity(
                code, client.toEntity(),
                startDate, endDate,
                application.toEntity(),
                promotions.toEntities(PromotionEntity.class));
    }
}
