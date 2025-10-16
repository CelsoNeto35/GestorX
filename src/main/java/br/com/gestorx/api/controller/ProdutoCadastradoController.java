/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.gestorx.api.controller;

import br.com.gestorx.api.service.ProdutoService;
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
public class ProdutoCadastradoController {
     @Autowired
    private ProdutoService produtoService;

    @GetMapping("/produtoCadastrados")
    public String listaProduto(Model model) {
        model.addAttribute("produtos", produtoService.listaProduto());
        return "produtoCadastrados"; // Nome da página Thymeleaf
}
}
