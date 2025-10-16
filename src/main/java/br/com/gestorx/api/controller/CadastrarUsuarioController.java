/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.gestorx.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import br.com.gestorx.api.Dto.UsuarioDto;

/**
 *
 * @author cneto
 */
@Controller
@RequestMapping()
public class CadastrarUsuarioController {
   
      @GetMapping("/cadastrarUsuario")
    public String ListaUsuarios(Model model){
        
        UsuarioDto cadastroDto = new UsuarioDto();
        model.addAttribute("usuario", cadastroDto);
        return "cadastrarUsuario";
    }
    
}
