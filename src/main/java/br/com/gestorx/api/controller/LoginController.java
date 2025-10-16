/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.gestorx.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import br.com.gestorx.api.Dto.LoginDto;
import br.com.gestorx.api.service.UsuarioService;

/**
 *
 * @author cneto
 */
@Controller
@RequestMapping()
public class LoginController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String exibirLogin(Model model) {
        LoginDto loginDto = new LoginDto();
        model.addAttribute("loginDto", loginDto);

        // Retorna a página de login (templates/login.html)
        return "login";
    }

    @PostMapping("/login")
    public String realizarLogin(@ModelAttribute("LoginDto") LoginDto loginDto,
                                RedirectAttributes redirectAttributes) {

        boolean acesso = usuarioService.validarLogin(loginDto);

        if (acesso) {
            // Define um atributo temporário para exibir o pop-up de boas-vindas
            redirectAttributes.addFlashAttribute("loginSucesso", true);
            return "redirect:/index";
        }

        // Caso as credenciais estejam incorretas
        return "redirect:/login?erro";
    }

    @PostMapping("/logout")
    public String realizarLogout() {
        // Redireciona para o login ao sair
        return "redirect:/login?logout";
    }
}
