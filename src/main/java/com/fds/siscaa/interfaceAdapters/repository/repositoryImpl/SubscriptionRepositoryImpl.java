package com.fds.siscaa.interfaceAdapters.repository.repositoryImpl;

import java.time.LocalDate;
import java.util.List;
import java.sql.Date;

import com.fds.siscaa.domain.enums.*;
import com.fds.siscaa.interfaceAdapters.repository.jpa.SubscriptionRepositoryJPA;
import com.fds.siscaa.useCases.adapters.SubscriptionRepositoryAdapter;
import com.fds.siscaa.domain.entity.SubscriptionEntity;
import com.fds.siscaa.interfaceAdapters.repository.model.SubscriptionModel;
import com.fds.siscaa.interfaceAdapters.repository.repositoryImpl.parser.SubscriptionModelParser;

public class SubscriptionRepositoryImpl implements SubscriptionRepositoryAdapter {
  private final SubscriptionRepositoryJPA subscriptionRepositoryJPA;
  private final SubscriptionModelParser subscriptionModelParser;
  Date sqlDate = Date.valueOf(LocalDate.now());

  public SubscriptionRepositoryImpl(SubscriptionRepositoryJPA subscriptionRepositoryJPA) {
    this.subscriptionRepositoryJPA = subscriptionRepositoryJPA;
    this.subscriptionModelParser = new SubscriptionModelParser();
  }

  @Override
  public List<SubscriptionEntity> listSubscriptions(long applicationCode) {
    List<SubscriptionModel> subscriptionModels = subscriptionRepositoryJPA.findByApplicationCode(applicationCode);
    return subscriptionModelParser.parseSubscriptionModelToEntity(subscriptionModels);
  }

  @Override
  public List<SubscriptionEntity> listSubscriptions() {
    List<SubscriptionModel> subscriptionModels = subscriptionRepositoryJPA.findAll();
    return subscriptionModelParser.parseSubscriptionModelToEntity(subscriptionModels);
  }

  @Override
  public List<SubscriptionEntity> listSubscriptionsByEndDate(long applicationCode, Date endDate) {
    List<SubscriptionModel> subscriptionModels = subscriptionRepositoryJPA.findByEndDate(endDate);
    return subscriptionModelParser.parseSubscriptionModelToEntity(subscriptionModels);
  }

  @Override
  public List<SubscriptionEntity> listSubscriptionsByClientCode(long clientCode) {
    List<SubscriptionModel> subscriptionModels = subscriptionRepositoryJPA.findByClientCode(clientCode);
    return subscriptionModelParser.parseSubscriptionModelToEntity(subscriptionModels);
  }

  @Override
  public List<SubscriptionEntity> listSubscriptionEntityByApplicationCode(long applicationCode) {
    List<SubscriptionModel> subscriptionModels = subscriptionRepositoryJPA.findByApplicationCode(applicationCode);
    return subscriptionModelParser.parseSubscriptionModelToEntity(subscriptionModels);
  }

  public List<SubscriptionEntity> listSubscriptionsByType(SubscriptionType subscriptionType) {
    LocalDate currentDate = LocalDate.now();
    switch (subscriptionType) {
      case ATIVAS:
        List<SubscriptionModel> subscriptionModel_ativas = subscriptionRepositoryJPA.findByEndDateAfter(sqlDate);
        return subscriptionModelParser.parseSubscriptionModelToEntity(subscriptionModel_ativas);
      case CANCELADAS:
        List<SubscriptionModel> subscriptionModels_canceladas = subscriptionRepositoryJPA.findByEndDateBefore(sqlDate);
        return subscriptionModelParser.parseSubscriptionModelToEntity(subscriptionModels_canceladas);
      case TODAS:
      default:
        List<SubscriptionModel> subscriptionModel_todas = subscriptionRepositoryJPA.findAll();
        return subscriptionModelParser.parseSubscriptionModelToEntity(subscriptionModel_todas);
    }
  }

  @Override
  public SubscriptionEntity getSubscriptionEntityByCode(long subscriptionCode) {
    SubscriptionModel subscriptionModel = subscriptionRepositoryJPA.findByCode(subscriptionCode);
    return subscriptionModel.toEntity();
  }
}
