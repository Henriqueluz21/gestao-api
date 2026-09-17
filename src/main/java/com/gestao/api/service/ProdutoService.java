package com.gestao.api.service;


import com.gestao.api.model.Produto;
import com.gestao.api.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public List<Produto> listarProduto() {
        return produtoRepository.findAll();
    }

    public Produto buscarPorSku(String sku) {
        return produtoRepository.findBySku(sku).orElseThrow(() -> new RuntimeException("Produto não encontrado com sku: " + sku));
    }

    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Produto criarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }
}
