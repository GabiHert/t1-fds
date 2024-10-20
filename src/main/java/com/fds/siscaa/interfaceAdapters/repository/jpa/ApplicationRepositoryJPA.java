package com.fds.siscaa.interfaceAdapters.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fds.siscaa.domain.utils.CustomList;
import com.fds.siscaa.interfaceAdapters.repository.model.ApplicationModel;

public interface ApplicationRepositoryJPA extends JpaRepository<ApplicationModel, Long> {
    CustomList<ApplicationModel> findAll();
}
