package com.gestao.api.service;

import com.gestao.api.dto.ItemVendaDTO;
import com.gestao.api.dto.ItemVendaResponseDTO;
import com.gestao.api.dto.VendaCadastroDTO;
import com.gestao.api.dto.VendaResponseDTO;
import com.gestao.api.excepiton.ResourceNotFoundException;
import com.gestao.api.model.Estoque;
import com.gestao.api.model.ItemVenda;
import com.gestao.api.model.Produto;
import com.gestao.api.model.Venda;
import com.gestao.api.repository.EstoqueRepository;
import com.gestao.api.repository.ProdutoRepository;
import com.gestao.api.repository.VendaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;
    private final EstoqueRepository estoqueRepository;

    public VendaService(
            VendaRepository vendaRepository,
            ProdutoRepository produtoRepository,
            EstoqueRepository estoqueRepository
    ) {
        this.vendaRepository = vendaRepository;
        this.produtoRepository = produtoRepository;
        this.estoqueRepository = estoqueRepository;
    }

    @Transactional
    public VendaResponseDTO cadastrar(VendaCadastroDTO dto) {

        Venda venda = new Venda();

        venda.setData(LocalDateTime.now());
        venda.setValorTotal(BigDecimal.ZERO);

        List<ItemVenda> itens = new ArrayList<>();

        BigDecimal valorTotal = BigDecimal.ZERO;

        for (ItemVendaDTO itemDTO : dto.getItens()) {

            Produto produto = produtoRepository
                    .findById(itemDTO.getProdutoId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Produto não encontrado: "
                                            + itemDTO.getProdutoId()
                            )
                    );

            Estoque estoque = estoqueRepository
                    .findByProduto(produto)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Estoque não encontrado para o produto: "
                                            + produto.getNome()
                            )
                    );

            if (estoque.getQuantidade()
                    < itemDTO.getQuantidade()) {

                throw new ResourceNotFoundException(
                        "Estoque insuficiente para o produto: "
                                + produto.getNome()
                );
            }

            ItemVenda item = new ItemVenda();

            item.setVenda(venda);
            item.setProduto(produto);
            item.setQuantidade(itemDTO.getQuantidade());

            item.setPrecoUnitario(
                    produto.getPreco()
            );

            BigDecimal subtotal = produto.getPreco()
                    .multiply(
                            BigDecimal.valueOf(
                                    itemDTO.getQuantidade()
                            )
                    );

            valorTotal = valorTotal.add(subtotal);

            estoque.setQuantidade(
                    estoque.getQuantidade()
                            - itemDTO.getQuantidade()
            );

            estoqueRepository.save(estoque);

            // 9. Adiciona o item à lista da venda
            itens.add(item);
        }

        // 10. Coloca os itens na venda
        venda.setItens(itens);

        // 11. Define o valor total
        venda.setValorTotal(valorTotal);

        // 12. Salva a venda
        Venda vendaSalva = vendaRepository.save(venda);

        // 13. Converte para DTO de resposta
        return converterParaDTO(vendaSalva);
    }

    public List<VendaResponseDTO> listar() {

        return vendaRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public VendaResponseDTO buscarPorId(Long id) {

        Venda venda = vendaRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Venda não encontrada: " + id
                        )
                );

        return converterParaDTO(venda);
    }

    private VendaResponseDTO converterParaDTO(Venda venda) {

        List<ItemVendaResponseDTO> itens = venda
                .getItens()
                .stream()
                .map(item -> {

                    BigDecimal subtotal =
                            item.getPrecoUnitario()
                                    .multiply(
                                            BigDecimal.valueOf(
                                                    item.getQuantidade()
                                            )
                                    );

                    return new ItemVendaResponseDTO(
                            item.getProduto().getId(),
                            item.getProduto().getSku(),
                            item.getProduto().getNome(),
                            item.getQuantidade(),
                            item.getPrecoUnitario(),
                            subtotal
                    );
                })
                .toList();

        return new VendaResponseDTO(
                venda.getId(),
                venda.getData(),
                venda.getValorTotal(),
                itens
        );
    }
}