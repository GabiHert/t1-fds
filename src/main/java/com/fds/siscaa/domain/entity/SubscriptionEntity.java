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
    private long applicationCode;
    private long clientCode;
    private Date startDate;
    private Date endDate;
    private float monthlyFee;

    public SubscriptionEntity() {
    }
}
