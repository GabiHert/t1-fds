package com.fds.siscaa.interfaceAdapters.repository.jpa;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.fds.siscaa.domain.utils.CustomList;
import com.fds.siscaa.interfaceAdapters.repository.model.SubscriptionModel;

public interface SubscriptionRepositoryJPA extends CrudRepository<SubscriptionModel, Long> {
    CustomList<SubscriptionModel> findByApplicationCode(long applicationCode);

    CustomList<SubscriptionModel> findAll();

    CustomList<SubscriptionModel> findByEndDate(LocalDate endDate);

    CustomList<SubscriptionModel> findByClientCode(long clientCode);

    CustomList<SubscriptionModel> findByEndDateBefore(LocalDate endDate);

    CustomList<SubscriptionModel> findByEndDateAfter(LocalDate endDate);

    SubscriptionModel findByCode(long code);

    @Modifying()
    @Query("update SubscriptionModel s set s.startDate = startDate, s.endDate = endDate where s.code = subscriptionCode")
    int updateSubscriptionStartDateAndEndDateByCode(LocalDate startDate, LocalDate endDate, long subscriptionCode);

    SubscriptionModel save(SubscriptionModel subscriptionModel);

}
