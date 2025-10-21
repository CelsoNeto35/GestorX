package br.com.gestorx.api.controller;

import br.com.gestorx.api.Dto.UsuarioDto;
import br.com.gestorx.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/editarUsuario")
public class editaUsuarioController {
    
    @Autowired
    UsuarioService service;
    
    @GetMapping("/{id}")
    public String atualizarUsuario(Model model, @PathVariable Long id){               
        
         UsuarioDto usuario = service.obterUsuario(id);
                
        model.addAttribute("usuario", usuario);
        
        if (usuario.getId() > 0){
            return "editarUsuario";
        }
        
        return "redirect:/usuarioCadastrados";
    }
}
