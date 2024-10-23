package com.fds.siscaa.interfaceAdapters.controller.dto;

import java.time.LocalDate;

import com.fds.siscaa.domain.entity.SubscriptionEntity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter()
@Setter()
@AllArgsConstructor()
public class SubscriptionDto {

        private long codigoAssinatura;
        private long codigoCliente;
        private long codigoAplicativo;
        private LocalDate dataDeInicio;
        private LocalDate dataDeEncerramento;
        private String status;

        public SubscriptionDto() {
        }

        public SubscriptionDto(SubscriptionEntity subscriptionEntity) {
                this.codigoAssinatura = subscriptionEntity.getCode();
                this.codigoCliente = subscriptionEntity.getClient().getCode();
                this.dataDeInicio = subscriptionEntity.getStartDate();
                this.dataDeEncerramento = subscriptionEntity.getEndDate();
                this.codigoAplicativo = subscriptionEntity.getApplication().getCode();
                this.status = subscriptionEntity.getStatus();
        }

}
