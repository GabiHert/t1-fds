package com.fds.siscaa.domain.entity;

import java.util.Date;

public class SubscriptionEntity {
    public long code;
    public long applicationCode;
    public long clientCode;
    public Date startDate;
    public Date endDate;

    public SubscriptionEntity(long code, Date startDate, Date endDate, long applicationCode) {
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
