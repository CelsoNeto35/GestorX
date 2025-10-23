package br.com.gestorx.api.service;

import br.com.gestorx.api.model.Fornecedor;
import br.com.gestorx.api.model.Produto;
import br.com.gestorx.api.repository.ProdutoRepository;
import br.com.gestorx.api.repository.FornecedorRepository;
import br.com.gestorx.api.Dto.ProdutoDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public List<ProdutoDto> listaProduto() {
        List<Produto> produtos = produtoRepository.findAll();
        List<ProdutoDto> produtoDtos = new ArrayList<>();

        for (Produto produto : produtos) {
            ProdutoDto dto = new ProdutoDto();
            dto.setId(produto.getId());
            dto.setDescricao(produto.getDescricao());
            dto.setCategoria(produto.getCategoria());
            dto.setUnidadeDeMedida(produto.getUnidadeDeMedida());
            if (produto.getFornecedor() != null) {
                dto.setFornecedorId(produto.getFornecedor().getId());
                dto.setFornecedorNomeFantasia(produto.getFornecedor().getNomeFantasia());
            }
            produtoDtos.add(dto);
        }

        return produtoDtos;
    }

    public boolean cadastrarProduto(ProdutoDto cadastro) {
        if (produtoRepository.findByDescricao(cadastro.getDescricao()).isPresent()) {
            return false;
        }

        Produto produto = new Produto();
        produto.setDescricao(cadastro.getDescricao());
        produto.setCategoria(cadastro.getCategoria());
        produto.setUnidadeDeMedida(cadastro.getUnidadeDeMedida());

        // Vincula o fornecedor selecionado
        if (cadastro.getFornecedorId() != null) {
            Optional<Fornecedor> fornecedorOpt = fornecedorRepository.findById(cadastro.getFornecedorId());
            fornecedorOpt.ifPresent(produto::setFornecedor);
        }

        produtoRepository.save(produto);
        return true;
    }

  public boolean excluirProduto(Long id) {
    Optional<Produto> optionalProduto = produtoRepository.findById(id);

    if (!optionalProduto.isPresent()) {
        return false;
    }

    produtoRepository.delete(optionalProduto.get());
    return true;
}


    public ProdutoDto editarProduto(Long id) {

        Optional<Produto> optionalProduto = produtoRepository.findById(id);

        ProdutoDto produtoDto = new ProdutoDto();

        if (!optionalProduto.isPresent()) {
            produtoDto.setId(0L);
            return produtoDto;
        }

        Produto produto = optionalProduto.get();

        produtoDto.setId(produto.getId());
        produtoDto.setDescricao(produto.getDescricao());
        produtoDto.setCategoria(produto.getCategoria());
        produtoDto.setUnidadeDeMedida(produto.getUnidadeDeMedida());
        if (produto.getFornecedor() != null) {
            produtoDto.setFornecedorId(produto.getFornecedor().getId());
            produtoDto.setFornecedorNomeFantasia(produto.getFornecedor().getNomeFantasia());
        }

        return produtoDto;
    }

    public boolean atualizarProduto(Long id, ProdutoDto dados) {
        Optional<Produto> optionalProduto = produtoRepository.findById(id);
        if (!optionalProduto.isPresent()) {
            return false;
        }

        Produto produto = optionalProduto.get();
        produto.setDescricao(dados.getDescricao());
        produto.setCategoria(dados.getCategoria());
        produto.setUnidadeDeMedida(dados.getUnidadeDeMedida());

        if (dados.getFornecedorId() != null) {
            fornecedorRepository.findById(dados.getFornecedorId())
                    .ifPresent(produto::setFornecedor);
        }

        produtoRepository.save(produto);
        return true;
    }
}
