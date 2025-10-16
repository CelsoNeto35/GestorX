/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.gestorx.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import br.com.gestorx.api.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author cneto
 */
@Controller
@RequestMapping()
public class ClienteCadastradoController {
     @Autowired
    private ClienteService clienteService;

    @GetMapping("/clienteCadastrados")
    public String listaCliente(Model model) {
        model.addAttribute("clientes", clienteService.listaCliente());
        return "clienteCadastrados"; // Nome da página Thymeleaf
}
}
