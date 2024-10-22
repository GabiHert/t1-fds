package com.fds.siscaa.useCases.adapters;

import com.fds.siscaa.domain.entity.PromotionEntity;
import com.fds.siscaa.domain.utils.CustomList;

public interface PromotionRepositoryAdapter {
    CustomList<PromotionEntity> listByApplicationCode(long applicationCode);
}