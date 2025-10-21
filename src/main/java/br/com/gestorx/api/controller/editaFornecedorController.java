/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.gestorx.api.controller;

import br.com.gestorx.api.Dto.FornecedorDto;
import br.com.gestorx.api.service.FornecedorService;
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
@RequestMapping("/editarFornecedor")
public class editaFornecedorController {
    @Autowired
    FornecedorService service;
    
    @GetMapping("/{id}")
    public String atualizarFornecedor(Model model, @PathVariable Long id){               
        
         FornecedorDto fornecedor = service.buscarFornecedorPorId(id);
                
        model.addAttribute("fornecedor", fornecedor);
        
         if (fornecedor.getId() > 0){
            return "editarFornecedor";
         }
        return "redirect:/fornecedorCadastrados";
    }  
}
