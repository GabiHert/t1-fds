package com.fds.siscaa.interfaceAdapters.repository.repositoryImpl;

import com.fds.siscaa.interfaceAdapters.repository.jpa.PaymentRepositoryJPA;
import com.fds.siscaa.useCases.adapters.PaymentRepositoryAdapter;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Repository;

import com.fds.siscaa.domain.entity.PaymentEntity;
import com.fds.siscaa.interfaceAdapters.repository.model.PaymentModel;

@Repository
@AllArgsConstructor()
public class PaymentRepositoryImpl implements PaymentRepositoryAdapter {
  private final PaymentRepositoryJPA paymentRepositoryJPA;

  @Override
  public PaymentEntity create(PaymentEntity paymentEntity) {
    return paymentRepositoryJPA.save(new PaymentModel(paymentEntity)).toEntity();
  }

}
