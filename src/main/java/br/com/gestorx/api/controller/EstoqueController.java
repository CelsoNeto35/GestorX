/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.gestorx.api.controller;

import br.com.gestorx.api.Dto.FornecedorDto;
import br.com.gestorx.api.Dto.EstoqueDto;
import br.com.gestorx.api.Dto.ProdutoDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import br.com.gestorx.api.service.ProdutoService;
import br.com.gestorx.api.service.EstoqueService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author cneto
 */
@Controller
@RequestMapping("estoque")
public class EstoqueController {
    @Autowired
    private EstoqueService estoqueService;

    @Autowired
    private ProdutoService produtoService;
    
     //  Exibe o formulário de cadastro com lista de fornecedores
    @GetMapping("/cadastrarEstoque")
    public String exibirFormularioCadastro(Model model) {
        EstoqueDto estoqueDto = new EstoqueDto();
        List<ProdutoDto> produtos = produtoService.listaProduto();

        model.addAttribute("estoque",estoqueDto);
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

    //  Atualiza produto
    @PostMapping("/{id}")
    public String atualizarEstoque(@PathVariable Long id,
                                   @ModelAttribute("estoque") EstoqueDto atualizar) {
        boolean sucesso = estoqueService.atualizarEstoque(id, atualizar);

        if (sucesso) {
            return "redirect:/estoqueCadastrados";
        }
        return "redirect:/cadastrarEstoque?erro";
    }
    
}
