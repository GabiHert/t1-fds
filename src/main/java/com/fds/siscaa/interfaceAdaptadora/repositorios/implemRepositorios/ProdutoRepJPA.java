package com.fds.siscaa.interfaceAdaptadora.repositorios.implemRepositorios;

import java.util.LinkedList;
import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.fds.siscaa.dominio.entidades.ProdutoModel;
import com.fds.siscaa.dominio.interfRepositorios.IProdutoRepositorio;
import com.fds.siscaa.interfaceAdaptadora.repositorios.interfaceJPA.ProdutoJPA_ItfRep;
import com.fds.siscaa.interfaceAdaptadora.repositorios.model.Produto;

@Repository
@Primary
public class ProdutoRepJPA implements IProdutoRepositorio {
    private ProdutoJPA_ItfRep produtoRepository;

    //@Autowired
    public ProdutoRepJPA(ProdutoJPA_ItfRep produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<ProdutoModel> todos() {
        List<Produto> produtos = produtoRepository.findAll();
        if (produtos.size() == 0) {
            return new LinkedList<ProdutoModel>();
        } else {
            return produtos.stream()
                    .map(prod -> Produto.toProdutoModel(prod))
                    .toList();
        }
    }

    public ProdutoModel consultaPorId(long id) {
        Produto produto = produtoRepository.findById(id);
        System.out.println("Produto de codigo: "+id+": "+produto);
        if (produto == null) {
            return null;
        } else {
            return Produto.toProdutoModel(produto);
        }
    }
}
