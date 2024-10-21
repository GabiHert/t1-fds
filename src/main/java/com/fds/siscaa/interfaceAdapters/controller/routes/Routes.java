package com.fds.siscaa.interfaceAdapters.controller.routes;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fds.siscaa.domain.entity.ClientEntity;
import com.fds.siscaa.domain.entity.ApplicationEntity;
import com.fds.siscaa.domain.entity.SubscriptionEntity;
import com.fds.siscaa.interfaceAdapters.controller.dto.ClientDto;
import com.fds.siscaa.interfaceAdapters.controller.dto.CreatePaymentDto;
import com.fds.siscaa.interfaceAdapters.controller.dto.CreatePaymentResponseDto;
import com.fds.siscaa.interfaceAdapters.controller.dto.ApplicationDto;
import com.fds.siscaa.interfaceAdapters.controller.dto.CreateSubscriptionDto;
import com.fds.siscaa.interfaceAdapters.controller.dto.SubscriptionDto;
import com.fds.siscaa.useCases.adapters.ClientRepositoryAdapter;
import com.fds.siscaa.useCases.useCases.CreatePaymentResponse;
import com.fds.siscaa.useCases.useCases.CreatePaymentUseCase;
import com.fds.siscaa.useCases.useCases.CreateSubscriptionUseCase;
import com.fds.siscaa.useCases.adapters.ApplicationRepositoryAdapter;
import com.fds.siscaa.useCases.adapters.SubscriptionRepositoryAdapter;

import com.fds.siscaa.domain.enums.SubscriptionType;
import com.fds.siscaa.domain.utils.CustomList;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("")
public class Routes {
    ClientRepositoryAdapter clientRepository;
    ApplicationRepositoryAdapter applicationRepository;
    CreateSubscriptionUseCase createSubscriptionUseCase;
    CreatePaymentUseCase createPaymentUseCase;
    SubscriptionRepositoryAdapter subscriptionRepository;

    public Routes() {
    }

    @PostMapping("servcad/assinaturas")
    public SubscriptionDto postSubscription(@RequestBody CreateSubscriptionDto createSubscriptionDto) {
        System.out.println("postSubscription - STARTED - createSubscriptionDto: " + createSubscriptionDto.toString());

        SubscriptionEntity subscriptionEntity = this.createSubscriptionUseCase
                .create(createSubscriptionDto.getClientCode(), createSubscriptionDto.getApplicationCode());

        SubscriptionDto subscriptionDto = new SubscriptionDto(subscriptionEntity);
        System.out.println("postSubscription - FINISHED - subscriptionDto: " + subscriptionDto.toString());
        return subscriptionDto;
    }

    @PostMapping("registrarpagamento")
    public CreatePaymentResponseDto postPayment(@RequestBody CreatePaymentDto createPaymentDto) {
        System.out.println("postPayment - STARTED - createPaymentDto" + createPaymentDto.toString());

        // todo: validação de payload

        LocalDate paymentDate = LocalDate.of(
                createPaymentDto.getYear(),
                createPaymentDto.getMonth(),
                createPaymentDto.getDay());

        CreatePaymentResponse createPaymentResponse = this.createPaymentUseCase.create(
                paymentDate,
                createPaymentDto.getCodass(),
                createPaymentDto.getValorPago());

        CreatePaymentResponseDto createPaymentResponseDto = new CreatePaymentResponseDto(createPaymentResponse);
        System.out.println("postPayment - FINISHED - createPaymentResponseDto" + createPaymentResponseDto.toString());
        return createPaymentResponseDto;
    }

    @PostMapping("servcad/aplicativos/atualizacusto/{idAplicativo}")
    public ApplicationEntity updateCost(@RequestBody float cost, @PathVariable long id) {

        ApplicationEntity applicationEntity = this.applicationRepository.getApplicationEntityByCode(id);

        applicationEntity.setMonthlyFee(cost);

        return applicationEntity;
    }

    @GetMapping("servcad/clientes")
    public List<ClientDto> listClients() {
        CustomList<ClientEntity> clientEntities = new CustomList<>(this.clientRepository.listClients());
        return clientEntities.toDtos(ClientDto.class);

    };

    @GetMapping("servcad/aplicativos")
    public List<ApplicationDto> listApplication() {
        CustomList<ApplicationEntity> applicationEntities = new CustomList<>(
                this.applicationRepository.listApplications());
        return applicationEntities.toDtos(ApplicationDto.class);
    }

    @GetMapping("servcad/assinaturas/{tipo}")
    public List<SubscriptionDto> ListSubscriptionsByType(@PathVariable SubscriptionType type) {
        CustomList<SubscriptionEntity> subscriptionEntities = new CustomList<>(
                this.subscriptionRepository.listSubscriptionByType(type));
        return subscriptionEntities.toDtos(SubscriptionDto.class);
    }

    @GetMapping("servcad/asscli/{codcli}")
    public List<SubscriptionDto> ListSubscriptionsByClientCode(@PathVariable Long codcli) {
        CustomList<SubscriptionEntity> subscriptionEntities = new CustomList<>(
                this.subscriptionRepository.listSubscriptionsByClientCode(codcli));
        return subscriptionEntities.toDtos(SubscriptionDto.class);
    }

    @GetMapping("servcad/assapp/{codapp}")
    public List<SubscriptionDto> ListSubscriptionsByAppCode(@PathVariable Long codapp) {
        CustomList<SubscriptionEntity> subscriptionEntities = new CustomList<>(
                this.subscriptionRepository.listSubscriptionEntityByApplicationCode(codapp));
        return subscriptionEntities.toDtos(SubscriptionDto.class);
    }

    @GetMapping("assinvalida/{codass}")
    public boolean SubscriptionIsValid(@PathVariable Long codsub) {
        SubscriptionEntity sub = this.subscriptionRepository.getSubscriptionEntityByCode(codsub);
        return sub != null;
    }

}