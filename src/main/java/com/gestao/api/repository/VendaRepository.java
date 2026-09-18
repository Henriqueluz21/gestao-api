package com.gestao.api.repository;

import com.gestao.api.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {

    List<Venda> findByDataBetween(LocalDateTime inicio, LocalDateTime fim);
}
