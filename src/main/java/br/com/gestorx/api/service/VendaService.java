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
            System.out.println(" Iniciando salvamento de venda...");
            
            // Validar e buscar cliente
            if (venda.getCliente() == null || venda.getCliente().getId() == null) {
                throw new RuntimeException("Cliente não selecionado");
            }
            
            Cliente cliente = clienteRepository.findById(venda.getCliente().getId())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
            venda.setCliente(cliente);

            // Validar e inicializar comissão
            if (venda.getComissaoPercentual() == null) {
                venda.setComissaoPercentual(BigDecimal.ZERO);
            }
            
            if (venda.getComissaoPercentual().compareTo(BigDecimal.ZERO) < 0 || 
                venda.getComissaoPercentual().compareTo(new BigDecimal(100)) > 0) {
                throw new RuntimeException("Comissão deve estar entre 0% e 100%");
            }

            // Inicializar desconto se null
            if (venda.getDesconto() == null) {
                venda.setDesconto(BigDecimal.ZERO);
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
                    
                    System.out.println(" Baixa no estoque: " + estoque.getMarcaModelo() + " | Qtd vendida: " + quantidadeVendida);
                    
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
            
            Venda vendaSalva = vendaRepository.save(venda);
            System.out.println(" Venda salva com sucesso - ID: " + vendaSalva.getId());
            
            return vendaSalva;
            
        } catch (Exception e) {
            System.err.println(" Erro ao salvar venda: " + e.getMessage());
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
        
        System.out.println(" Totais calculados - Subtotal: R$ " + subtotal + " | Desconto: R$ " + desconto + " | Total: R$ " + total);
    }

    public Venda atualizar(Long id, Venda vendaAtualizada) {
        // Buscar venda com itens carregados usando JOIN FETCH
        Optional<Venda> vendaExistente = vendaRepository.findByIdWithItens(id);
        if (!vendaExistente.isPresent()) {
            throw new RuntimeException("Venda não encontrada");
        }
        
        Venda venda = vendaExistente.get();
        
        System.out.println("️ Atualizando venda ID: " + id);
        
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
                    
                    System.out.println("↩️ Revertendo estoque: " + estoque.getMarcaModelo() + " | Qtd: " + quantidadeDevolvida);
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
        venda.setDesconto(vendaAtualizada.getDesconto() != null ? vendaAtualizada.getDesconto() : BigDecimal.ZERO);
        venda.setComissaoPercentual(vendaAtualizada.getComissaoPercentual() != null ? 
                                    vendaAtualizada.getComissaoPercentual() : BigDecimal.ZERO);
        
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
                
                System.out.println(" Nova baixa: " + estoque.getMarcaModelo() + " | Qtd: " + quantidadeVendida);
                
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
        Venda vendaSalva = vendaRepository.save(venda);
        System.out.println(" Venda atualizada com sucesso");
        
        return vendaSalva;
    }

    /**
     * Busca venda por ID com todos os itens carregados
     * CORRIGIDO: Agora usa findByIdWithItens para evitar LazyInitializationException
     */
    public Optional<Venda> buscarPorId(Long id) {
        try {
            Optional<Venda> vendaOpt = vendaRepository.findByIdWithItens(id);
            if (vendaOpt.isPresent()) {
                Venda venda = vendaOpt.get();
                
                // Garantir inicialização de campos que podem ser null
                if (venda.getComissaoPercentual() == null) {
                    venda.setComissaoPercentual(BigDecimal.ZERO);
                }
                if (venda.getDesconto() == null) {
                    venda.setDesconto(BigDecimal.ZERO);
                }
                
                // Forçar inicialização da coleção de itens (garantia extra)
                if (venda.getItens() != null) {
                    venda.getItens().size();
                }
                
                System.out.println("Venda carregada com sucesso - ID: " + venda.getId() + 
                                 " | Itens: " + (venda.getItens() != null ? venda.getItens().size() : 0));
            }
            return vendaOpt;
        } catch (Exception e) {
            System.err.println("Erro ao buscar venda por ID: " + e.getMessage());
            e.printStackTrace();
            // Fallback para busca simples (sem itens)
            return vendaRepository.findById(id);
        }
    }

    /**
     * Lista todas as vendas ativas com itens carregados
     * CORRIGIDO: Usa findAllWithItens para evitar N+1 queries
     */
    public List<Venda> listar() {
        try {
            List<Venda> vendas = vendaRepository.findAllWithItens();
            
            // Garantir que todos os campos estejam inicializados
            for (Venda venda : vendas) {
                if (venda.getComissaoPercentual() == null) {
                    venda.setComissaoPercentual(BigDecimal.ZERO);
                }
                if (venda.getDesconto() == null) {
                    venda.setDesconto(BigDecimal.ZERO);
                }
            }
            
            System.out.println("" + vendas.size() + " vendas carregadas com itens");
            return vendas;
        } catch (Exception e) {
            System.err.println("Erro ao listar vendas com itens: " + e.getMessage());
            e.printStackTrace();
            // Fallback para busca simples
            return vendaRepository.findAllAtivos();
        }
    }

    public List<Venda> buscarPorCliente(Long clienteId) {
        return vendaRepository.findByClienteId(clienteId);
    }

    /**
     * Lista vendas por período com itens carregados
     * CORRIGIDO: Usa findByPeriodoWithItens
     */
    public List<Venda> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        try {
            List<Venda> vendas = vendaRepository.findByPeriodoWithItens(inicio, fim);
            
            // Garantir inicialização
            for (Venda venda : vendas) {
                if (venda.getComissaoPercentual() == null) {
                    venda.setComissaoPercentual(BigDecimal.ZERO);
                }
                if (venda.getDesconto() == null) {
                    venda.setDesconto(BigDecimal.ZERO);
                }
            }
            
            return vendas;
        } catch (Exception e) {
            System.err.println("Erro ao listar vendas por período: " + e.getMessage());
            e.printStackTrace();
            // Fallback
            return vendaRepository.findByDataVendaBetweenAndAtivoTrue(inicio, fim);
        }
    }

    /**
     * Deleta venda e reverte estoque
     * CORRIGIDO: Usa findByIdWithItens para garantir que os itens estejam carregados
     */
    public void deletar(Long id) {
        Optional<Venda> vendaOpt = vendaRepository.findByIdWithItens(id);
        if (vendaOpt.isPresent()) {
            Venda venda = vendaOpt.get();
            
            System.out.println("️ Deletando venda ID: " + id);
            
            // REVERTER O ESTOQUE ao deletar/cancelar venda
            if (venda.getItens() != null && !venda.getItens().isEmpty()) {
                for (ItemVendas item : venda.getItens()) {
                    Estoque estoque = item.getEstoque();
                    if (estoque != null) {
                        // Devolver a quantidade ao estoque
                        int quantidadeDevolvida = item.getQuantidade().intValue();
                        int novaQuantidade = estoque.getQuantidadeDisponivel() + quantidadeDevolvida;
                        estoque.setQuantidadeDisponivel(novaQuantidade);
                        estoqueRepository.save(estoque);
                        
                        System.out.println("Estoque revertido: " + estoque.getMarcaModelo() + 
                                         " | Quantidade devolvida: " + quantidadeDevolvida);
                    }
                }
            }
            
            venda.setAtivo(false);
            vendaRepository.save(venda);
            System.out.println("Venda deletada com sucesso");
        } else {
            throw new RuntimeException("Venda não encontrada com ID: " + id);
        }
    }
}