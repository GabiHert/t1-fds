package com.fds.siscaa.interfaceAdapters.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import com.fds.siscaa.interfaceAdapters.repository.model.ApplicationModel;
import com.fds.siscaa.interfaceAdapters.repository.model.PaymentModel;

public interface PaymentRepositoryJPA extends CrudRepository<ApplicationModel, Long> {
    PaymentModel save(PaymentModel paymentModel);

}
