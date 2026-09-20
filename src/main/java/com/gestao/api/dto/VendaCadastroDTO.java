package com.gestao.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class VendaCadastroDTO {

    @NotEmpty
    @Valid
    private List<ItemVendaDTO> itens;

    public List<ItemVendaDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemVendaDTO> itens) {
        this.itens = itens;
    }
}
