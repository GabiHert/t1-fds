package com.fds.siscaa.interfaceAdapters.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fds.siscaa.interfaceAdapters.repository.model.ClientModel;

public interface ClientRepositoryJPA extends JpaRepository<ClientModel, Long> {
    List<ClientModel> findAll();
}
