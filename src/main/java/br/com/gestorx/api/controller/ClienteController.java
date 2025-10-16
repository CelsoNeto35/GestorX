package br.com.gestorx.api.controller;

import br.com.gestorx.api.model.Cliente;
import br.com.gestorx.api.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import br.com.gestorx.api.Dto.ClienteDto;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    ClienteService service;
    
    @PostMapping
    public String cadastrarCliente(@ModelAttribute("clientes") ClienteDto cadastro){
        boolean sucesso = service.cadastrarCliente(cadastro);
        if(sucesso){
            return "redirect:/clienteCadastrados";
        }
        return "redirect:/cadastrarCliente?erro";
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String>excluirCliente(@PathVariable Long id){
        boolean sucesso = service.excluirCliente(id);
        if (sucesso){
            return ResponseEntity.ok("Cliente excluido com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir cliente");
        
    }
    
     @PostMapping("/{id}")
    public String atualizarCliente(@ModelAttribute("clientes") @PathVariable Long id, ClienteDto atualizar){
        
        boolean sucesso = service.atualizarCliente(id,atualizar);
        
        if(sucesso){
            return "redirect:/clienteCadastrados";
        }
        return "redirect:/clienteCadastrados?erro";
    }

}