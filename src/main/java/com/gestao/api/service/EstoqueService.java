package com.gestao.api.service;

import com.gestao.api.dto.EstoqueCadastroDTO;
import com.gestao.api.dto.EstoqueResponseDTO;
import com.gestao.api.excepiton.ResourceNotFoundException;
import com.gestao.api.model.Estoque;
import com.gestao.api.model.Produto;
import com.gestao.api.repository.EstoqueRepository;
import com.gestao.api.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final ProdutoRepository produtoRepository;

    public EstoqueService(
            EstoqueRepository estoqueRepository,
            ProdutoRepository produtoRepository
    ) {
        this.estoqueRepository = estoqueRepository;
        this.produtoRepository = produtoRepository;
    }

    @Transactional
    public EstoqueResponseDTO cadastrar(EstoqueCadastroDTO dto) {

        // 1. Busca o produto
        Produto produto = produtoRepository
                .findById(dto.getProdutoId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Produto não encontrado: "
                                        + dto.getProdutoId()
                        )
                );

        Estoque estoque = estoqueRepository
                .findByProduto(produto)
                .orElse(null);

        if (estoque != null) {

            estoque.setQuantidade(
                    estoque.getQuantidade()
                            + dto.getQuantidade()
            );

        } else {

            estoque = new Estoque();

            estoque.setProduto(produto);
            estoque.setQuantidade(dto.getQuantidade());
        }

        Estoque estoqueSalvo =
                estoqueRepository.save(estoque);

        return converterParaDTO(estoqueSalvo);
    }

    @Transactional
    public EstoqueResponseDTO atualizar(
            Long id,
            EstoqueCadastroDTO dto
    ) {

        Estoque estoque = estoqueRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Estoque não encontrado: " + id
                        )
                );

        Produto produto = produtoRepository
                .findById(dto.getProdutoId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Produto não encontrado: "
                                        + dto.getProdutoId()
                        )
                );

        estoque.setProduto(produto);
        estoque.setQuantidade(dto.getQuantidade());

        Estoque estoqueAtualizado =
                estoqueRepository.save(estoque);

        return converterParaDTO(estoqueAtualizado);
    }

    @Transactional
    public void excluir(Long id) {

        Estoque estoque = estoqueRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Estoque não encontrado: " + id
                        )
                );

        estoqueRepository.delete(estoque);
    }

    public List<EstoqueResponseDTO> listar() {

        return estoqueRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public EstoqueResponseDTO buscarPorId(Long id) {

        Estoque estoque = estoqueRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Estoque não encontrado: " + id
                        )
                );

        return converterParaDTO(estoque);
    }

    public EstoqueResponseDTO buscarPorProduto(Long produtoId) {

        Produto produto = produtoRepository
                .findById(produtoId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Produto não encontrado: "
                                        + produtoId
                        )
                );

        Estoque estoque = estoqueRepository
                .findByProduto(produto)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Estoque não encontrado para o produto: "
                                        + produtoId
                        )
                );

        return converterParaDTO(estoque);
    }

    private EstoqueResponseDTO converterParaDTO(
            Estoque estoque
    ) {

        Produto produto = estoque.getProduto();

        return new EstoqueResponseDTO(
                estoque.getId(),
                produto.getId(),
                produto.getSku(),
                produto.getNome(),
                estoque.getQuantidade()
        );
    }
}