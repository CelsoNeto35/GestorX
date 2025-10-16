/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.gestorx.api.controller;

import br.com.gestorx.api.Dto.FornecedorDto;
import br.com.gestorx.api.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author cneto
 */
@Controller
@RequestMapping("/fornecedor")
public class FornecedorController {
    @Autowired
    FornecedorService service;
    
    @PostMapping
    public String cadastrarFornecedor(@ModelAttribute("fornecedor") FornecedorDto cadastro){
        boolean sucesso = service.cadastrarFornecedor(cadastro);
        if(sucesso){
            return "redirect:/fornecedorCadastrados";
        }
        return "redirect:/cadastrarFornecedor?erro";
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String>excluirFornecedor(@PathVariable Long id){
        boolean sucesso = service.excluirFornecedor(id);
        if (sucesso){
            return ResponseEntity.ok("Fornecedor excluido com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir fornecedor");
        
    }
    
     @PostMapping("/{id}")
    public String atualizarCliente(@ModelAttribute("fornecedor") @PathVariable Long id, FornecedorDto atualizar){
        
        boolean sucesso = service.atualizarFornecedor(id,atualizar);
        
        if(sucesso){
            return "redirect:/fornecedorCadastrados";
        }
        return "redirect:/cadastrarFornecedor?erro";
    }
}
