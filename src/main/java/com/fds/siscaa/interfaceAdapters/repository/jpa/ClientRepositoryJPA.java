package com.fds.siscaa.interfaceAdapters.repository.jpa;

import com.fds.siscaa.domain.utils.CustomList;
import com.fds.siscaa.interfaceAdapters.repository.model.ClientModel;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepositoryJPA extends CrudRepository<ClientModel, Long> {
    CustomList<ClientModel> findAll();

    ClientModel findByCode(long clientCode);
}
