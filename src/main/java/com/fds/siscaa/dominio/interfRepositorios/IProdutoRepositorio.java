package com.fds.siscaa.dominio.interfRepositorios;

import java.util.List;

import com.fds.siscaa.dominio.entidades.ProdutoModel;

public interface IProdutoRepositorio {
    List<ProdutoModel> todos();
    ProdutoModel consultaPorId(long codigo);
}
