package com.fds.siscaa.interfaceAdapters.repository.jpa;

import org.springframework.data.repository.CrudRepository;

import com.fds.siscaa.domain.utils.CustomList;
import com.fds.siscaa.interfaceAdapters.repository.model.PromotionModel;

public interface PromotionRepositoryJPA extends CrudRepository<PromotionModel, Long> {
    CustomList<PromotionModel> findByApplicationCode(long applicationCode);

    PromotionModel findByCode(long code);

}
