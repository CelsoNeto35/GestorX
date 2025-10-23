package br.com.gestorx.api.controller;

import br.com.gestorx.api.Dto.FornecedorDto;
import br.com.gestorx.api.Dto.EstoqueDto;
import br.com.gestorx.api.Dto.ProdutoDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import br.com.gestorx.api.service.ProdutoService;
import br.com.gestorx.api.service.EstoqueService;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("estoque")
public class EstoqueController {
    @Autowired
    private EstoqueService estoqueService;
    @Autowired
    private ProdutoService produtoService;
    
    // Lista todos os estoques cadastrados
    @GetMapping("/listar")
    public String listarEstoque(Model model) {
        List<EstoqueDto> estoques = estoqueService.listarEstoque();
        model.addAttribute("estoque", estoques);
        return "estoqueCadastrados";
    }
    
    // Exibe o formulário de cadastro com lista de produtos
    @GetMapping("/cadastrarEstoque")
    public String exibirFormularioCadastro(Model model) {
        EstoqueDto estoqueDto = new EstoqueDto();
        List<ProdutoDto> produtos = produtoService.listaProduto();
        model.addAttribute("estoque", estoqueDto);
        model.addAttribute("produtos", produtos);
        return "cadastrarEstoque";
    }
    
    // Salva o produto
    @PostMapping
    public String cadastrarEstoque(@ModelAttribute("estoque") EstoqueDto cadastro) {
        boolean sucesso = estoqueService.cadastrarEstoque(cadastro);
        if (sucesso) {
            return "redirect:/estoqueCadastrados";
        }
        return "redirect:/cadastrarEstoque?erro";
    }
    
    // Exclui produto
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirEstoque(@PathVariable Long id) {
        boolean sucesso = estoqueService.excluirEstoque(id);
        if (sucesso) {
            return ResponseEntity.ok("Estoque excluído com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao excluir estoque");
    }
    
    // Atualiza produto
    @PostMapping("/{id}")
    public String atualizarEstoque(@PathVariable Long id,
                                   @ModelAttribute("estoque") EstoqueDto atualizar) {
        boolean sucesso = estoqueService.atualizarEstoque(id, atualizar);
        if (sucesso) {
            return "redirect:/estoqueCadastrados";
        }
        return "redirect:/cadastrarEstoque?erro";
    }
    
    // Validar lote antes de adicionar entrada
    @PostMapping("/validar-lote")
    public ResponseEntity<Map<String, Object>> validarLote(@RequestBody Map<String, Object> dados) {
        Long estoqueId = Long.valueOf(dados.get("estoqueId").toString());
        String lote = dados.get("lote").toString();
        
        boolean loteValido = estoqueService.validarLote(estoqueId, lote);
        
        Map<String, Object> response = new HashMap<>();
        response.put("valido", loteValido);
        
        if (!loteValido) {
            response.put("mensagem", "O lote informado não corresponde ao lote cadastrado para este item.");
        }
        
        return ResponseEntity.ok(response);
    }
    
    // Adicionar entrada de estoque
    @PostMapping("/adicionar-entrada")
    public ResponseEntity<Map<String, Object>> adicionarEntrada(@RequestBody Map<String, Object> dados) {
        Long estoqueId = Long.valueOf(dados.get("estoqueId").toString());
        Integer quantidade = Integer.valueOf(dados.get("quantidade").toString());
        String lote = dados.get("lote").toString();
        
        boolean sucesso = estoqueService.adicionarEntradaEstoque(estoqueId, quantidade, lote);
        
        Map<String, Object> response = new HashMap<>();
        
        if (sucesso) {
            response.put("sucesso", true);
            response.put("mensagem", "Entrada de estoque realizada com sucesso!");
        } else {
            response.put("sucesso", false);
            response.put("mensagem", "Erro ao adicionar entrada. Verifique se o lote está correto.");
        }
        
        return ResponseEntity.ok(response);
    }
}