package com.fds.siscaa.interfaceAdapters.controller.routes;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.fds.siscaa.interfaceAdapters.controller.parser.ApplicationDtoParser;
import com.fds.siscaa.interfaceAdapters.controller.parser.ClientDtoParser;
import com.fds.siscaa.useCases.adapters.ClientRepositoryAdapter;
import com.fds.siscaa.useCases.useCases.CreatePaymentResponse;
import com.fds.siscaa.useCases.useCases.CreatePaymentUseCase;
import com.fds.siscaa.useCases.useCases.CreateSubscriptionUseCase;
import com.fds.siscaa.useCases.adapters.ApplicationRepositoryAdapter;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("servcad")
public class Routes {
    ClientRepositoryAdapter clientRepository;
    ApplicationRepositoryAdapter applicationRepository;
    CreateSubscriptionUseCase createSubscriptionUseCase;
    CreatePaymentUseCase createPaymentUseCase;
    ClientDtoParser clientDtoParser;
    ApplicationDtoParser applicationDtoParser;

    public Routes() {
    }

    @PostMapping("assinaturas")
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

    @GetMapping("clientes")
    public List<ClientDto> listClients() {
        List<ClientEntity> clientEntities = this.clientRepository.listClients();
        return this.clientDtoParser.parseClientEntitiesToDto(clientEntities);

    };

    @GetMapping("aplicativos")
    public List<ApplicationDto> listApplication() {
        List<ApplicationEntity> applicationEntities = this.applicationRepository.listApplications();
        return this.applicationDtoParser.parseApplicationEntitiesToDto(applicationEntities);
    }

    @GetMapping("assinaturas/{tipo}")
    public List<ApplicationDto> listApplication() {
        List<ApplicationEntity> applicationEntities = this.applicationRepository.listApplications();
        return this.applicationDtoParser.parseApplicationEntitiesToDto(applicationEntities);
    }
}