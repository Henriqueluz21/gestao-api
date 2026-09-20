package com.gestao.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class VendaResponseDTO {

    private Long id;
    private LocalDateTime data;
    private BigDecimal valorTotal;
    private List<ItemVendaResponseDTO> itens;

    public VendaResponseDTO(
            Long id,
            LocalDateTime data,
            BigDecimal valorTotal,
            List<ItemVendaResponseDTO> itens
    ) {
        this.id = id;
        this.data = data;
        this.valorTotal = valorTotal;
        this.itens = itens;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public List<ItemVendaResponseDTO> getItens() {
        return itens;
    }
}