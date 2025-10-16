/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.gestorx.api.controller;

import br.com.gestorx.api.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author cneto
 */
@Controller
@RequestMapping
public class FornecedorCadastradoController {
     @Autowired
    private FornecedorService fornecedorService;

    @GetMapping("/fornecedorCadastrados")
    public String listaFornecedor(Model model) {
        model.addAttribute("fornecedores", fornecedorService.listaFornecedor());
        return "fornecedorCadastrados"; // Nome da página Thymeleaf
    
}
}
