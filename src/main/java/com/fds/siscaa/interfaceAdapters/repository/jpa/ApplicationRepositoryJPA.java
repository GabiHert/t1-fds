package com.fds.siscaa.interfaceAdapters.repository.jpa;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.fds.siscaa.domain.utils.CustomList;
import com.fds.siscaa.interfaceAdapters.repository.model.ApplicationModel;

import jakarta.transaction.Transactional;

public interface ApplicationRepositoryJPA extends CrudRepository<ApplicationModel, Long> {
    CustomList<ApplicationModel> findAll();

    ApplicationModel findByCode(long code);

    @Modifying
    @Transactional
    @Query("update ApplicationModel s set s.monthlyFee = :cost where s.code = :code")
    int updateApplicationCostByCode(@Param("code") long code, @Param("cost") float cost);
}
