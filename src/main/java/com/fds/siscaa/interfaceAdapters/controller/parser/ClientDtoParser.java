package com.fds.siscaa.interfaceAdapters.controller.parser;

import java.util.List;
import java.util.stream.Collectors;

import com.fds.siscaa.domain.entity.ClientEntity;
import com.fds.siscaa.interfaceAdapters.controller.dto.ClientDto;

public class ClientDtoParser {
  public List<ClientDto> parseClientEntitiesToDto(List<ClientEntity> clientEntities) {
    return clientEntities.stream().map(cli -> new ClientDto(cli))
        .collect(Collectors.toList());
  }
}
