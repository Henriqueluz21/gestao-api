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

        // 2. Verifica se já existe estoque para esse produto
        Estoque estoque = estoqueRepository
                .findByProduto(produto)
                .orElse(null);

        // 3. Se já existe, adiciona a quantidade
        if (estoque != null) {

            estoque.setQuantidade(
                    estoque.getQuantidade()
                            + dto.getQuantidade()
            );

        } else {

            // 4. Se não existe, cria um novo estoque
            estoque = new Estoque();

            estoque.setProduto(produto);
            estoque.setQuantidade(dto.getQuantidade());
        }

        // 5. Salva
        Estoque estoqueSalvo =
                estoqueRepository.save(estoque);

        // 6. Retorna DTO
        return converterParaDTO(estoqueSalvo);
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