package com.fds.siscaa.interfaceAdapters.controller.dto;

import java.time.LocalDate;

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
        private LocalDate startDate;
        private LocalDate endDate;
        private ApplicationEntity application;

        public SubscriptionDto() {
        }

        public SubscriptionDto(SubscriptionEntity subscriptionEntity) {
                this.code = subscriptionEntity.getCode();
                this.clientEntity = subscriptionEntity.getClient();
                this.startDate = subscriptionEntity.getStartDate();
                this.endDate = subscriptionEntity.getEndDate();
                this.application = subscriptionEntity.getApplication();
        }

}
