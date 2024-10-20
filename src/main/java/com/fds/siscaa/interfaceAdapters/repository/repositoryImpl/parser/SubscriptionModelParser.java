package com.fds.siscaa.interfaceAdapters.repository.repositoryImpl.parser;

import java.util.List;
import java.util.stream.Collectors;

import com.fds.siscaa.domain.entity.SubscriptionEntity;
import com.fds.siscaa.interfaceAdapters.repository.model.SubscriptionModel;

public class SubscriptionModelParser {
  public List<SubscriptionEntity> parseSubscriptionModelToEntity(List<SubscriptionModel> subscriptionModels) {
    return subscriptionModels.stream().map(subscriptionModel -> subscriptionModel.toEntity())
        .collect(Collectors.toList());
  }
}