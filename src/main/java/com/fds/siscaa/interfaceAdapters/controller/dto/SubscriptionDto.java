package com.fds.siscaa.interfaceAdapters.controller.dto;

import java.util.Date;

import com.fds.siscaa.domain.entity.ApplicationEntity;
import com.fds.siscaa.domain.entity.ClientEntity;
import com.fds.siscaa.domain.entity.SubscriptionEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
@AllArgsConstructor()
public class SubscriptionDto {

        private long code;
    private ClientEntity clientEntity;
    private Date startDate;
    private Date endDate;
    private ApplicationEntity application;

    public SubscriptionDto(){}

        public SubscriptionDto(SubscriptionEntity subscriptionEntity){
                this.code = clientEntity.getCode();
                this.clientEntity = subscriptionEntity.getClientEntity();
                this.startDate = subscriptionEntity.getStartDate();
                this.endDate = subscriptionEntity.getEndDate();
                this.application = subscriptionEntity.getApplication();
        }
        
}   
