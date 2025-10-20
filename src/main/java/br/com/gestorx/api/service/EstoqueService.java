package br.com.gestorx.api.service;

import br.com.gestorx.api.model.Estoque;
import br.com.gestorx.api.model.Produto;
import br.com.gestorx.api.repository.EstoqueRepository;
import br.com.gestorx.api.repository.ProdutoRepository;
import br.com.gestorx.api.Dto.EstoqueDto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstoqueService {

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private ProdutoRepository produtoRepository;


    public List<EstoqueDto> listarEstoque() {
        List<Estoque> estoques = estoqueRepository.findAll();
        List<EstoqueDto> estoqueDtos = new ArrayList<>();

        for (Estoque estoque : estoques) {
            EstoqueDto dto = new EstoqueDto();
            dto.setId(estoque.getId());
            dto.setCategoria(estoque.getCategoria());
            dto.setMarcaModelo(estoque.getMarcaModelo());
            dto.setQuantidadeDisponivel(estoque.getQuantidadeDisponivel());
            dto.setLote(estoque.getLote());
            dto.setValidade(estoque.getValidade());
            dto.setPrecoDeCusto(estoque.getPrecoDeCusto());
            dto.setPrecoDeVenda(estoque.getPrecoDeVenda());
            dto.setMargemDeLucro(estoque.getMargemDeLucro());

            // Adiciona informações do produto vinculado
            if (estoque.getProduto() != null) {
                dto.setProdutoId(estoque.getProduto().getId());
                dto.setProdutoDescricao(estoque.getProduto().getDescricao());
            }

            estoqueDtos.add(dto);
        }

        return estoqueDtos;
    }

    // ============ CADASTRAR ============
    public boolean cadastrarEstoque(EstoqueDto cadastro) {
        // Exemplo de regra: não permitir duplicar o mesmo lote
        if (estoqueRepository.findByLote(cadastro.getLote()).isPresent()) {
            return false;
        }

        Estoque estoque = new Estoque();
        estoque.setCategoria(cadastro.getCategoria());
        estoque.setMarcaModelo(cadastro.getMarcaModelo());
        estoque.setQuantidadeDisponivel(cadastro.getQuantidadeDisponivel());
        estoque.setLote(cadastro.getLote());
        estoque.setValidade(cadastro.getValidade());
        estoque.setPrecoDeCusto(cadastro.getPrecoDeCusto());
        estoque.setPrecoDeVenda(cadastro.getPrecoDeVenda());
        estoque.setMargemDeLucro(cadastro.getMargemDeLucro());
        
        // Calcula a margem de lucro se não foi informada
        if (estoque.getMargemDeLucro() == null || estoque.getMargemDeLucro().compareTo(BigDecimal.ZERO) == 0) {
            estoque.setMargemDeLucro(calcularMargemDeLucro(estoque.getPrecoDeCusto(), estoque.getPrecoDeVenda()));
        }

        System.out.println("cadastrarEstoque");

        // Vincula o produto selecionado
        if (cadastro.getProdutoId() != null) {
            Optional<Produto> produtoOpt = produtoRepository.findById(cadastro.getProdutoId());
            produtoOpt.ifPresent(estoque::setProduto);
        }

        estoqueRepository.save(estoque);
        return true;
    }

    // ============ EXCLUIR ============
    public boolean excluirEstoque(Long id) {
        return estoqueRepository.findById(id)
                .map(estoque -> {
                    estoqueRepository.delete(estoque);
                    return true;
                }).orElse(false);
    }

    // ============ ATUALIZAR ============
    public boolean atualizarEstoque(Long id, EstoqueDto dados) {
        Optional<Estoque> optionalEstoque = estoqueRepository.findById(id);
        if (!optionalEstoque.isPresent()) {
            return false;
        }

        Estoque estoque = optionalEstoque.get();
        estoque.setCategoria(dados.getCategoria());
        estoque.setMarcaModelo(dados.getMarcaModelo());
        estoque.setQuantidadeDisponivel(dados.getQuantidadeDisponivel());
        estoque.setLote(dados.getLote());
        estoque.setValidade(dados.getValidade());
        estoque.setPrecoDeCusto(dados.getPrecoDeCusto());
        estoque.setPrecoDeVenda(dados.getPrecoDeVenda());
        estoque.setMargemDeLucro(dados.getMargemDeLucro());

        // Atualiza o produto vinculado
        if (dados.getProdutoId() != null) {
            produtoRepository.findById(dados.getProdutoId())
                    .ifPresent(estoque::setProduto);
        }

        estoqueRepository.save(estoque);
        return true;
    }

    // ============ CALCULAR MARGEM DE LUCRO ============
    private BigDecimal calcularMargemDeLucro(BigDecimal precoDeCusto, BigDecimal precoDeVenda) {
        if (precoDeCusto == null || precoDeCusto.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        
        BigDecimal lucro = precoDeVenda.subtract(precoDeCusto);
        return lucro.divide(precoDeCusto, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
    }
}