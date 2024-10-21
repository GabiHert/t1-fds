package com.fds.siscaa.interfaceAdapters.repository.repositoryImpl;

import com.fds.siscaa.interfaceAdapters.repository.jpa.ClientRepositoryJPA;
import com.fds.siscaa.useCases.adapters.ClientRepositoryAdapter;

import lombok.AllArgsConstructor;

import com.fds.siscaa.domain.entity.ApplicationEntity;
import com.fds.siscaa.domain.entity.ClientEntity;
import com.fds.siscaa.domain.entity.SubscriptionEntity;
import com.fds.siscaa.domain.utils.CustomList;

import java.util.List;

import com.fds.siscaa.interfaceAdapters.repository.model.ClientModel;
import com.fds.siscaa.interfaceAdapters.repository.model.*;

@AllArgsConstructor()
public class ClientRepositoryImpl implements ClientRepositoryAdapter {
  private ClientRepositoryJPA clientRepositoryJPA;

  public ClientRepositoryImpl() {

  }

  public CustomList<ClientEntity> listClients() {
    CustomList<ClientModel> cliModels = clientRepositoryJPA.findAll();
    return cliModels.toEntities(ClientEntity.class);
  }

  @Override
  public ClientEntity getClientEntityByCode(long clientCode) {
    ClientModel clientModel = clientRepositoryJPA.findByCode(clientCode);
    return clientModel.toEntity();
  }
}
