package com.gestao.api.dto;

import java.math.BigDecimal;

public class ProdutoResponseDTO {

    private Long id;
    private String sku;
    private String nome;
    private String descricao;
    private BigDecimal preco;

    public ProdutoResponseDTO(
            Long id,
            String sku,
            String nome,
            String descricao,
            BigDecimal preco
    ) {
        this.id = id;
        this.sku = sku;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    public Long getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }
}