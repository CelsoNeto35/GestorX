package br.com.gestorx.api.controller;

import br.com.gestorx.api.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public String exibirIndex(Model model) {
        return "index";
    }

    /**
     * Endpoint para dados de vendas mensais (gráfico de colunas)
     */
    @GetMapping("/api/vendas-mensais")
    @ResponseBody
    public Map<String, Integer> getVendasMensais() {
        return dashboardService.getVendasPorMes();
    }

    /**
     * Endpoint para dados de raças/categorias (gráfico de pizza)
     */
    @GetMapping("/api/racas")
    @ResponseBody
    public Map<String, Integer> getRacas() {
        // Opção 1: Usar método que busca raças específicas
        Map<String, Integer> racas = dashboardService.getRacasVendidas();
        
        // Se não houver dados, buscar por categoria geral
        if (racas.isEmpty() || racas.values().stream().allMatch(v -> v == 0)) {
            racas = dashboardService.getVendasPorCategoria();
        }
        
        return racas;
    }

    /**
     * Endpoint para dados de produtos (gráfico donut)
     */
    @GetMapping("/api/produtos")
    @ResponseBody
    public Map<String, Integer> getProdutos() {
        return dashboardService.getProdutosPorCategoria();
    }
}