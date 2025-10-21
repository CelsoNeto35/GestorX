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
        Optional<Venda> venda = vendaService.buscarPorId(id);
        if (venda.isPresent()) {
            List<ClienteDto> clientes = clienteService.listaCliente();
            List<EstoqueDto> estoques = estoqueService.listarEstoque();
            
            model.addAttribute("venda", venda.get());
            model.addAttribute("clientes", clientes);
            model.addAttribute("estoques", estoques);
            model.addAttribute("formasPagamento", FormaPagamento.values());
            model.addAttribute("condicoesPagamento", CondicaoPagamento.values());
            
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
}