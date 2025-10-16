package br.com.gestorx.api.service;

import br.com.gestorx.api.Dto.VendaDto;
import br.com.gestorx.api.Dto.ItemVendasDto;
import br.com.gestorx.api.model.*;
import br.com.gestorx.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ItemVendasRepository itemVendasRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<VendaDto> listarVendas() {
        return vendaRepository.findAll().stream().map(venda -> {
            VendaDto dto = new VendaDto();
            dto.setId(venda.getId());
            dto.setDataDaVenda(venda.getDataDaVenda());
            dto.setHoraDaVenda(venda.getHoraDaVenda());
            dto.setPrecoTotal(venda.getPrecoTotal());
            dto.setDescontoAplicado(venda.getDescontoAplicado());
            dto.setFormaDePagamento(venda.getFormaDePagamento());
            dto.setCondicaoDePagamento(venda.getCondicaoDePagamento());

            if (venda.getCliente() != null) {
                dto.setClienteId(venda.getCliente().getId());
                dto.setClienteNome(venda.getCliente().getNome());
            }

            if (venda.getItens() != null) {
                dto.setItens(
                        venda.getItens().stream().map(item -> {
                            ItemVendasDto i = new ItemVendasDto();
                            i.setId(item.getId());
                            i.setEstoqueId(item.getEstoque().getId());
                            i.setProdutoDescricao(item.getEstoque().getProduto().getDescricao());
                            i.setQuantidade(item.getQuantidade());
                            i.setPrecoUnitario(item.getPrecoUnitario());
                            i.setSubtotal(item.getSubtotal());
                            i.setLote(item.getEstoque().getLote());
                            return i;
                        }).collect(Collectors.toList())
                );
            }

            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public boolean cadastrarVenda(VendaDto dados) {
        try {
            Venda venda = new Venda();
            venda.setDataDaVenda(dados.getDataDaVenda());
            venda.setHoraDaVenda(dados.getHoraDaVenda());
            venda.setDescontoAplicado(dados.getDescontoAplicado());
            venda.setFormaDePagamento(dados.getFormaDePagamento());
            venda.setCondicaoDePagamento(dados.getCondicaoDePagamento());

            // Associa o cliente
            if (dados.getClienteId() != null) {
                clienteRepository.findById(dados.getClienteId())
                        .ifPresent(venda::setCliente);
            }

            List<ItemVendas> itens = new ArrayList<>();
            BigDecimal total = BigDecimal.ZERO;

            // ========== PROCESSAR ITENS ==========
            for (ItemVendasDto itemDto : dados.getItens()) {
                Optional<Estoque> estoqueOpt = estoqueRepository.findById(itemDto.getEstoqueId());
                if (estoqueOpt.isEmpty()) continue;

                Estoque estoque = estoqueOpt.get();
                BigDecimal qtdVendida = itemDto.getQuantidade();

                // Verifica estoque disponível
                if (estoque.getQuantidadeDisponivel().compareTo(qtdVendida) < 0) {
                    throw new IllegalArgumentException(
                            "Quantidade insuficiente no estoque para o produto: " +
                                    estoque.getProduto().getDescricao()
                    );
                }

                // Cria item da venda
                ItemVendas item = new ItemVendas();
                item.setVenda(venda);
                item.setEstoque(estoque);
                item.setQuantidade(qtdVendida);
                item.setPrecoUnitario(estoque.getPrecoDeVenda());
                itens.add(item);

                // Calcula subtotal e soma ao total
                total = total.add(item.getSubtotal());

                // 🔻 DÁ BAIXA NO ESTOQUE
                BigDecimal novaQtd = estoque.getQuantidadeDisponivel().subtract(qtdVendida);
                estoque.setQuantidadeDisponivel(novaQtd);
                estoqueRepository.save(estoque);
            }

            venda.setItens(itens);
            venda.setPrecoTotal(total);

            vendaRepository.save(venda);
            return true;

        } catch (Exception e) {
            System.err.println("❌ Erro ao cadastrar venda: " + e.getMessage());
            throw e;
        }
    }

    public List<Map<String, Object>> autocompleteProdutos() {
        return estoqueRepository.findAll().stream().map(estoque -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", estoque.getId());
            map.put("produtoDescricao", estoque.getProduto().getDescricao());
            map.put("precoVenda", estoque.getPrecoDeVenda());
            map.put("quantidadeDisponivel", estoque.getQuantidadeDisponivel());
            map.put("lote", estoque.getLote()); // já estava puxando o lote ✅
            return map;
        }).collect(Collectors.toList());
    }
    public List<Map<String, Object>> listarClientes() {
        return clienteRepository.findAll().stream().map(cliente -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", cliente.getId());
            map.put("nome", cliente.getNome());
            map.put("cpfCnpj", cliente.getCpfCnpj());
            map.put("telefone", cliente.getTelefone());
            return map;
        }).collect(Collectors.toList());
    }

    public boolean excluirVenda(Long id) {
        return vendaRepository.findById(id)
                .map(venda -> {
                    // Reverte o estoque antes de excluir
                    if (venda.getItens() != null) {
                        for (ItemVendas item : venda.getItens()) {
                            Estoque est = item.getEstoque();
                            est.setQuantidadeDisponivel(
                                    est.getQuantidadeDisponivel().add(item.getQuantidade())
                            );
                            estoqueRepository.save(est);
                        }
                    }
                    vendaRepository.delete(venda);
                    return true;
                }).orElse(false);
    }
}
