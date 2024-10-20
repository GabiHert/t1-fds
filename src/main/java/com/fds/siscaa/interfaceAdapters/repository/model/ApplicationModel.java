package com.fds.siscaa.interfaceAdapters.repository.model;

import com.fds.siscaa.domain.entity.ApplicationEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter()
@Setter()
@AllArgsConstructor()
public class ApplicationModel {
    @Id
    private long code;
    private String name;
    private float monthlyFee;

    @ManyToOne(cascade = CascadeType.REFRESH)
    public SubscriptionModel subscription;

    protected ApplicationModel() {
    }

    public ApplicationModel(ApplicationEntity applicationEntity) {
        this.code = applicationEntity.getCode();
        this.name = applicationEntity.getName();
        this.monthlyFee = applicationEntity.getMonthlyFee();
    }

    public ApplicationEntity toEntity() {
        return new ApplicationEntity(code, name, monthlyFee);
    }
}
