package com.fds.siscaa.useCases.adapters;

import java.util.List;
import com.fds.siscaa.domain.entity.ClientEntity;
import com.fds.siscaa.domain.utils.CustomList;

public interface ClientRepositoryAdapter {
    CustomList<ClientEntity> listClients();

    ClientEntity getClientEntityByCode(long clientCode);
}