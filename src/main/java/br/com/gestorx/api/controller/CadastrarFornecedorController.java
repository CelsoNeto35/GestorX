/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.gestorx.api.controller;

import br.com.gestorx.api.Dto.FornecedorDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author cneto
 */
@Controller
@RequestMapping()
public class CadastrarFornecedorController {
     @GetMapping("/cadastrarFornecedor")
    public String listaFornecedor(Model model){
        FornecedorDto cadastroDto = new FornecedorDto();
        model.addAttribute("fornecedor", cadastroDto);
        return "cadastrarFornecedor";
    }
    
}
