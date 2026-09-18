package com.gestao.api.controller;

import com.gestao.api.model.Estoque;
import com.gestao.api.model.Produto;
import com.gestao.api.repository.ProdutoRepository;
import com.gestao.api.service.EstoqueService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {

    private final ProdutoRepository produtoRepository;
    private final EstoqueService estoqueService;

    public EstoqueController( ProdutoRepository produtoRepository, EstoqueService estoqueService) {
        this.produtoRepository = produtoRepository;
        this.estoqueService = estoqueService;
    }

    @GetMapping("/{produtoId}")
    public Estoque consultar(@PathVariable Long produtoId){
        Produto produto = produtoRepository.findById(produtoId).orElseThrow(() -> new RuntimeException("Produto não encontrado com id: " + produtoId));
        return estoqueService.consultarEstoque(produto);
    }
}
