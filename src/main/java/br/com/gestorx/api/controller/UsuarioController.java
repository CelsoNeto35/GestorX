/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.gestorx.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import br.com.gestorx.api.service.UsuarioService;
import br.com.gestorx.api.Dto.UsuarioDto;

/**
 *
 * @author cneto
 */
@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    UsuarioService service;
    
    @PostMapping
    public String cadastrarUsuario(@ModelAttribute("usuario") UsuarioDto cadastro){
        boolean sucesso = service.cadastrarUsuario(cadastro);
        if(sucesso){
            return "redirect:usuarioCadastrados";
        }
        return "redirect:cadastrarUsuario?erro";
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String>  excluirUsuario(@PathVariable Long id){
    
        boolean sucesso = service.excluirUsuario(id);
        
        if (sucesso){
            return ResponseEntity.ok("Usuário excluído com sucesso.");
        }
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir usuário.");
        
    }
    
     @PostMapping("/{id}")
    public String atualizarContato(@ModelAttribute("usuario") @PathVariable Long id, UsuarioDto atualizar){
        
        boolean sucesso = service.atualizarUsuario(id,atualizar);
        
        if(sucesso){
            return "redirect:/usuarioCadastrados";
        }
        return "redirect:/cadastrarUsuario?erro";
    }
    
}
