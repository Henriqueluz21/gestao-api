package com.gestao.api.controller;

import com.gestao.api.dto.EstoqueCadastroDTO;
import com.gestao.api.dto.EstoqueResponseDTO;
import com.gestao.api.service.EstoqueService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estoque")
public class EstoqueController {

    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstoqueResponseDTO cadastrar(
            @Valid @RequestBody EstoqueCadastroDTO dto
    ) {
        return estoqueService.cadastrar(dto);
    }

    @GetMapping
    public List<EstoqueResponseDTO> listar() {
        return estoqueService.listar();
    }

    @GetMapping("/{id}")
    public EstoqueResponseDTO buscarPorId(
            @PathVariable Long id
    ) {
        return estoqueService.buscarPorId(id);
    }

    @GetMapping("/produto/{produtoId}")
    public EstoqueResponseDTO buscarPorProduto(
            @PathVariable Long produtoId
    ) {
        return estoqueService.buscarPorProduto(produtoId);
    }
}