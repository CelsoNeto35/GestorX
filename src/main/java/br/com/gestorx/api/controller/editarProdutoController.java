/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.gestorx.api.controller;

import br.com.gestorx.api.Dto.FornecedorDto;
import br.com.gestorx.api.Dto.ProdutoDto;
import br.com.gestorx.api.service.ProdutoService;
import br.com.gestorx.api.service.FornecedorService;
import java.util.List;
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
@RequestMapping("/editarProduto")
public class editarProdutoController {
    @Autowired
    ProdutoService service;
    @Autowired
    FornecedorService fornecedorService;
    
    @GetMapping("/{id}")
public String atualizarProduto(Model model, @PathVariable Long id) {

    ProdutoDto produto = service.editarProduto(id);
    model.addAttribute("produto", produto);
    List<FornecedorDto> fornecedores = fornecedorService.listaFornecedor();
    model.addAttribute("fornecedores", fornecedores);

    if (produto.getId() > 0) {
        return "editarProduto";
    }

    return "redirect:/produtoCadastrados";
}

    
}
