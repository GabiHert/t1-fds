package com.fds.siscaa.useCases.useCases;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.fds.siscaa.domain.entity.PromotionEntity;
import com.fds.siscaa.domain.entity.SubscriptionEntity;
import com.fds.siscaa.domain.utils.CustomList;
import com.fds.siscaa.useCases.adapters.ApplicationRepositoryAdapter;
import com.fds.siscaa.useCases.adapters.ClientRepositoryAdapter;

import lombok.AllArgsConstructor;

import com.fds.siscaa.domain.entity.ApplicationEntity;
import com.fds.siscaa.domain.entity.ClientEntity;

/*
 * Sempre que uma assinatura for cadastrada, o cliente ganha 7 dias grátis. 
A extensão do período de validade da assinatura se dá mediante o pagamento da mensalidade dentro desse período.
*/

@AllArgsConstructor()
public class CreateSubscriptionUseCase {

    private final ClientRepositoryAdapter clientRepository;
    private final ApplicationRepositoryAdapter applicationRepository;

    public SubscriptionEntity create(long clientCode, long applicationCode) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(7);

        ClientEntity client = clientRepository.getClientEntityByCode(clientCode);
        ApplicationEntity app = applicationRepository.getApplicationEntityByCode(applicationCode);

        Date startDateConverted = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDateConverted = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        CustomList<PromotionEntity> promotionList = new CustomList<>();

        return new SubscriptionEntity(client, startDateConverted, endDateConverted, app, promotionList);

    }

}

/*
 * package com.fds.siscaa.domain.entity;
 * 
 * import java.util.Date;
 * 
 * import com.fds.siscaa.domain.utils.CustomList;
 * 
 * import lombok.AllArgsConstructor;
 * import lombok.Getter;
 * import lombok.Setter;
 * 
 * @Getter()
 * 
 * @Setter()
 * 
 * @AllArgsConstructor()
 * public class SubscriptionEntity {
 * private long code;
 * private ClientEntity clientEntity;
 * private Date startDate;
 * private Date endDate;
 * private ApplicationEntity application;
 * private CustomList<PromotionEntity> promotions;
 * 
 * public SubscriptionEntity() {
 * }
 * }
 * 
 */
