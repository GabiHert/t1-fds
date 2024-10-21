package com.fds.siscaa.interfaceAdapters.repository.jpa;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.fds.siscaa.domain.utils.CustomList;
import com.fds.siscaa.interfaceAdapters.repository.model.ApplicationModel;

public interface ApplicationRepositoryJPA extends CrudRepository<ApplicationModel, Long> {
    CustomList<ApplicationModel> findAll();

    ApplicationModel findByCode(long code);

    @Modifying()
    @Query("update ApplicationModel s set s.monthlyFee = cost where s.code = code")
    int updateApplicationCostByCode(long code, float cost);
}
