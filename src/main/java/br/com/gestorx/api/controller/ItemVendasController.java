package br.com.gestorx.api.controller;

import br.com.gestorx.api.model.ItemVendas;
import br.com.gestorx.api.model.Venda;
import br.com.gestorx.api.service.ItemVendasService;
import br.com.gestorx.api.service.VendaService;
import br.com.gestorx.api.service.EstoqueService;
import br.com.gestorx.api.Dto.EstoqueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/item-vendas")
public class ItemVendasController {

    @Autowired
    private ItemVendasService itemVendasService;

    @Autowired
    private VendaService vendaService;

    @Autowired
    private EstoqueService estoqueService;

    @GetMapping("/venda/{vendaId}")
    public String listarPorVenda(@PathVariable Long vendaId, Model model) {
        List<ItemVendas> itens = itemVendasService.buscarPorVenda(vendaId);
        Optional<Venda> venda = vendaService.buscarPorId(vendaId);
        
        if (venda.isPresent()) {
            model.addAttribute("itens", itens);
            model.addAttribute("venda", venda.get());
            return "item-venda/lista";
        }
        return "redirect:/vendaCadastradas";
    }

    @GetMapping("/novo/{vendaId}")
    public String novo(@PathVariable Long vendaId, Model model) {
        ItemVendas itemVenda = new ItemVendas();
        Optional<Venda> venda = vendaService.buscarPorId(vendaId);
        
        if (venda.isPresent()) {
            List<EstoqueDto> estoques = estoqueService.listarEstoque();
            model.addAttribute("itemVenda", itemVenda);
            model.addAttribute("venda", venda.get());
            model.addAttribute("estoques", estoques);
            return "item-venda/formulario";
        }
        return "redirect:/vendaCadastradas";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Optional<ItemVendas> itemVenda = itemVendasService.buscarPorId(id);
        if (itemVenda.isPresent()) {
            List<EstoqueDto> estoques = estoqueService.listarEstoque();
            model.addAttribute("itemVenda", itemVenda.get());
            model.addAttribute("estoques", estoques);
            return "item-venda/formulario";
        }
        return "redirect:/vendaCadastradas";
    }

    @PostMapping("/{vendaId}")
    public String salvar(@PathVariable Long vendaId, ItemVendas itemVenda, RedirectAttributes attributes) {
        try {
            Optional<Venda> vendaOpt = vendaService.buscarPorId(vendaId);
            if (vendaOpt.isPresent()) {
                itemVenda.setVenda(vendaOpt.get());
                itemVendasService.salvar(itemVenda);
                attributes.addFlashAttribute("mensagem", "Item adicionado com sucesso!");
                return "redirect:/item-vendas/venda/" + vendaId;
            }
            return "redirect:/vendaCadastradas";
        } catch (Exception e) {
            attributes.addFlashAttribute("erro", "Erro ao adicionar item: " + e.getMessage());
            return "redirect:/item-vendas/novo/" + vendaId;
        }
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, ItemVendas itemVenda, RedirectAttributes attributes) {
        try {
            ItemVendas itemAtualizado = itemVendasService.atualizar(id, itemVenda);
            Long vendaId = itemAtualizado.getVenda().getId();
            attributes.addFlashAttribute("mensagem", "Item atualizado com sucesso!");
            return "redirect:/item-vendas/venda/" + vendaId;
        } catch (Exception e) {
            attributes.addFlashAttribute("erro", "Erro ao atualizar item: " + e.getMessage());
            return "redirect:/item-vendas/" + id + "/editar";
        }
    }

    @DeleteMapping("/{id}")
    public String deletar(@PathVariable Long id, RedirectAttributes attributes) {
        try {
            Optional<ItemVendas> item = itemVendasService.buscarPorId(id);
            if (item.isPresent()) {
                Long vendaId = item.get().getVenda().getId();
                itemVendasService.deletar(id);
                attributes.addFlashAttribute("mensagem", "Item removido com sucesso!");
                return "redirect:/item-vendas/venda/" + vendaId;
            }
        } catch (Exception e) {
            attributes.addFlashAttribute("erro", "Erro ao deletar item: " + e.getMessage());
        }
        return "redirect:/vendaCadastradas";
    }
}