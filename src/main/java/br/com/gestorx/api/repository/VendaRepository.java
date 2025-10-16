package br.com.gestorx.api.repository;

import br.com.gestorx.api.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Long> {}
