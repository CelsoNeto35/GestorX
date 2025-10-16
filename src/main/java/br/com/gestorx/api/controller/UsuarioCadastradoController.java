/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.gestorx.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import br.com.gestorx.api.service.UsuarioService;

/**
 *
 * @author cneto
 */
@Controller
@RequestMapping()
public class UsuarioCadastradoController {
    @Autowired
    UsuarioService usuarioService;
    
    @GetMapping("/usuarioCadastrados")
    public String ListaUsuarios(Model model){
     model.addAttribute("usuarios", usuarioService.listarUsuarios());
     return "usuarioCadastrados";
    }
}
