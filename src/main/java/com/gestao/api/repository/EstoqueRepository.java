package com.gestao.api.repository;

import com.gestao.api.model.Estoque;
import com.gestao.api.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    Optional<Estoque> findByProduto(Produto produto);

    void deleteByProduto(Produto produto);
}
