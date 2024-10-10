package com.fds.siscaa.interfaceAdaptadora.repositorios.implemRepositorios;

import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.fds.siscaa.dominio.entidades.ProdutoModel;
import com.fds.siscaa.dominio.interfRepositorios.IEstoqueRepositorio;
import com.fds.siscaa.interfaceAdaptadora.repositorios.interfaceJPA.EstoqueJPA_ItfRep;
import com.fds.siscaa.interfaceAdaptadora.repositorios.model.ItemDeEstoque;
import com.fds.siscaa.interfaceAdaptadora.repositorios.model.Produto;


@Repository
@Primary
public class EstoqueRepJPA implements IEstoqueRepositorio{
    private EstoqueJPA_ItfRep estoque;

    //@Autowired
    public EstoqueRepJPA(EstoqueJPA_ItfRep estoque){
        this.estoque = estoque;
    }

    @Override
    public List<ProdutoModel> todos() {
        List<ItemDeEstoque> itens = estoque.findAll();
        return itens.stream()
                .map(it->Produto.toProdutoModel(it.getProduto()))
                .toList();
    }

    @Override
    public List<ProdutoModel> todosComEstoque() {
        List<ItemDeEstoque> itens = estoque.findAll();
        return itens.stream()
                .filter(it->it.getQuantidade()>0)
                .map(it->Produto.toProdutoModel(it.getProduto()))
                .toList();
    }

    @Override
    public int quantidadeEmEstoque(long codigo) {
        ItemDeEstoque item = estoque.findById(codigo).orElse(null);
        if (item == null){
            return -1;
        }else{
            return item.getQuantidade();
        }
    }

    @Override
    public int baixaEstoque(long codProd, int qtdade) {
        ItemDeEstoque item = estoque.findById(codProd).orElse(null);
        if (item == null){
            throw new IllegalArgumentException("Produto inexistente");
        }
        if (item.getQuantidade() < qtdade){
            throw new IllegalArgumentException("Quantidade em estoque insuficiente");
        }
        int novaQuantidade = item.getQuantidade() - qtdade;
        item.setQuantidade(novaQuantidade);
        estoque.save(item);
        return novaQuantidade;
    }
}
