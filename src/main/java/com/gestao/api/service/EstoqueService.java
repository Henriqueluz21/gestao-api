package com.gestao.api.service;


import com.gestao.api.model.Estoque;
import com.gestao.api.model.Produto;
import com.gestao.api.repository.EstoqueRepository;
import org.springframework.stereotype.Service;

@Service
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;

    public EstoqueService(EstoqueRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
    }

    public Estoque consultarEstoque(Produto produto){

        return estoqueRepository.findByProduto(produto)
                .orElseThrow(() -> new RuntimeException("Estoque não encontrado para o produto: " + produto.getNome()));
    }

    public Estoque adiconarEstoque(Produto produto, Integer quantidade){

        Estoque estoque = estoqueRepository.findByProduto(produto)
                .orElseGet(() -> {
                    Estoque novoEstoque = new Estoque();
                    novoEstoque.setProduto(produto);
                    novoEstoque.setQuantidade(0);
                    return novoEstoque;
                });
        estoque.setQuantidade(estoque.getQuantidade() + quantidade);
        return estoqueRepository.save(estoque);
    }

}
