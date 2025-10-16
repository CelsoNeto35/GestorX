package br.com.gestorx.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/index")
public class IndexController {

    @GetMapping
    public String exibirIndex(Model model) {
        // Dados de exemplo para o gráfico (vendas por mês)
        Map<String, Integer> vendasPorMes = new LinkedHashMap<>();
        vendasPorMes.put("Janeiro", 120);
        vendasPorMes.put("Fevereiro", 150);
        vendasPorMes.put("Março", 100);
        vendasPorMes.put("Abril", 180);

        // Adiciona os dados ao modelo para Thymeleaf
        model.addAttribute("vendasPorMes", vendasPorMes);

        return "index";
    }

    @PostMapping
    public String voltarParaLogin() {
        System.out.println("Vai voltar para o login:");
        return "redirect:/login";
    }
}
