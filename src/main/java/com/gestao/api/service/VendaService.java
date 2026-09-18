package com.gestao.api.service;

import com.gestao.api.model.Estoque;
import com.gestao.api.model.ItemVenda;
import com.gestao.api.model.Produto;
import com.gestao.api.model.Venda;
import com.gestao.api.repository.EstoqueRepository;
import com.gestao.api.repository.ProdutoRepository;
import com.gestao.api.repository.VendaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final EstoqueService estoqueService;
    private final ProdutoService produtoService;
    private final ProdutoRepository produtoRepository;
    private final EstoqueRepository estoqueRepository;

    public VendaService(VendaRepository vendaRepository, EstoqueService estoqueService, ProdutoService produtoService, ProdutoRepository produtoRepository, EstoqueRepository estoqueRepository) {
        this.vendaRepository = vendaRepository;
        this.estoqueService = estoqueService;
        this.produtoService = produtoService;
        this.produtoRepository = produtoRepository;
        this.estoqueRepository = estoqueRepository;
    }

    @Transactional
    public Venda registrarVenda(Long produtoId, Integer quantidade){

        Produto produto = produtoRepository.findById(produtoId).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        Estoque estoque = estoqueRepository.findByProduto(produto).orElseThrow(() -> new RuntimeException("Estoque não encontrado para o produto: " + produto.getNome()));

        estoque.setQuantidade(estoque.getQuantidade() - quantidade);
        estoqueRepository.save(estoque);

        ItemVenda item = new ItemVenda();
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setPrecoUnitario(produto.getPreco());

        Venda venda = new Venda();
        venda.setData(LocalDateTime.now());

        venda.setValorTotal(produto.getPreco().multiply(BigDecimal.valueOf(quantidade)));

        item.setVenda(venda);
        venda.getItens().add(item);
        return vendaRepository.save(venda);
    }
}
