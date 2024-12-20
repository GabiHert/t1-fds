package com.fds.siscaa.interfaceAdapters.controller.routes;

import java.time.LocalDate;
import java.util.List;

import com.fds.siscaa.useCases.useCases.ValidSubscriptionUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fds.siscaa.domain.entity.ClientEntity;
import com.fds.siscaa.domain.entity.CalculatePaymentResponseEntity;
import com.fds.siscaa.domain.entity.ApplicationEntity;
import com.fds.siscaa.domain.entity.SubscriptionEntity;
import com.fds.siscaa.interfaceAdapters.controller.dto.ClientDto;
import com.fds.siscaa.interfaceAdapters.controller.dto.CreatePaymentDto;
import com.fds.siscaa.interfaceAdapters.controller.dto.CreatePaymentResponseDto;
import com.fds.siscaa.interfaceAdapters.controller.dto.ApplicationDto;
import com.fds.siscaa.interfaceAdapters.controller.dto.CreateSubscriptionDto;
import com.fds.siscaa.interfaceAdapters.controller.dto.SubscriptionDto;
import com.fds.siscaa.interfaceAdapters.controller.dto.UpdateCostDto;
import com.fds.siscaa.useCases.adapters.ClientRepositoryAdapter;
import com.fds.siscaa.useCases.useCases.CreatePaymentUseCase;
import com.fds.siscaa.useCases.useCases.CreateSubscriptionUseCase;

import lombok.AllArgsConstructor;

import com.fds.siscaa.useCases.adapters.ApplicationRepositoryAdapter;
import com.fds.siscaa.useCases.adapters.SubscriptionRepositoryAdapter;
import com.fds.siscaa.domain.enums.PaymentStatus;
import com.fds.siscaa.domain.enums.SubscriptionType;
import com.fds.siscaa.domain.utils.CustomList;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("")
@AllArgsConstructor
public class Routes {

    private final ClientRepositoryAdapter clientRepository;

    private final ApplicationRepositoryAdapter applicationRepository;

    private final CreateSubscriptionUseCase createSubscriptionUseCase;

    private final CreatePaymentUseCase createPaymentUseCase;

    private final SubscriptionRepositoryAdapter subscriptionRepository;

    private final ValidSubscriptionUseCase validSubscriptionUseCase;

    @PostMapping("servcad/assinaturas")
    public ResponseEntity<SubscriptionDto> postSubscription(
            @Valid @RequestBody CreateSubscriptionDto createSubscriptionDto) {
        System.out.println("postSubscription - STARTED - createSubscriptionDto: " + createSubscriptionDto.toString());

        SubscriptionEntity subscriptionEntity = this.createSubscriptionUseCase
                .create(createSubscriptionDto.getCodigoDoCliente(), createSubscriptionDto.getCodigoDoAplicativo());

        SubscriptionDto subscriptionDto = new SubscriptionDto(subscriptionEntity);
        System.out.println("postSubscription - FINISHED - subscriptionDto: " + subscriptionDto.toString());
        return ResponseEntity.status(201).body(subscriptionDto);
    }

    @PostMapping("registrarpagamento")
    public ResponseEntity<CreatePaymentResponseDto> postPayment(@Valid @RequestBody CreatePaymentDto createPaymentDto) {
        System.out.println("postPayment - STARTED - createPaymentDto" + createPaymentDto.toString());

        LocalDate paymentDate = LocalDate.of(
                createPaymentDto.getYear(),
                createPaymentDto.getMonth(),
                createPaymentDto.getDay());

        CalculatePaymentResponseEntity createPaymentResponse = this.createPaymentUseCase.create(
                paymentDate,
                createPaymentDto.getCodass(),
                createPaymentDto.getValorPago(),
                createPaymentDto.getCodpromo());

        CreatePaymentResponseDto createPaymentResponseDto = new CreatePaymentResponseDto(createPaymentResponse);
        System.out.println("postPayment - FINISHED - createPaymentResponseDto" + createPaymentResponseDto.toString());
        if (createPaymentResponse.getStatus().equals(PaymentStatus.VALOR_INCORRETO)) {
            return ResponseEntity.status(400).body(createPaymentResponseDto);
        }
        return ResponseEntity.status(201).body(createPaymentResponseDto);

    }

    @PostMapping("servcad/aplicativos/atualizacusto/{idAplicativo}")
    public ResponseEntity<ApplicationDto> updateCost(@Valid @RequestBody UpdateCostDto cost,
            @PathVariable long idAplicativo) {
        ApplicationEntity applicationEntity = this.applicationRepository.UpdateApplicationCostByCode(idAplicativo,
                cost.getCusto());
        return ResponseEntity.ok(new ApplicationDto(applicationEntity));
    }

    @GetMapping("servcad/clientes")
    public ResponseEntity<List<ClientDto>> listClients() {
        CustomList<ClientEntity> clientEntities = this.clientRepository.listClients();
        return ResponseEntity.ok(clientEntities.toDtos(ClientDto.class));
    }

    @GetMapping("servcad/aplicativos")
    public ResponseEntity<List<ApplicationDto>> listApplication() {
        CustomList<ApplicationEntity> applicationEntities = new CustomList<>(
                this.applicationRepository.listApplications());
        return ResponseEntity.ok(applicationEntities.toDtos(ApplicationDto.class));
    }

    @GetMapping("servcad/assinaturas/{tipo}")
    public ResponseEntity<List<SubscriptionDto>> listSubscriptionsByType(@PathVariable("tipo") String tipo) {
        SubscriptionType type;
        type = SubscriptionType.valueOf(tipo);
        CustomList<SubscriptionEntity> subscriptionEntities = this.subscriptionRepository.listSubscriptionByType(type);

        List<SubscriptionDto> subscriptionDtos = subscriptionEntities.toDtos(SubscriptionDto.class);

        return ResponseEntity.ok(subscriptionDtos);
    }

    @GetMapping("servcad/asscli/{codcli}")
    public ResponseEntity<List<SubscriptionDto>> listSubscriptionsByClientCode(@PathVariable Long codcli) {
        CustomList<SubscriptionEntity> subscriptionEntities = new CustomList<>(
                this.subscriptionRepository.listSubscriptionsByClientCode(codcli));
        return ResponseEntity.ok(subscriptionEntities.toDtos(SubscriptionDto.class));
    }

    @GetMapping("servcad/assapp/{codapp}")
    public ResponseEntity<List<SubscriptionDto>> listSubscriptionsByAppCode(@PathVariable Long codapp) {
        CustomList<SubscriptionEntity> subscriptionEntities = new CustomList<>(
                this.subscriptionRepository.listSubscriptionEntityByApplicationCode(codapp));
        return ResponseEntity.ok(subscriptionEntities.toDtos(SubscriptionDto.class));
    }

    @GetMapping("assinvalida/{codass}")
    public boolean subscriptionIsValid(@PathVariable Long codass) {
        return this.validSubscriptionUseCase.isValid(codass);
    }

}