/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.gestorx.api.controller;

import br.com.gestorx.api.Dto.ClienteDto;
import br.com.gestorx.api.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author cneto
 */
@Controller
@RequestMapping("/editarCliente")
public class editarClienteController {
    @Autowired
    ClienteService service;
    
    @GetMapping("/{id}")
    public String atualizarCliente(Model model, @PathVariable Long id){               
        
         ClienteDto cliente = service.buscarClientePorId(id);
                
        model.addAttribute("clientes", cliente);
        
         if (cliente.getId() > 0){
            return "editarCliente";
         }
        return "redirect:/clienteCadastrados";
    }  
    
}
