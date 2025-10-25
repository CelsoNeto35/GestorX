/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.gestorx.api.repository;

import br.com.gestorx.api.model.Produto;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author cneto
 */
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findByDescricao(String descricao);

    List<Produto> findByCategoria(String categoria);
    
    @Query("SELECT p.categoria, COUNT(p) FROM Produto p GROUP BY p.categoria")
    List<Object[]> countByCategoria();
    
   
    List<Produto> findByDescricaoContainingIgnoreCase(String descricao);
}
