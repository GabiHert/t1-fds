package com.fds.siscaa.interfaceAdapters.repository.jpa;

import org.springframework.data.repository.CrudRepository;

import com.fds.siscaa.domain.utils.CustomList;
import com.fds.siscaa.interfaceAdapters.repository.model.ApplicationModel;

public interface ApplicationRepositoryJPA extends CrudRepository<ApplicationModel, Long> {
    CustomList<ApplicationModel> findAll();

    ApplicationModel findByCode(long code);
}
