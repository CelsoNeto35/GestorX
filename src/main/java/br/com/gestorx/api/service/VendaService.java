// ==================== VENDA SERVICE ====================
package br.com.gestorx.api.service;

import br.com.gestorx.api.model.Venda;
import br.com.gestorx.api.model.ItemVendas;
import br.com.gestorx.api.model.Estoque;
import br.com.gestorx.api.model.Cliente;
import br.com.gestorx.api.repository.VendaRepository;
import br.com.gestorx.api.repository.EstoqueRepository;
import br.com.gestorx.api.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public Venda salvar(Venda venda) {
        try {
            // Validar e buscar cliente
            if (venda.getCliente() == null || venda.getCliente().getId() == null) {
                throw new RuntimeException("Cliente não selecionado");
            }
            
            Cliente cliente = clienteRepository.findById(venda.getCliente().getId())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
            venda.setCliente(cliente);

            // Validar comissão
            if (venda.getComissaoPercentual() == null) {
                venda.setComissaoPercentual(BigDecimal.ZERO);
            }
            
            if (venda.getComissaoPercentual().compareTo(BigDecimal.ZERO) < 0 || 
                venda.getComissaoPercentual().compareTo(new BigDecimal(100)) > 0) {
                throw new RuntimeException("Comissão deve estar entre 0% e 100%");
            }

            // Processar itens
            if (venda.getItens() != null && !venda.getItens().isEmpty()) {
                List<ItemVendas> itensProcessados = new ArrayList<>();
                
                for (ItemVendas item : venda.getItens()) {
                    // Buscar estoque pelo ID
                    if (item.getEstoque() == null || item.getEstoque().getId() == null) {
                        continue; // Pular itens sem estoque
                    }
                    
                    Estoque estoque = estoqueRepository.findById(item.getEstoque().getId())
                            .orElseThrow(() -> new RuntimeException("Estoque não encontrado: " + item.getEstoque().getId()));
                    
                    // Converter BigDecimal para Integer para comparar com estoque
                    int quantidadeVendida = item.getQuantidade().intValue();
                    
                    // Validar quantidade disponível
                    if (estoque.getQuantidadeDisponivel() < quantidadeVendida) {
                        throw new RuntimeException("Quantidade insuficiente em estoque para: " + estoque.getMarcaModelo() 
                            + ". Disponível: " + estoque.getQuantidadeDisponivel() + ", Solicitado: " + quantidadeVendida);
                    }
                    
                    // DAR BAIXA NO ESTOQUE
                    int novaQuantidade = estoque.getQuantidadeDisponivel() - quantidadeVendida;
                    estoque.setQuantidadeDisponivel(novaQuantidade);
                    estoqueRepository.save(estoque);
                    
                    // Atualizar item com dados do estoque
                    item.setEstoque(estoque);
                    if (item.getPrecoVenda() == null || item.getPrecoVenda().compareTo(BigDecimal.ZERO) == 0) {
                        item.setPrecoVenda(estoque.getPrecoDeVenda());
                    }
                    item.setVenda(venda);
                    
                    itensProcessados.add(item);
                }
                
                venda.setItens(itensProcessados);
            }

            // Validar dados obrigatórios
            if (venda.getId() == null) {
                venda.setDataVenda(LocalDateTime.now());
            }
            
            if (venda.getFormaPagamento() == null) {
                throw new RuntimeException("Forma de pagamento não selecionada");
            }
            
            if (venda.getCondicaoPagamento() == null) {
                throw new RuntimeException("Condição de pagamento não selecionada");
            }

            // Calcular totais
            calcularTotais(venda);
            
            return vendaRepository.save(venda);
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar venda: " + e.getMessage(), e);
        }
    }

    public void calcularTotais(Venda venda) {
        BigDecimal subtotal = BigDecimal.ZERO;
        
        if (venda.getItens() != null && !venda.getItens().isEmpty()) {
            for (ItemVendas item : venda.getItens()) {
                if (item.getQuantidade() != null && item.getPrecoVenda() != null) {
                    subtotal = subtotal.add(item.getSubtotal());
                }
            }
        }
        
        BigDecimal desconto = venda.getDesconto() != null ? venda.getDesconto() : BigDecimal.ZERO;
        
        // Garantir que desconto não seja maior que o subtotal
        if (desconto.compareTo(subtotal) > 0) {
            desconto = subtotal;
        }
        
        BigDecimal total = subtotal.subtract(desconto);
        
        // Garantir que o total nunca seja negativo
        if (total.compareTo(BigDecimal.ZERO) < 0) {
            total = BigDecimal.ZERO;
        }
        
        venda.setPrecoTotal(total);
        
        // Garantir que a comissão esteja definida
        if (venda.getComissaoPercentual() == null) {
            venda.setComissaoPercentual(BigDecimal.ZERO);
        }
    }

    public Venda atualizar(Long id, Venda vendaAtualizada) {
        Optional<Venda> vendaExistente = vendaRepository.findById(id);
        if (!vendaExistente.isPresent()) {
            throw new RuntimeException("Venda não encontrada");
        }
        
        Venda venda = vendaExistente.get();
        
        // PASSO 1: REVERTER O ESTOQUE DOS ITENS ANTIGOS
        if (venda.getItens() != null && !venda.getItens().isEmpty()) {
            for (ItemVendas itemAntigo : venda.getItens()) {
                Estoque estoque = itemAntigo.getEstoque();
                if (estoque != null) {
                    // Devolver a quantidade ao estoque
                    int quantidadeDevolvida = itemAntigo.getQuantidade().intValue();
                    int novaQuantidade = estoque.getQuantidadeDisponivel() + quantidadeDevolvida;
                    estoque.setQuantidadeDisponivel(novaQuantidade);
                    estoqueRepository.save(estoque);
                }
            }
        }
        
        // PASSO 2: Atualizar dados básicos
        if (vendaAtualizada.getCliente() != null && vendaAtualizada.getCliente().getId() != null) {
            Cliente cliente = clienteRepository.findById(vendaAtualizada.getCliente().getId())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
            venda.setCliente(cliente);
        }
        
        venda.setFormaPagamento(vendaAtualizada.getFormaPagamento());
        venda.setCondicaoPagamento(vendaAtualizada.getCondicaoPagamento());
        venda.setDesconto(vendaAtualizada.getDesconto());
        venda.setComissaoPercentual(vendaAtualizada.getComissaoPercentual());
        
        // PASSO 3: Processar NOVOS itens e dar baixa no estoque
        if (vendaAtualizada.getItens() != null && !vendaAtualizada.getItens().isEmpty()) {
            List<ItemVendas> itensProcessados = new ArrayList<>();
            
            for (ItemVendas novoItem : vendaAtualizada.getItens()) {
                if (novoItem.getEstoque() == null || novoItem.getEstoque().getId() == null) {
                    continue;
                }
                
                Estoque estoque = estoqueRepository.findById(novoItem.getEstoque().getId())
                        .orElseThrow(() -> new RuntimeException("Estoque não encontrado: " + novoItem.getEstoque().getId()));
                
                int quantidadeVendida = novoItem.getQuantidade().intValue();
                
                // Validar quantidade disponível
                if (estoque.getQuantidadeDisponivel() < quantidadeVendida) {
                    throw new RuntimeException("Quantidade insuficiente em estoque para: " + estoque.getMarcaModelo() 
                        + ". Disponível: " + estoque.getQuantidadeDisponivel() + ", Solicitado: " + quantidadeVendida);
                }
                
                // DAR BAIXA NO ESTOQUE novamente
                int novaQuantidade = estoque.getQuantidadeDisponivel() - quantidadeVendida;
                estoque.setQuantidadeDisponivel(novaQuantidade);
                estoqueRepository.save(estoque);
                
                // Configurar item
                novoItem.setEstoque(estoque);
                if (novoItem.getPrecoVenda() == null || novoItem.getPrecoVenda().compareTo(BigDecimal.ZERO) == 0) {
                    novoItem.setPrecoVenda(estoque.getPrecoDeVenda());
                }
                novoItem.setVenda(venda);
                
                itensProcessados.add(novoItem);
            }
            
            // Limpar itens antigos e adicionar novos
            venda.getItens().clear();
            venda.setItens(itensProcessados);
        }
        
        calcularTotais(venda);
        return vendaRepository.save(venda);
    }

    public Optional<Venda> buscarPorId(Long id) {
        return vendaRepository.findById(id);
    }

    public List<Venda> listar() {
        return vendaRepository.findAllAtivos();
    }

    public List<Venda> buscarPorCliente(Long clienteId) {
        return vendaRepository.findByClienteId(clienteId);
    }

    public void deletar(Long id) {
        Optional<Venda> vendaOpt = vendaRepository.findById(id);
        if (vendaOpt.isPresent()) {
            Venda venda = vendaOpt.get();
            
            // REVERTER O ESTOQUE ao deletar/cancelar venda
            if (venda.getItens() != null) {
                for (ItemVendas item : venda.getItens()) {
                    Estoque estoque = item.getEstoque();
                    if (estoque != null) {
                        // Devolver a quantidade ao estoque
                        int quantidadeDevolvida = item.getQuantidade().intValue();
                        int novaQuantidade = estoque.getQuantidadeDisponivel() + quantidadeDevolvida;
                        estoque.setQuantidadeDisponivel(novaQuantidade);
                        estoqueRepository.save(estoque);
                    }
                }
            }
            
            venda.setAtivo(false);
            vendaRepository.save(venda);
        }
    }
}