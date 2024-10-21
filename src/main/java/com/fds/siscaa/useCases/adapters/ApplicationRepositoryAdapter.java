package com.fds.siscaa.useCases.adapters;

import java.util.List;

import com.fds.siscaa.domain.entity.ApplicationEntity;
import com.fds.siscaa.domain.utils.CustomList;

public interface ApplicationRepositoryAdapter {
    CustomList<ApplicationEntity> listApplications();

    ApplicationEntity getApplicationEntityByCode(long appCode);

    ApplicationEntity UpdateApplicationCostByCode(long appCode, float cost);
}