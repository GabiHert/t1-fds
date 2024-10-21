package com.fds.siscaa.interfaceAdapters.repository.jpa;

import java.sql.Date;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.fds.siscaa.domain.utils.CustomList;
import com.fds.siscaa.interfaceAdapters.repository.model.SubscriptionModel;

public interface SubscriptionRepositoryJPA extends JpaRepository<SubscriptionModel, Long> {
    CustomList<SubscriptionModel> findAll();

    CustomList<SubscriptionModel> findByEndDate(Date endDate);

    CustomList<SubscriptionModel> findByEndDateAfter(Date currentDate);

    CustomList<SubscriptionModel> findByEndDateBefore(Date currentDate);

    @EntityGraph(attributePaths = { "application", "promotions" })
    CustomList<SubscriptionModel> findByClientCode(long clientCode);

    CustomList<SubscriptionModel> findByApplicationCode(long applicationCode);

    SubscriptionModel findByCode(long code);

    @Modifying()
    @Query("update SubscriptionModel s set s.startDate = startDate, s.endDate = endDate where s.code = subscriptionCode")
    int updateSubscriptionStartDateAndEndDateByCode(Date startDate, Date endDate, long subscriptionCode);

    SubscriptionModel save(SubscriptionModel subscriptionModel);

}
