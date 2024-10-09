package com.fds.siscaa.dominio.interfRepositorios;

import java.util.List;

import com.fds.siscaa.dominio.entidades.ProdutoModel;

public interface IEstoqueRepositorio {
    List<ProdutoModel> todos();
    List<ProdutoModel> todosComEstoque();
    int quantidadeEmEstoque(long codigo);
    int baixaEstoque(long codProd, int qtdade);
}
