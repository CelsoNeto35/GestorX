
package br.com.gestorx.api.repository;
import br.com.gestorx.api.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    @Query("SELECT v FROM Venda v WHERE v.cliente.id = :clienteId ORDER BY v.dataVenda DESC")
    List<Venda> findByClienteId(@Param("clienteId") Long clienteId);

    @Query("SELECT v FROM Venda v WHERE v.dataVenda BETWEEN :dataInicio AND :dataFim ORDER BY v.dataVenda DESC")
    List<Venda> findByPeriodo(@Param("dataInicio") LocalDateTime dataInicio, @Param("dataFim") LocalDateTime dataFim);

    @Query("SELECT v FROM Venda v WHERE v.ativo = true ORDER BY v.dataVenda DESC")
    List<Venda> findAllAtivos();

    Optional<Venda> findByIdAndAtivo(Long id, Boolean ativo);
}