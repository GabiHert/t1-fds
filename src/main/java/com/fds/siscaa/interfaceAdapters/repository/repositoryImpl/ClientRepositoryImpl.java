package com.fds.siscaa.interfaceAdapters.repository.repositoryImpl;

import com.fds.siscaa.interfaceAdapters.repository.jpa.ClientRepositoryJPA;
import com.fds.siscaa.useCases.adapters.ClientRepositoryAdapter;
import com.fds.siscaa.domain.entity.ClientEntity;
import java.util.List;
import com.fds.siscaa.interfaceAdapters.repository.model.ClientModel;

public class ClientRepositoryImpl implements ClientRepositoryAdapter {
  ClientRepositoryJPA clientRepositoryJPA;
  ClientModelParser clientModelParser;

  public ClientRepositoryImpl(ClientRepositoryJPA clientRepositoryJPA) {
    this.clientRepositoryJPA = clientRepositoryJPA;
    this.clientModelParser = new ClientModelParser();
  }

  public List<ClientEntity> listClients() {
    List<ClientModel> cliModels = clientRepositoryJPA.findAll();
    return clientModelParser.parseClientModelToEntity(cliModels);
  }
}
