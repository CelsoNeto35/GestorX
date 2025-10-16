package br.com.gestorx.api.repository;

import br.com.gestorx.api.model.Estoque;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    Optional<Estoque> findByLote(String lote);
}
