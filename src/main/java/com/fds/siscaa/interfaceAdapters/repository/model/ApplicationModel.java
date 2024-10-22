package com.fds.siscaa.interfaceAdapters.repository.model;

import com.fds.siscaa.domain.entity.ApplicationEntity;

import com.fds.siscaa.domain.entity.PromotionEntity;
import com.fds.siscaa.domain.utils.CustomList;
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

    @Column(name = "name")
    private String name;

    @Column(name = "monthlyFee")
    private float monthlyFee;

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
