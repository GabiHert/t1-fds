package com.fds.siscaa.domain.entity;

import java.util.Date;

public class SubscriptionEntity {
    public long code;
    public long applicationCode;
    public long clientCode;
    public Date startDate;
    public Date endDate;


    public SubscriptionEntity(long code, long applicationCode, long clientCode, Date startDate, Date endDate) {
        this.code = code;
        this.applicationCode = applicationCode;
        this.clientCode = clientCode;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
