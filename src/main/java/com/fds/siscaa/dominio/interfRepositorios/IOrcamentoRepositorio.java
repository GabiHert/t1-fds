package com.fds.siscaa.dominio.interfRepositorios;

import java.util.List;

import com.fds.siscaa.dominio.entidades.OrcamentoModel;

public interface IOrcamentoRepositorio {
    List<OrcamentoModel> todos();
    OrcamentoModel cadastra(OrcamentoModel orcamento);
    OrcamentoModel recuperaPorId(long id);
    void marcaComoEfetivado(long id); 
}
