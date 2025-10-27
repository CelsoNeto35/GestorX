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
    
    @Query("SELECT v FROM Venda v WHERE v.dataVenda BETWEEN :inicio AND :fim AND v.ativo = true ORDER BY v.dataVenda DESC")
    List<Venda> findByDataVendaBetweenAndAtivoTrue(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    /**
     * Query para obter total de vendas por mês
     * Retorna: [mês, ano, quantidade]
     */
    @Query("SELECT MONTH(v.dataVenda), YEAR(v.dataVenda), COUNT(v) " +
           "FROM Venda v WHERE v.ativo = true " +
           "GROUP BY YEAR(v.dataVenda), MONTH(v.dataVenda) " +
           "ORDER BY YEAR(v.dataVenda) DESC, MONTH(v.dataVenda) DESC")
    List<Object[]> countVendasPorMes();
    
    @Query("SELECT v FROM Venda v WHERE v.ativo = true " +
           "AND v.dataVenda >= :dataInicio " +
           "ORDER BY v.dataVenda DESC")
    List<Venda> findVendasRecentes(@Param("dataInicio") LocalDateTime dataInicio);
    
    // ========== NOVOS MÉTODOS COM JOIN FETCH ==========
    
    /**
     * Busca uma venda por ID com todos os relacionamentos carregados (EAGER)
     * Resolve o problema de LazyInitializationException
     */
    @Query("SELECT DISTINCT v FROM Venda v " +
           "LEFT JOIN FETCH v.cliente " +
           "LEFT JOIN FETCH v.itens i " +
           "LEFT JOIN FETCH i.estoque " +
           "WHERE v.id = :id")
    Optional<Venda> findByIdWithItens(@Param("id") Long id);
    
    /**
     * Lista todas as vendas ativas com itens carregados
     * Use este método na tela de listagem para evitar N+1 queries
     */
    @Query("SELECT DISTINCT v FROM Venda v " +
           "LEFT JOIN FETCH v.cliente " +
           "LEFT JOIN FETCH v.itens i " +
           "LEFT JOIN FETCH i.estoque " +
           "WHERE v.ativo = true " +
           "ORDER BY v.dataVenda DESC")
    List<Venda> findAllWithItens();
    
    /**
     * Busca vendas por período com itens carregados
     */
    @Query("SELECT DISTINCT v FROM Venda v " +
           "LEFT JOIN FETCH v.cliente " +
           "LEFT JOIN FETCH v.itens i " +
           "LEFT JOIN FETCH i.estoque " +
           "WHERE v.dataVenda BETWEEN :inicio AND :fim " +
           "AND v.ativo = true " +
           "ORDER BY v.dataVenda DESC")
    List<Venda> findByPeriodoWithItens(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
}