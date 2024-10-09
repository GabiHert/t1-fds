package com.fds.siscaa.aplicacao.casosDeUso;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fds.siscaa.aplicacao.dtos.OrcamentoDTO;
import com.fds.siscaa.dominio.entidades.OrcamentoModel;
import com.fds.siscaa.dominio.servicos.ServicoDeVendas;

@Component
public class EfetivaOrcamentoUC {
    private ServicoDeVendas servicoDeVendas;
    
    //@Autowired
    public EfetivaOrcamentoUC(ServicoDeVendas servicoDeVendas){
        this.servicoDeVendas = servicoDeVendas;
    }

    public OrcamentoDTO run(long idOrcamento){
        OrcamentoModel orcamento =  servicoDeVendas.efetivaOrcamento(idOrcamento);
        return OrcamentoDTO.fromModel(orcamento);
    }
}