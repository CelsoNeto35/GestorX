/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.gestorx.api.controller;

import br.com.gestorx.api.Dto.ProdutoDto;
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
public class CadastrarProdutoController {
      @GetMapping("/cadastrarProduto")
    public String listaProduto(Model model){
        ProdutoDto cadastroDto = new ProdutoDto();
        model.addAttribute("produto", cadastroDto);
        return "cadastrarProduto";
    }
}
