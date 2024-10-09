package com.fds.siscaa.dominio.servicos;

import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.fds.siscaa.dominio.entidades.ProdutoModel;
import com.fds.siscaa.dominio.interfRepositorios.IEstoqueRepositorio;
import com.fds.siscaa.dominio.interfRepositorios.IProdutoRepositorio;

@Service
public class ServicoDeEstoque{
    private IEstoqueRepositorio estoque;
    private IProdutoRepositorio produtos;
    
    //@Autowired
    public ServicoDeEstoque(IProdutoRepositorio produtos,IEstoqueRepositorio estoque){
        this.produtos = produtos;
        this.estoque = estoque;
    }
 
    public List<ProdutoModel> produtosDisponiveis(){
        return estoque.todosComEstoque();
    }

    public ProdutoModel produtoPorCodigo(long id){
        return this.produtos.consultaPorId(id);
    }

    public int qtdadeEmEstoque(long id){
        return estoque.quantidadeEmEstoque(id);
    }

    public void baixaEstoque(long id,int qtdade){
        estoque.baixaEstoque(id,qtdade);
    }  
}
