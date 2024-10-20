package com.fds.siscaa.interfaceAdapters.repository.repositoryImpl;

import com.fds.siscaa.interfaceAdapters.repository.jpa.ApplicationRepositoryJPA;
import com.fds.siscaa.useCases.adapters.ApplicationRepositoryAdapter;
import com.fds.siscaa.domain.entity.*;
import java.util.List;
import com.fds.siscaa.interfaceAdapters.repository.model.*;
import com.fds.siscaa.interfaceAdapters.repository.repositoryImpl.parser.ApplicationModelParser;

public class ApplicationRepositoryImpl implements ApplicationRepositoryAdapter {
  ApplicationRepositoryJPA applicationRepositoryJPA;
  ApplicationModelParser applicationModelParser;

  public ApplicationRepositoryImpl(ApplicationRepositoryJPA applicationRepositoryJPA) {
    this.applicationRepositoryJPA = applicationRepositoryJPA;
    this.applicationModelParser = new ApplicationModelParser();
  }

  public List<ApplicationEntity> listApplications() {
    List<ApplicationModel> appModels = applicationRepositoryJPA.findAll();
    return applicationModelParser.parseApplicationModelToEntity(appModels);

  }

}
