package br.com.gestorx.api.controller;

import br.com.gestorx.api.Dto.ProdutoDto;
import br.com.gestorx.api.Dto.FornecedorDto;
import br.com.gestorx.api.service.ProdutoService;
import br.com.gestorx.api.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private FornecedorService fornecedorService;

    //  Exibe o formulário de cadastro com lista de fornecedores
    @GetMapping("/cadastrarProduto")
    public String exibirFormularioCadastro(Model model) {
        ProdutoDto produtoDto = new ProdutoDto();
        List<FornecedorDto> fornecedores = fornecedorService.listaFornecedor();

        model.addAttribute("produto", produtoDto);
        model.addAttribute("fornecedores", fornecedores);

        return "cadastrarProduto";
    }

    // Salva o produto
    @PostMapping
    public String cadastrarProduto(@ModelAttribute("produto") ProdutoDto cadastro) {
        boolean sucesso = produtoService.cadastrarProduto(cadastro);
        if (sucesso) {
            return "redirect:/produtoCadastrados";
        }
        return "redirect:/cadastrarProduto?erro";
    }

    // Exclui produto
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluirProduto(@PathVariable Long id) {
        boolean sucesso = produtoService.excluirProduto(id);
        if (sucesso) {
            return ResponseEntity.ok("Produto excluído com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro ao excluir produto");
    }

    //  Atualiza produto
    @PostMapping("/{id}")
    public String atualizarProduto(@PathVariable Long id,
                                   @ModelAttribute("produto") ProdutoDto atualizar) {
        boolean sucesso = produtoService.atualizarProduto(id, atualizar);

        if (sucesso) {
            return "redirect:/produtoCadastrados";
        }
        return "redirect:/cadastrarProduto?erro";
    }
}
