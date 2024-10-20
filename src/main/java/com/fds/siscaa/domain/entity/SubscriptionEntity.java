package com.fds.siscaa.domain.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
@AllArgsConstructor()
public class SubscriptionEntity {
    private long code;
    private ClientEntity clientEntity;
    private Date startDate;
    private Date endDate;
    private ApplicationEntity application;

    public SubscriptionEntity() {
    }
}
