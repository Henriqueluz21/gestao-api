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

    // CADASTRAR
    public Produto cadastrar(Produto produto) {

        return produtoRepository.save(produto);
    }

    // LISTAR TODOS
    public List<Produto> listar() {

        return produtoRepository.findAll();
    }

    // BUSCAR POR ID
    public Produto buscarPorId(Long id) {

        return produtoRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Produto não encontrado: " + id
                        )
                );
    }

    // ATUALIZAR
    public Produto atualizar(
            Long id,
            Produto produto
    ) {

        Produto produtoExistente =
                produtoRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Produto não encontrado: " + id
                                )
                        );

        produtoExistente.setSku(produto.getSku());
        produtoExistente.setNome(produto.getNome());
        produtoExistente.setDescricao(produto.getDescricao());
        produtoExistente.setPreco(produto.getPreco());

        return produtoRepository.save(produtoExistente);
    }

    // EXCLUIR
    public void excluir(Long id) {

        Produto produto =
                produtoRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Produto não encontrado: " + id
                                )
                        );

        produtoRepository.delete(produto);
    }
}