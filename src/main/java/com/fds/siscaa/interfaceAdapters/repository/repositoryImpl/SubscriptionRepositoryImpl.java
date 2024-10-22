package com.fds.siscaa.interfaceAdapters.repository.repositoryImpl;

import java.time.LocalDate;

import com.fds.siscaa.domain.enums.*;
import com.fds.siscaa.domain.utils.CustomList;
import com.fds.siscaa.interfaceAdapters.repository.jpa.SubscriptionRepositoryJPA;
import com.fds.siscaa.useCases.adapters.SubscriptionRepositoryAdapter;

import lombok.AllArgsConstructor;

import com.fds.siscaa.domain.entity.SubscriptionEntity;
import com.fds.siscaa.interfaceAdapters.repository.model.SubscriptionModel;
import org.springframework.stereotype.Repository;

@AllArgsConstructor()
@Repository
public class SubscriptionRepositoryImpl implements SubscriptionRepositoryAdapter {
  private SubscriptionRepositoryJPA subscriptionRepositoryJPA;

  public CustomList<SubscriptionEntity> listSubscriptions(long applicationCode) {
    CustomList<SubscriptionModel> subscriptionModels = subscriptionRepositoryJPA.findByApplicationCode(applicationCode);
    return subscriptionModels.toEntities(SubscriptionEntity.class);
  }

  public CustomList<SubscriptionEntity> listSubscriptions() {
    CustomList<SubscriptionModel> subscriptionModels = subscriptionRepositoryJPA.findAll();
    return subscriptionModels.toEntities(SubscriptionEntity.class);
  }

  public CustomList<SubscriptionEntity> listSubscriptionsByEndDate(long applicationCode, LocalDate endDate) {
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

  @Override
  public CustomList<SubscriptionEntity> listSubscriptionByType(SubscriptionType subscriptionType) {
    switch (subscriptionType) {
      case ATIVAS:
        CustomList<SubscriptionModel> subscriptionModels_ativas = subscriptionRepositoryJPA
            .findByEndDateAfter(LocalDate.now());
        return subscriptionModels_ativas.toEntities(SubscriptionEntity.class);
      case CANCELADAS:
        CustomList<SubscriptionModel> subscriptionModels_canceladas = subscriptionRepositoryJPA
            .findByEndDateBefore(LocalDate.now());
        return subscriptionModels_canceladas.toEntities(SubscriptionEntity.class);
      case TODAS:
      default:
        CustomList<SubscriptionModel> subscriptionModels_todas = subscriptionRepositoryJPA.findAll();
        return subscriptionModels_todas.toEntities(SubscriptionEntity.class);
    }
  }

  public int updateSubscriptionStartDateAndEndDateByCode(LocalDate startDate, LocalDate endDate,
      long subscriptionCode) {
    return this.subscriptionRepositoryJPA
        .updateSubscriptionStartDateAndEndDateByCode(startDate, endDate, subscriptionCode);
  }

  public SubscriptionEntity getSubscriptionEntityByCode(long subscriptionCode) {
    SubscriptionModel subscriptionModel = subscriptionRepositoryJPA.findByCode(subscriptionCode);
    return subscriptionModel.toEntity();
  }

  @Override
  public SubscriptionEntity create(SubscriptionEntity subscriptionEntity) {
    return this.subscriptionRepositoryJPA.save(new SubscriptionModel(subscriptionEntity)).toEntity();
  }

}
