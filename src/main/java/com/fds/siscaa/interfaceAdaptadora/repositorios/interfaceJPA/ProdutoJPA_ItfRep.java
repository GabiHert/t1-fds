package com.fds.siscaa.interfaceAdaptadora.repositorios.interfaceJPA;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.fds.siscaa.interfaceAdaptadora.repositorios.entidades.Produto;

public interface ProdutoJPA_ItfRep extends CrudRepository<Produto, Long>{
    List<Produto> findAll();
    Produto findById(long id);
}
