package com.fds.siscaa.interfaceAdapters.repository.model;

import com.fds.siscaa.domain.entity.ApplicationEntity;

import com.fds.siscaa.domain.entity.PromotionEntity;
import com.fds.siscaa.domain.utils.CustomList;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Application")
@Getter()
@Setter()
@AllArgsConstructor()
public class ApplicationModel {
    @Id
    private long code;
    private String name;
    private float monthlyFee;

    @OneToMany(mappedBy = "application", cascade = CascadeType.REFRESH)
    private CustomList<SubscriptionModel> subscriptions;

    @OneToMany(mappedBy = "application", cascade = CascadeType.REFRESH)
    private CustomList<PromotionModel> promotions;

    protected ApplicationModel() {
    }

    public ApplicationModel(ApplicationEntity applicationEntity) {
        this.code = applicationEntity.getCode();
        this.name = applicationEntity.getName();
        this.monthlyFee = applicationEntity.getMonthlyFee();
    }

    public ApplicationEntity toEntity() {
        return new ApplicationEntity(code, name, monthlyFee, promotions.toEntities(PromotionEntity.class));
    }
}
