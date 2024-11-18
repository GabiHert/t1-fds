package com.fds.siscaa.interfaceAdapters.repository.repositoryImpl;

import com.fds.siscaa.interfaceAdapters.repository.jpa.PromotionRepositoryJPA;
import com.fds.siscaa.useCases.adapters.PromotionRepositoryAdapter;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Repository;

import com.fds.siscaa.domain.entity.PromotionEntity;
import com.fds.siscaa.domain.utils.CustomList;
import com.fds.siscaa.interfaceAdapters.repository.model.PromotionModel;

@Repository
@AllArgsConstructor()
public class PromotionRepositoryImpl implements PromotionRepositoryAdapter {
  PromotionRepositoryJPA promotionRepositoryJPA;

  @Override
  public CustomList<PromotionEntity> listByApplicationCode(long applicationCode) {
    CustomList<PromotionModel> promotionModels = promotionRepositoryJPA.findByApplicationCode(applicationCode);
    return promotionModels.toEntities(PromotionEntity.class);
  }

  @Override
  public PromotionEntity getByCode(long code) {
    PromotionModel promotionModel = promotionRepositoryJPA.findByCode(code);
    return promotionModel.toEntity();
  }

}
