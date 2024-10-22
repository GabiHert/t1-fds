package com.fds.siscaa.interfaceAdapters.repository.model;

import com.fds.siscaa.domain.entity.ApplicationEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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

    @ManyToOne(cascade = CascadeType.REFRESH)
    private SubscriptionModel subscription;

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
