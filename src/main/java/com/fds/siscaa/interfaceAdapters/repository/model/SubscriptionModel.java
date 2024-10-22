package com.fds.siscaa.interfaceAdapters.repository.model;

import java.time.LocalDate;
import java.util.List;

import com.fds.siscaa.domain.entity.PromotionEntity;
import com.fds.siscaa.domain.entity.SubscriptionEntity;
import com.fds.siscaa.domain.utils.CustomList;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity()
@Table(name = "Subscription")
@Getter()
@Setter()
@AllArgsConstructor()
public class SubscriptionModel {
    @Id
    public long code;

    @Column(name = "startDate")
    public LocalDate startDate;

    @Column(name = "endDate")
    public LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "client_code", referencedColumnName = "code")
    private ClientModel client;

    @JoinColumn(name = "application_code", referencedColumnName = "code")
    @ManyToOne(cascade = CascadeType.REFRESH)
    private ApplicationModel application;

    @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL)
    private CustomList<PaymentModel> payments;

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
                application.toEntity());
    }
}
