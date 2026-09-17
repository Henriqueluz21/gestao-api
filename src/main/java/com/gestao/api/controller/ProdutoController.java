package com.gestao.api.controller;


import com.gestao.api.model.Produto;
import com.gestao.api.service.ProdutoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public List<Produto> listar(){
        return produtoService.listarProduto();
    }

    @GetMapping("/buscar")
    public Produto buscar(@RequestParam String sku){
        return produtoService.buscarPorSku(sku);
    }

    @PostMapping
    public Produto criar(@RequestBody Produto produto){
        return produtoService.criarProduto(produto);
    }
}
