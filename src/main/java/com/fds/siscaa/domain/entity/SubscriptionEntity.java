package com.fds.siscaa.domain.entity;

import java.time.LocalDate;

import com.fds.siscaa.domain.utils.CustomLocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
@AllArgsConstructor()
public class SubscriptionEntity {

    private long code;
    private ClientEntity client;
    private LocalDate startDate;
    private LocalDate endDate;
    private ApplicationEntity application;
    private String status;

    public SubscriptionEntity(long clientCode, long applicationCode, LocalDate startDate, LocalDate endDate) {
        this.client = new ClientEntity(clientCode);
        this.application = new ApplicationEntity(applicationCode);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public SubscriptionEntity(long code, ClientEntity client, LocalDate startDate, LocalDate endDate,
            ApplicationEntity application) {
        this.code = code;
        this.client = client;
        this.startDate = startDate;
        this.endDate = endDate;
        this.application = application;
        if (endDate.isBefore(CustomLocalDate.now())) {
            status = "CANCELADA";
        } else {
            status = "ATIVA";
        }
    }

}
