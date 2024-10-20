package com.fds.siscaa.interfaceAdapters.repository.jpa;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.fds.siscaa.interfaceAdapters.repository.model.SubscriptionModel;

public interface SubscriptionRepositoryJPA extends JpaRepository<SubscriptionModel, Long> {
    List<SubscriptionModel> findAll();

    List<SubscriptionModel> findByEndDate(Date endDate);

    List<SubscriptionModel> findByEndDateAfter(Date currentDate);

    List<SubscriptionModel> findByEndDateBefore(Date currentDate);

    @EntityGraph(attributePaths = { "application", "promotions" })
    List<SubscriptionModel> findByClientCode(long clientCode);

    List<SubscriptionModel> findByApplicationCode(long applicationCode);

    SubscriptionModel findByCode(long code);

    @Modifying()
    @Query("update SubscriptionModel s set s.startDate = startDate, s.endDate = endDate where s.code = subscriptionCode")
    int updateSubscriptionStartDateAndEndDateByCode(Date startDate, Date endDate, long subscriptionCode);

}
