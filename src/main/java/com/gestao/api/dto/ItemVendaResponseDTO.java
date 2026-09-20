package com.gestao.api.dto;

import java.math.BigDecimal;

public class ItemVendaResponseDTO {

    private Long produtoId;
    private String sku;
    private String nomeProduto;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal subtotal;

    public ItemVendaResponseDTO(
            Long produtoId,
            String sku,
            String nomeProduto,
            Integer quantidade,
            BigDecimal precoUnitario,
            BigDecimal subtotal
    ) {
        this.produtoId = produtoId;
        this.sku = sku;
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.subtotal = subtotal;
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

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }
}