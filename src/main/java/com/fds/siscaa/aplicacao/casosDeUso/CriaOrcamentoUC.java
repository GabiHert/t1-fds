package com.fds.siscaa.aplicacao.casosDeUso;

import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fds.siscaa.aplicacao.dtos.ItemPedidoDTO;
import com.fds.siscaa.aplicacao.dtos.OrcamentoDTO;
import com.fds.siscaa.dominio.entidades.ItemPedidoModel;
import com.fds.siscaa.dominio.entidades.OrcamentoModel;
import com.fds.siscaa.dominio.entidades.PedidoModel;
import com.fds.siscaa.dominio.entidades.ProdutoModel;
import com.fds.siscaa.dominio.servicos.ServicoDeEstoque;
import com.fds.siscaa.dominio.servicos.ServicoDeVendas;

@Component
public class CriaOrcamentoUC {
    private ServicoDeVendas servicoDeVendas;
    private ServicoDeEstoque servicoDeEstoque;
    
    //@Autowired
    public CriaOrcamentoUC(ServicoDeVendas servicoDeVendas,ServicoDeEstoque servicoDeEstoque){
        this.servicoDeVendas = servicoDeVendas;
        this.servicoDeEstoque = servicoDeEstoque;
    }

    public OrcamentoDTO run(List<ItemPedidoDTO> itens){
        PedidoModel pedido = new PedidoModel(0);
        for(ItemPedidoDTO item:itens){
            ProdutoModel produto = servicoDeEstoque.produtoPorCodigo(item.getIdProduto());
            ItemPedidoModel itemPedido = new ItemPedidoModel(produto, item.getQtdade());
            pedido.addItem(itemPedido);
        }
        OrcamentoModel orcamento = servicoDeVendas.criaOrcamento(pedido);
        return OrcamentoDTO.fromModel(orcamento);
    }
}
