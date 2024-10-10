package com.fds.siscaa.domain.entity;

import java.util.Date;

public class SubscriptionEntity {
    public long code;
    public Date startDate;
    public Date endDate;

    public SubscriptionEntity(long code, Date startDate, Date endDate) {
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
