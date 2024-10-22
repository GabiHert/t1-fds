package com.fds.siscaa.interfaceAdapters.controller.routes;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fds.siscaa.domain.entity.ClientEntity;
import com.fds.siscaa.domain.entity.CreatePaymentResponseEntity;
import com.fds.siscaa.domain.entity.ApplicationEntity;
import com.fds.siscaa.domain.entity.SubscriptionEntity;
import com.fds.siscaa.interfaceAdapters.controller.dto.ClientDto;
import com.fds.siscaa.interfaceAdapters.controller.dto.CreatePaymentDto;
import com.fds.siscaa.interfaceAdapters.controller.dto.CreatePaymentResponseDto;
import com.fds.siscaa.interfaceAdapters.controller.dto.ApplicationDto;
import com.fds.siscaa.interfaceAdapters.controller.dto.CreateSubscriptionDto;
import com.fds.siscaa.interfaceAdapters.controller.dto.SubscriptionDto;
import com.fds.siscaa.useCases.adapters.ClientRepositoryAdapter;
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
    public ResponseEntity<SubscriptionDto> postSubscription(@RequestBody CreateSubscriptionDto createSubscriptionDto) {
        System.out.println("postSubscription - STARTED - createSubscriptionDto: " + createSubscriptionDto.toString());

        SubscriptionEntity subscriptionEntity = this.createSubscriptionUseCase
                .create(createSubscriptionDto.getClientCode(), createSubscriptionDto.getApplicationCode());

        SubscriptionDto subscriptionDto = new SubscriptionDto(subscriptionEntity);
        System.out.println("postSubscription - FINISHED - subscriptionDto: " + subscriptionDto.toString());
        return ResponseEntity.status(201).body(subscriptionDto);
    }

    @PostMapping("registrarpagamento")
    public ResponseEntity<CreatePaymentResponseDto> postPayment(@RequestBody CreatePaymentDto createPaymentDto) {
        System.out.println("postPayment - STARTED - createPaymentDto" + createPaymentDto.toString());

        // todo: validação de payload

        LocalDate paymentDate = LocalDate.of(
                createPaymentDto.getYear(),
                createPaymentDto.getMonth(),
                createPaymentDto.getDay());

        CreatePaymentResponseEntity createPaymentResponse = this.createPaymentUseCase.create(
                paymentDate,
                createPaymentDto.getCodass(),
                createPaymentDto.getValorPago());

        CreatePaymentResponseDto createPaymentResponseDto = new CreatePaymentResponseDto(createPaymentResponse);
        System.out.println("postPayment - FINISHED - createPaymentResponseDto" + createPaymentResponseDto.toString());
        return ResponseEntity.status(201).body(createPaymentResponseDto);

    }

    @PostMapping("servcad/aplicativos/atualizacusto/{idAplicativo}")
    public ResponseEntity<ApplicationEntity> updateCost(@RequestBody float cost, @PathVariable long id) {
        ApplicationEntity applicationEntity = this.applicationRepository.UpdateApplicationCostByCode(id, cost);
        return ResponseEntity.ok(applicationEntity);
    }

    @GetMapping("servcad/clientes")
    public ResponseEntity<List<ClientDto>> listClients() {
        CustomList<ClientEntity> clientEntities = new CustomList<>(this.clientRepository.listClients());
        return ResponseEntity.ok(clientEntities.toDtos(ClientDto.class));
    }

    @GetMapping("servcad/aplicativos")
    public ResponseEntity<List<ApplicationDto>> listApplication() {
        CustomList<ApplicationEntity> applicationEntities = new CustomList<>(
                this.applicationRepository.listApplications());
        return ResponseEntity.ok(applicationEntities.toDtos(ApplicationDto.class));
    }

    @GetMapping("servcad/assinaturas/{tipo}")
    public ResponseEntity<List<SubscriptionDto>> ListSubscriptionsByType(@PathVariable SubscriptionType type) {
        CustomList<SubscriptionEntity> subscriptionEntities = new CustomList<>(
                this.subscriptionRepository.listSubscriptionByType(type));
        return ResponseEntity.ok(subscriptionEntities.toDtos(SubscriptionDto.class));
    }

    @GetMapping("servcad/asscli/{codcli}")
    public ResponseEntity<List<SubscriptionDto>> ListSubscriptionsByClientCode(@PathVariable Long codcli) {
        CustomList<SubscriptionEntity> subscriptionEntities = new CustomList<>(
                this.subscriptionRepository.listSubscriptionsByClientCode(codcli));
        return ResponseEntity.ok(subscriptionEntities.toDtos(SubscriptionDto.class));
    }

    @GetMapping("servcad/assapp/{codapp}")
    public ResponseEntity<List<SubscriptionDto>> ListSubscriptionsByAppCode(@PathVariable Long codapp) {
        CustomList<SubscriptionEntity> subscriptionEntities = new CustomList<>(
                this.subscriptionRepository.listSubscriptionEntityByApplicationCode(codapp));
        return ResponseEntity.ok(subscriptionEntities.toDtos(SubscriptionDto.class));
    }

    @GetMapping("assinvalida/{codass}")
    public boolean SubscriptionIsValid(@PathVariable Long codsub) {
        SubscriptionEntity subcriptionEntity = this.subscriptionRepository.getSubscriptionEntityByCode(codsub);
        return subcriptionEntity != null;
    }

}