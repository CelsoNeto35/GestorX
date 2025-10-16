/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.gestorx.api.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import br.com.gestorx.api.Dto.EstoqueDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author cneto
 */
@Controller
@RequestMapping()
public class CadastrarEstoqueController {
    @GetMapping("/cadastrarEstoque")
    public String listarEstoque(Model model){
        EstoqueDto cadastroDto = new EstoqueDto();
        model.addAttribute("estoque", cadastroDto);
        return "cadastrarEstoque";
}
}
