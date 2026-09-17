package com.gestao.api.repository;

import com.gestao.api.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findBySku(String sku);

    List<Produto> findByNomeContainingIgnoreCase(String nome);

    String sku(String sku);
}
