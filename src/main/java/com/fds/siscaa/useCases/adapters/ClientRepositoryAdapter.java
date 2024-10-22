package com.fds.siscaa.useCases.adapters;

import com.fds.siscaa.domain.entity.ClientEntity;
import com.fds.siscaa.domain.utils.CustomList;
import org.springframework.stereotype.Repository;


public interface ClientRepositoryAdapter {
    CustomList<ClientEntity> listClients();

    ClientEntity getClientEntityByCode(long clientCode);
}