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
import br.com.gestorx.api.Dto.VendaFormDto;
import br.com.gestorx.api.Dto.ItemVendaFormDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.beans.PropertyEditorSupport;
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
    public String salvar(@ModelAttribute VendaFormDto vendaFormDto, RedirectAttributes attributes) {
        try {
            // Validar itens
            if (vendaFormDto.getItens() == null || vendaFormDto.getItens().isEmpty()) {
                attributes.addFlashAttribute("erro", "Erro: Adicione pelo menos um item à venda!");
                return "redirect:/cadastrarVenda";
            }

            // Remover itens vazios
            List<ItemVendaFormDto> itensValidos = new ArrayList<>();
            for (ItemVendaFormDto item : vendaFormDto.getItens()) {
                if (item.getEstoqueId() != null && item.getQuantidade() != null && item.getQuantidade().signum() > 0) {
                    itensValidos.add(item);
                }
            }

            if (itensValidos.isEmpty()) {
                attributes.addFlashAttribute("erro", "Erro: Adicione pelo menos um item válido à venda!");
                return "redirect:/cadastrarVenda";
            }

            // Converter DTO para entidade
            Venda venda = new Venda();
            venda.setDataVenda(vendaFormDto.getDataVenda() != null ? vendaFormDto.getDataVenda() : LocalDateTime.now());
            venda.setDesconto(vendaFormDto.getDesconto() != null ? vendaFormDto.getDesconto() : java.math.BigDecimal.ZERO);
            venda.setFormaPagamento(FormaPagamento.valueOf(vendaFormDto.getFormaPagamento()));
            venda.setCondicaoPagamento(CondicaoPagamento.valueOf(vendaFormDto.getCondicaoPagamento()));

            // Buscar cliente
            Cliente cliente = new Cliente();
            cliente.setId(vendaFormDto.getClienteId());
            venda.setCliente(cliente);

            // Converter itens
            List<ItemVendas> itens = new ArrayList<>();
            for (ItemVendaFormDto itemDto : itensValidos) {
                ItemVendas itemVenda = new ItemVendas();
                itemVenda.setEstoque(new Estoque());
                itemVenda.getEstoque().setId(itemDto.getEstoqueId());
                itemVenda.setQuantidade(itemDto.getQuantidade());
                itemVenda.setPrecoVenda(itemDto.getPrecoVenda());
                itens.add(itemVenda);
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

    @PostMapping("/venda/{id}/editar")
    public String atualizar(@PathVariable Long id, VendaFormDto vendaFormDto, RedirectAttributes attributes) {
        try {
            vendaFormDto.setId(id);
            // Usar o mesmo fluxo do salvar
            return salvar(vendaFormDto, attributes);
        } catch (Exception e) {
            attributes.addFlashAttribute("erro", "Erro ao atualizar venda: " + e.getMessage());
            return "redirect:/venda/" + id + "/editar";
        }
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
