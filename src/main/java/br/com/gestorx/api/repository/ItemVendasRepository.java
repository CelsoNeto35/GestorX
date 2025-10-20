package br.com.gestorx.api.repository;

import br.com.gestorx.api.model.ItemVendas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemVendasRepository extends JpaRepository<ItemVendas, Long> {

   @Query("SELECT iv FROM ItemVendas iv WHERE iv.venda.id = :vendaId")
    List<ItemVendas> findByVendaId(@Param("vendaId") Long vendaId);
    
    @Query("SELECT iv FROM ItemVendas iv WHERE iv.estoque.id = :estoqueId")
    List<ItemVendas> findByEstoqueId(@Param("estoqueId") Long estoqueId);
}
