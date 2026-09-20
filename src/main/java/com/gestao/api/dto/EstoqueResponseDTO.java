package com.gestao.api.dto;

public class EstoqueResponseDTO {

    private Long id;
    private Long produtoId;
    private String sku;
    private String nomeProduto;
    private Integer quantidade;

    public EstoqueResponseDTO(
            Long id,
            Long produtoId,
            String sku,
            String nomeProduto,
            Integer quantidade
    ) {
        this.id = id;
        this.produtoId = produtoId;
        this.sku = sku;
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
    }

    public Long getId() {
        return id;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public String getSku() {
        return sku;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }
}