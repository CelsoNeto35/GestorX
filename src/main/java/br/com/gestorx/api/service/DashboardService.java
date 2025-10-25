package br.com.gestorx.api.service;

import br.com.gestorx.api.model.Venda;
import br.com.gestorx.api.model.Produto;
import br.com.gestorx.api.model.ItemVendas;
import br.com.gestorx.api.repository.VendaRepository;
import br.com.gestorx.api.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

@Service
public class DashboardService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    /**
     * Retorna vendas agrupadas por mês (últimos 6 meses)
     */
    public Map<String, Integer> getVendasPorMes() {
        Map<String, Integer> vendasPorMes = new LinkedHashMap<>();
        
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime seisMesesAtras = agora.minusMonths(6);
        
        // Buscar vendas dos últimos 6 meses
        List<Venda> vendas = vendaRepository.findByDataVendaBetweenAndAtivoTrue(seisMesesAtras, agora);
        
        // Agrupar por mês
        Map<Month, Integer> vendaPorMesEnum = new HashMap<>();
        
        for (Venda venda : vendas) {
            Month mes = venda.getDataVenda().getMonth();
            vendaPorMesEnum.put(mes, vendaPorMesEnum.getOrDefault(mes, 0) + 1);
        }
        
        // Converter para LinkedHashMap com nomes dos meses em ordem
        for (int i = 5; i >= 0; i--) {
            LocalDateTime data = agora.minusMonths(i);
            Month mes = data.getMonth();
            String nomeMes = mes.getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));
            nomeMes = nomeMes.substring(0, 1).toUpperCase() + nomeMes.substring(1);
            
            vendasPorMes.put(nomeMes, vendaPorMesEnum.getOrDefault(mes, 0));
        }
        
        return vendasPorMes;
    }

    /**
     * Retorna quantidade de produtos por categoria
     * (Para o gráfico de "Produtos")
     */
    public Map<String, Integer> getProdutosPorCategoria() {
        Map<String, Integer> produtosPorCategoria = new LinkedHashMap<>();
        
        List<Produto> produtos = produtoRepository.findAll();
        
        // Agrupar por categoria
        for (Produto produto : produtos) {
            String categoria = produto.getCategoria() != null ? produto.getCategoria() : "Sem Categoria";
            produtosPorCategoria.put(categoria, produtosPorCategoria.getOrDefault(categoria, 0) + 1);
        }
        
        // Ordenar por quantidade (decrescente)
        return produtosPorCategoria.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(LinkedHashMap::new, 
                        (map, entry) -> map.put(entry.getKey(), entry.getValue()), 
                        LinkedHashMap::putAll);
    }

    /**
     * Retorna raças/animais mais vendidos
     * (Assumindo que raças estão em categorias de produtos relacionados a animais)
     */
    public Map<String, Integer> getRacasVendidas() {
        Map<String, Integer> racasVendidas = new LinkedHashMap<>();
        
        LocalDateTime umAnoAtras = LocalDateTime.now().minusYears(1);
        List<Venda> vendas = vendaRepository.findByDataVendaBetweenAndAtivoTrue(umAnoAtras, LocalDateTime.now());
        
        // Mapear vendas por categoria de produto (assumindo que categoria representa raça)
        for (Venda venda : vendas) {
            if (venda.getItens() != null) {
                for (ItemVendas item : venda.getItens()) {
                    if (item.getEstoque() != null && item.getEstoque().getProduto() != null) {
                        String categoria = item.getEstoque().getProduto().getCategoria();
                        if (categoria != null && isRaca(categoria)) {
                            int quantidade = item.getQuantidade().intValue();
                            racasVendidas.put(categoria, racasVendidas.getOrDefault(categoria, 0) + quantidade);
                        }
                    }
                }
            }
        }
        
        // Se não houver dados, retornar valores padrão
        if (racasVendidas.isEmpty()) {
            racasVendidas.put("Nelore", 0);
            racasVendidas.put("Angus", 0);
            racasVendidas.put("Gir", 0);
            racasVendidas.put("Jersei", 0);
        }
        
        // Ordenar por quantidade
        return racasVendidas.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5) // Top 5 raças
                .collect(LinkedHashMap::new, 
                        (map, entry) -> map.put(entry.getKey(), entry.getValue()), 
                        LinkedHashMap::putAll);
    }

    /**
     * Verifica se a categoria representa uma raça de animal
     */
    private boolean isRaca(String categoria) {
        if (categoria == null) return false;
        
        String cat = categoria.toLowerCase();
        List<String> racasConhecidas = Arrays.asList(
            "nelore", "angus", "gir", "jersei", "jersey", 
            "holandês", "holandes", "girolando", "brahman"
        );
        
        for (String raca : racasConhecidas) {
            if (cat.contains(raca)) {
                return true;
            }
        }
        
        return false;
    }

    /**
     * Alternativa: Retorna vendas por categoria de produto
     * (Se as "raças" não forem diretamente categorias)
     */
    public Map<String, Integer> getVendasPorCategoria() {
        Map<String, Integer> vendasPorCategoria = new LinkedHashMap<>();
        
        LocalDateTime seisUltimosMeses = LocalDateTime.now().minusMonths(6);
        List<Venda> vendas = vendaRepository.findByDataVendaBetweenAndAtivoTrue(seisUltimosMeses, LocalDateTime.now());
        
        for (Venda venda : vendas) {
            if (venda.getItens() != null) {
                for (ItemVendas item : venda.getItens()) {
                    if (item.getEstoque() != null && item.getEstoque().getProduto() != null) {
                        String categoria = item.getEstoque().getProduto().getCategoria();
                        if (categoria != null) {
                            int quantidade = item.getQuantidade().intValue();
                            vendasPorCategoria.put(categoria, 
                                vendasPorCategoria.getOrDefault(categoria, 0) + quantidade);
                        }
                    }
                }
            }
        }
        
        return vendasPorCategoria.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .collect(LinkedHashMap::new, 
                        (map, entry) -> map.put(entry.getKey(), entry.getValue()), 
                        LinkedHashMap::putAll);
    }
}