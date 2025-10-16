/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.gestorx.api.repository;

import br.com.gestorx.api.model.Produto;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author cneto
 */
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findByDescricao(String descricao);
}
