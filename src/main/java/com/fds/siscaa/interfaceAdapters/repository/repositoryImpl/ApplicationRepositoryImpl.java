package com.fds.siscaa.interfaceAdapters.repository.repositoryImpl;

import com.fds.siscaa.interfaceAdapters.repository.jpa.ApplicationRepositoryJPA;
import com.fds.siscaa.useCases.adapters.ApplicationRepositoryAdapter;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Repository;

import com.fds.siscaa.domain.entity.*;
import com.fds.siscaa.domain.exception.CustomException;
import com.fds.siscaa.domain.utils.CustomList;

import com.fds.siscaa.interfaceAdapters.repository.model.*;

@Repository
@AllArgsConstructor()
public class ApplicationRepositoryImpl implements ApplicationRepositoryAdapter {
  private ApplicationRepositoryJPA applicationRepositoryJPA;

  public CustomList<ApplicationEntity> listApplications() {
    CustomList<ApplicationModel> appModels = applicationRepositoryJPA.findAll();
    return appModels.toEntities(ApplicationEntity.class);
  }

  @Override
  public ApplicationEntity getApplicationEntityByCode(long appCode) {
    ApplicationModel applicationModel = applicationRepositoryJPA.findByCode(appCode);
    if (applicationModel == null) {
      throw CustomException.NotFound("Application with code " + appCode + " not found");
    }
    return applicationModel.toEntity();
  }

  public ApplicationEntity UpdateApplicationCostByCode(long appCode, float cost) {
    applicationRepositoryJPA.updateApplicationCostByCode(appCode, cost);

    return getApplicationEntityByCode(appCode);
  }

}
