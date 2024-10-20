package com.fds.siscaa.interfaceAdapters.repository.repositoryImpl;

import java.time.LocalDate;
import java.util.List;
import java.sql.Date;

import com.fds.siscaa.domain.enums.*;
import com.fds.siscaa.domain.utils.CustomList;
import com.fds.siscaa.interfaceAdapters.repository.jpa.SubscriptionRepositoryJPA;
import com.fds.siscaa.useCases.adapters.SubscriptionRepositoryAdapter;

import lombok.AllArgsConstructor;

import com.fds.siscaa.domain.entity.SubscriptionEntity;
import com.fds.siscaa.interfaceAdapters.repository.model.SubscriptionModel;

@AllArgsConstructor()
public class SubscriptionRepositoryImpl implements SubscriptionRepositoryAdapter {
  private SubscriptionRepositoryJPA subscriptionRepositoryJPA;
  Date sqlDate = Date.valueOf(LocalDate.now());

  public SubscriptionRepositoryImpl() {
  }

  public CustomList<SubscriptionEntity> listSubscriptions(long applicationCode) {
    CustomList<SubscriptionModel> subscriptionModels = subscriptionRepositoryJPA.findByApplicationCode(applicationCode);
    return subscriptionModels.toEntities(SubscriptionEntity.class);
  }

  public CustomList<SubscriptionEntity> listSubscriptions() {
    CustomList<SubscriptionModel> subscriptionModels = subscriptionRepositoryJPA.findAll();
    return subscriptionModels.toEntities(SubscriptionEntity.class);
  }

  public CustomList<SubscriptionEntity> listSubscriptionsByEndDate(long applicationCode, Date endDate) {
    CustomList<SubscriptionModel> subscriptionModels = subscriptionRepositoryJPA.findByEndDate(endDate);
    return subscriptionModels.toEntities(SubscriptionEntity.class);
  }

  public CustomList<SubscriptionEntity> listSubscriptionsByClientCode(long clientCode) {
    CustomList<SubscriptionModel> subscriptionModels = subscriptionRepositoryJPA.findByClientCode(clientCode);
    return subscriptionModels.toEntities(SubscriptionEntity.class);
  }

  public CustomList<SubscriptionEntity> listSubscriptionEntityByApplicationCode(long applicationCode) {
    CustomList<SubscriptionModel> subscriptionModels = subscriptionRepositoryJPA.findByApplicationCode(applicationCode);
    return subscriptionModels.toEntities(SubscriptionEntity.class);
  }

  public CustomList<SubscriptionEntity> listSubscriptionsByType(SubscriptionType subscriptionType) {
    LocalDate currentDate = LocalDate.now();
    switch (subscriptionType) {
      case ATIVAS:
        CustomList<SubscriptionModel> subscriptionModels_ativas = subscriptionRepositoryJPA.findByEndDateAfter(sqlDate);
        return subscriptionModels_ativas.toEntities(SubscriptionEntity.class);
      case CANCELADAS:
        CustomList<SubscriptionModel> subscriptionModels_canceladas = subscriptionRepositoryJPA
            .findByEndDateBefore(sqlDate);
        return subscriptionModels_canceladas.toEntities(SubscriptionEntity.class);
      case TODAS:
      default:
        CustomList<SubscriptionModel> subscriptionModels_todas = subscriptionRepositoryJPA.findAll();
        return subscriptionModels_todas.toEntities(SubscriptionEntity.class);
    }
  }

  public SubscriptionEntity updateSubscriptionStartDateAndEndDateByCode(Date startDate, Date endDate,
      long subscriptionCode) {
    SubscriptionEntity subscriptionEntity = getSubscriptionEntityByCode(subscriptionCode);
    subscriptionEntity.setStartDate(startDate);
    subscriptionEntity.setEndDate(endDate);

    return subscriptionEntity;
  }

  public SubscriptionEntity getSubscriptionEntityByCode(long subscriptionCode) {
    SubscriptionModel subscriptionModel = subscriptionRepositoryJPA.findByCode(subscriptionCode);
    return subscriptionModel.toEntity();
  }
}
