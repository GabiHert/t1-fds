package com.fds.siscaa.useCases.adapters;

import java.util.List;
import com.fds.siscaa.domain.entity.ClientEntity;

public interface ClientRepositoryAdapter {
    List<ClientEntity> listClients();
}