package com.fds.siscaa.interfaceAdapters.controller.routes;

import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fds.siscaa.domain.entity.ClientEntity;
import com.fds.siscaa.domain.entity.ApplicationEntity;
import com.fds.siscaa.domain.entity.SubscriptionEntity;
import com.fds.siscaa.interfaceAdapters.controller.dto.ClientDto;
import com.fds.siscaa.interfaceAdapters.controller.dto.ApplicationDto;
import com.fds.siscaa.interfaceAdapters.controller.dto.CreateSubscriptionDto;
import com.fds.siscaa.interfaceAdapters.controller.dto.SubscriptionDto;
import com.fds.siscaa.useCases.adapters.ClientRepositoryAdapter;
import com.fds.siscaa.useCases.useCases.CreateSubscriptionUseCase;
import com.fds.siscaa.useCases.adapters.ApplicationRepositoryAdapter;

@RestController
public class Routes {
    ClientRepositoryAdapter clientRepository;
    ApplicationRepositoryAdapter applicationRepository;
    CreateSubscriptionUseCase createSubscriptionUseCase;
    public Routes(){
    }

    @PostMapping("servcad/assinaturas")
    @CrossOrigin(origins = "*")
    public SubscriptionDto postSubscription(@RequestBody CreateSubscriptionDto subscriptionDto){
        SubscriptionEntity subscriptionEntity = this.createSubscriptionUseCase.create(0, 0);
        return new SubscriptionDto(subscriptionEntity);    
    }

    @GetMapping("efetivaOrcamento/{id}")
    @CrossOrigin(origins = "*")
    public OrcamentoDTO efetivaOrcamento(@PathVariable(value="id") long idOrcamento){
    }


   @GetMapping("servcad/clientes")
    @CrossOrigin(origins ="*")
    public List<ClientDto> listClients(){
        List<ClientEntity> clientEntities = this.clientRepository.listClients();

    };


    @GetMapping("servcad/aplicativos")
    @CrossOrigin(origins = "*")
    public List<ApplicationDto> listApplication(){
        List<ApplicationEntity> applicationEntities = this.applicationRepository.listApplications();
    }
}