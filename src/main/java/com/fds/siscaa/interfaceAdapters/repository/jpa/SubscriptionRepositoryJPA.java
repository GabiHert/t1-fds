package com.fds.siscaa.interfaceAdapters.repository.jpa;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.fds.siscaa.domain.utils.CustomList;
import com.fds.siscaa.interfaceAdapters.repository.model.SubscriptionModel;

import jakarta.transaction.Transactional;

public interface SubscriptionRepositoryJPA extends CrudRepository<SubscriptionModel, Long> {
    CustomList<SubscriptionModel> findByApplicationCode(long applicationCode);

    CustomList<SubscriptionModel> findAll();

    CustomList<SubscriptionModel> findByEndDate(LocalDate endDate);

    CustomList<SubscriptionModel> findByClientCode(long clientCode);



    @Query("SELECT s FROM SubscriptionModel s WHERE s.endDate < :endDate")
    CustomList<SubscriptionModel> findByEndDateBefore(@Param("endDate") LocalDate endDate);

    @Query("SELECT s FROM SubscriptionModel s WHERE s.endDate > :endDate")
    CustomList<SubscriptionModel> findByEndDateAfter(@Param("endDate") LocalDate endDate);

    @Query("SELECT s FROM SubscriptionModel s WHERE s.endDate <= :endDate")
    CustomList<SubscriptionModel> findByEndDateBeforeOrEqual(@Param("endDate") LocalDate endDate);

    SubscriptionModel findByCode(long code);

    @Modifying
    @Transactional
    @Query("update SubscriptionModel s set s.startDate = :startDate, s.endDate = :endDate where s.code = :subscriptionCode")
    int updateSubscriptionStartDateAndEndDateByCode(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("subscriptionCode") long subscriptionCode);

    SubscriptionModel save(SubscriptionModel subscriptionModel);

}
