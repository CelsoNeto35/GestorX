package br.com.gestorx.api.controller;

import br.com.gestorx.api.model.Venda;
import br.com.gestorx.api.model.ItemVendas;
import br.com.gestorx.api.model.Estoque;
import br.com.gestorx.api.model.Cliente;
import br.com.gestorx.api.model.FormaPagamento;
import br.com.gestorx.api.model.CondicaoPagamento;
import br.com.gestorx.api.service.VendaService;
import br.com.gestorx.api.service.ClienteService;
import br.com.gestorx.api.service.EstoqueService;
import br.com.gestorx.api.Dto.ClienteDto;
import br.com.gestorx.api.Dto.EstoqueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EstoqueService estoqueService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        binder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                try {
                    setValue(LocalDateTime.parse(text, formatter));
                } catch (Exception e) {
                    setValue(null);
                }
            }
        });
    }

    @GetMapping("/vendaCadastradas")
    public String vendaCadastradas(Model model) {
        List<Venda> vendas = vendaService.listar();
        model.addAttribute("vendas", vendas);
        return "vendaCadastradas";
    }

    @GetMapping("/cadastrarVenda")
    public String cadastrarVenda(Model model) {
        Venda venda = new Venda();
        List<ClienteDto> clientes = clienteService.listaCliente();
        List<EstoqueDto> estoques = estoqueService.listarEstoque();
        
        model.addAttribute("venda", venda);
        model.addAttribute("clientes", clientes);
        model.addAttribute("estoques", estoques);
        model.addAttribute("formasPagamento", FormaPagamento.values());
        model.addAttribute("condicoesPagamento", CondicaoPagamento.values());
        
        return "cadastrarVenda";
    }

    @PostMapping("/venda")
    public String salvar(@RequestParam(required = false) Long clienteId,
                        @RequestParam(required = false) String formaPagamento,
                        @RequestParam(required = false) String condicaoPagamento,
                        @RequestParam(required = false, defaultValue = "0") BigDecimal desconto,
                        @RequestParam(required = false, defaultValue = "0") BigDecimal comissaoPercentual,
                        @RequestParam(required = false) LocalDateTime dataVenda,
                        @RequestParam(required = false) List<Long> estoqueIds,
                        @RequestParam(required = false) List<BigDecimal> quantidades,
                        @RequestParam(required = false) List<BigDecimal> precosVenda,
                        RedirectAttributes attributes) {
        try {
            System.out.println("====== RECEBENDO VENDA ======");
            System.out.println("ClienteId: " + clienteId);
            System.out.println("FormaPagamento: " + formaPagamento);
            System.out.println("CondicaoPagamento: " + condicaoPagamento);
            System.out.println("Desconto: " + desconto);
            System.out.println("Comissão %: " + comissaoPercentual);
            System.out.println("EstoqueIds: " + estoqueIds);
            System.out.println("Quantidades: " + quantidades);
            System.out.println("PrecosVenda: " + precosVenda);
            
            // Validar cliente
            if (clienteId == null) {
                attributes.addFlashAttribute("erro", "Erro: Selecione um cliente!");
                return "redirect:/cadastrarVenda";
            }
            
            // Validar forma e condição de pagamento
            if (formaPagamento == null || formaPagamento.isEmpty()) {
                attributes.addFlashAttribute("erro", "Erro: Selecione a forma de pagamento!");
                return "redirect:/cadastrarVenda";
            }
            
            if (condicaoPagamento == null || condicaoPagamento.isEmpty()) {
                attributes.addFlashAttribute("erro", "Erro: Selecione a condição de pagamento!");
                return "redirect:/cadastrarVenda";
            }
            
            // Validar comissão
            if (comissaoPercentual == null || comissaoPercentual.compareTo(BigDecimal.ZERO) < 0 || comissaoPercentual.compareTo(new BigDecimal(100)) > 0) {
                attributes.addFlashAttribute("erro", "Erro: Comissão deve estar entre 0% e 100%!");
                return "redirect:/cadastrarVenda";
            }
            
            // Validar itens
            if (estoqueIds == null || estoqueIds.isEmpty() || 
                quantidades == null || quantidades.isEmpty()) {
                attributes.addFlashAttribute("erro", "Erro: Adicione pelo menos um item à venda!");
                return "redirect:/cadastrarVenda";
            }

            // Criar venda
            Venda venda = new Venda();
            venda.setDataVenda(dataVenda != null ? dataVenda : LocalDateTime.now());
            venda.setDesconto(desconto);
            venda.setComissaoPercentual(comissaoPercentual);
            venda.setFormaPagamento(FormaPagamento.valueOf(formaPagamento));
            venda.setCondicaoPagamento(CondicaoPagamento.valueOf(condicaoPagamento));

            // Buscar e setar cliente
            Cliente cliente = new Cliente();
            cliente.setId(clienteId);
            venda.setCliente(cliente);

            // Criar itens
            List<ItemVendas> itens = new ArrayList<>();
            for (int i = 0; i < estoqueIds.size(); i++) {
                Long estoqueId = estoqueIds.get(i);
                BigDecimal quantidade = quantidades.get(i);
                BigDecimal precoVenda = precosVenda.get(i);
                
                if (estoqueId != null && quantidade != null && quantidade.compareTo(BigDecimal.ZERO) > 0) {
                    ItemVendas itemVenda = new ItemVendas();
                    Estoque estoque = new Estoque();
                    estoque.setId(estoqueId);
                    itemVenda.setEstoque(estoque);
                    itemVenda.setQuantidade(quantidade);
                    itemVenda.setPrecoVenda(precoVenda);
                    itens.add(itemVenda);
                }
            }
            
            if (itens.isEmpty()) {
                attributes.addFlashAttribute("erro", "Erro: Adicione pelo menos um item válido à venda!");
                return "redirect:/cadastrarVenda";
            }

            venda.setItens(itens);

            vendaService.salvar(venda);
            attributes.addFlashAttribute("mensagem", "Venda salva com sucesso!");
            return "redirect:/vendaCadastradas";
        } catch (Exception e) {
            e.printStackTrace();
            attributes.addFlashAttribute("erro", "Erro ao salvar venda: " + e.getMessage());
            return "redirect:/cadastrarVenda";
        }
    }

    @GetMapping("/venda/{id}/detalhes")
    public String detalhes(@PathVariable Long id, Model model) {
        Optional<Venda> venda = vendaService.buscarPorId(id);
        if (venda.isPresent()) {
            model.addAttribute("venda", venda.get());
            return "venda/detalhes";
        }
        return "redirect:/vendaCadastradas";
    }

    @GetMapping("/venda/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Optional<Venda> vendaOpt = vendaService.buscarPorId(id);
        if (vendaOpt.isPresent()) {
            Venda venda = vendaOpt.get();
            List<ClienteDto> clientes = clienteService.listaCliente();
            List<EstoqueDto> estoques = estoqueService.listarEstoque();
            
            model.addAttribute("venda", venda);
            model.addAttribute("clientes", clientes);
            model.addAttribute("estoques", estoques);
            model.addAttribute("formasPagamento", FormaPagamento.values());
            model.addAttribute("condicoesPagamento", CondicaoPagamento.values());
            model.addAttribute("editando", true);
            
            return "cadastrarVenda";
        }
        return "redirect:/vendaCadastradas";
    }

    @GetMapping("/venda/{id}/deletar")
    public String deletar(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            vendaService.deletar(id);
            attributes.addFlashAttribute("mensagem", "Venda deletada com sucesso!");
        } catch (Exception e) {
            attributes.addFlashAttribute("erro", "Erro ao deletar venda: " + e.getMessage());
        }
        return "redirect:/vendaCadastradas";
    }
    @GetMapping("/listarComissao")
    public String listarComissoes(Model model) {
    List<Venda> vendas = vendaService.listar();
    model.addAttribute("vendas", vendas);
    return "listarComissao";
    }
    // Adicione este método completo no VendaController.java

@GetMapping("/listarFaturamento")
public String listarFaturamento(
        @RequestParam(required = false) String dataInicio,
        @RequestParam(required = false) String dataFim,
        Model model) {
    
    LocalDateTime inicio = null;
    LocalDateTime fim = null;
    
    // Parse das datas se fornecidas
    if (dataInicio != null && !dataInicio.isEmpty()) {
        inicio = LocalDateTime.parse(dataInicio + "T00:00:00");
    }
    if (dataFim != null && !dataFim.isEmpty()) {
        fim = LocalDateTime.parse(dataFim + "T23:59:59");
    }
    
    // Buscar vendas (filtradas ou todas)
    List<Venda> vendas;
    if (inicio != null && fim != null) {
        vendas = vendaService.listarPorPeriodo(inicio, fim);
    } else {
        vendas = vendaService.listar();
    }
    
    // Calcular totais com proteção contra null
    BigDecimal faturamentoTotal = BigDecimal.ZERO;
    BigDecimal comissaoTotal = BigDecimal.ZERO;
    
    for (Venda venda : vendas) {
        // Garantir que os valores não sejam nulos
        BigDecimal precoTotal = venda.getPrecoTotal() != null ? venda.getPrecoTotal() : BigDecimal.ZERO;
        BigDecimal comissaoPerc = venda.getComissaoPercentual() != null ? venda.getComissaoPercentual() : BigDecimal.ZERO;
        
        faturamentoTotal = faturamentoTotal.add(precoTotal);
        BigDecimal comissao = precoTotal.multiply(comissaoPerc).divide(new BigDecimal(100));
        comissaoTotal = comissaoTotal.add(comissao);
    }
    
    // Calcular faturamento mensal e anual
    LocalDateTime agora = LocalDateTime.now();
    LocalDateTime inicioMes = agora.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
    LocalDateTime inicioAno = agora.withDayOfYear(1).withHour(0).withMinute(0).withSecond(0);
    
    List<Venda> vendasMes = vendaService.listarPorPeriodo(inicioMes, agora);
    List<Venda> vendasAno = vendaService.listarPorPeriodo(inicioAno, agora);
    
    BigDecimal faturamentoMensal = BigDecimal.ZERO;
    BigDecimal faturamentoAnual = BigDecimal.ZERO;
    
    for (Venda venda : vendasMes) {
        BigDecimal precoTotal = venda.getPrecoTotal() != null ? venda.getPrecoTotal() : BigDecimal.ZERO;
        faturamentoMensal = faturamentoMensal.add(precoTotal);
    }
    
    for (Venda venda : vendasAno) {
        BigDecimal precoTotal = venda.getPrecoTotal() != null ? venda.getPrecoTotal() : BigDecimal.ZERO;
        faturamentoAnual = faturamentoAnual.add(precoTotal);
    }
    
    model.addAttribute("vendas", vendas);
    model.addAttribute("faturamentoTotal", faturamentoTotal);
    model.addAttribute("comissaoTotal", comissaoTotal);
    model.addAttribute("faturamentoMensal", faturamentoMensal);
    model.addAttribute("faturamentoAnual", faturamentoAnual);
    model.addAttribute("dataInicio", dataInicio);
    model.addAttribute("dataFim", dataFim);
    
    return "listarFaturamento";
}
}