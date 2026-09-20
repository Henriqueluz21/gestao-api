package com.gestao.api.controller;

import com.gestao.api.dto.VendaCadastroDTO;
import com.gestao.api.dto.VendaResponseDTO;
import com.gestao.api.service.VendaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendas")
public class VendaController {

    private final VendaService vendaService;

    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendaResponseDTO cadastrar(
            @Valid @RequestBody VendaCadastroDTO dto
    ) {
        return vendaService.cadastrar(dto);
    }

    @GetMapping
    public List<VendaResponseDTO> listar() {
        return vendaService.listar();
    }

    @GetMapping("/{id}")
    public VendaResponseDTO buscarPorId(
            @PathVariable Long id
    ) {
        return vendaService.buscarPorId(id);
    }
}