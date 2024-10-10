package com.fds.siscaa.interfaceAdapters.repositories.model;

import java.util.Date;


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

    protected SubscriptionModel() {
    }

    public SubscriptionModel(long code, Date startDate, Date endDate) {
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
