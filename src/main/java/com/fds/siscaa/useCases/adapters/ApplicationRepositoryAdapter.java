package com.fds.siscaa.useCases.adapters;

import java.util.List;

import com.fds.siscaa.domain.entity.ApplicationEntity;

public interface ApplicationRepositoryAdapter {
    List<ApplicationEntity> listApplications();
}