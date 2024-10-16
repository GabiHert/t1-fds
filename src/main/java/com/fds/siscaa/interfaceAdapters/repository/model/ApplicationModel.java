package com.fds.siscaa.interfaceAdapters.repository.model;

import com.fds.siscaa.domain.entity.ApplicationEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ApplicationModel {
    @Id
    public long code;
    public String name;
    public long  monthlyPrice;

    @ManyToOne(cascade = CascadeType.REFRESH)
    public SubscriptionModel subscription;

    protected ApplicationModel() {
    }

    public ApplicationModel(long code, String name, long monthlyPrice) {
        this.code = code;
        this.name = name;
        this.monthlyPrice = monthlyPrice;
    }
    
    public ApplicationModel(ApplicationEntity applicationEntity) {
        this.code = applicationEntity.code;
        this.name = applicationEntity.name;
        this.monthlyPrice = applicationEntity.monthlyPrice;
    }

    public ApplicationEntity toEntity() {
        return new ApplicationEntity(code, name, monthlyPrice);
    }
}
