
package br.com.gestorx.api.service;

import br.com.gestorx.api.model.ItemVendas;
import br.com.gestorx.api.model.Estoque;
import br.com.gestorx.api.repository.ItemVendasRepository;
import br.com.gestorx.api.repository.EstoqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ItemVendasService {

    @Autowired
    private ItemVendasRepository itemVendaRepository;

    @Autowired
    private EstoqueRepository estoqueRepository;

    public ItemVendas salvar(ItemVendas itemVenda) {
        if (itemVenda.getEstoque() != null) {
            Estoque estoque = estoqueRepository.findById(itemVenda.getEstoque().getId())
                    .orElseThrow(() -> new RuntimeException("Estoque não encontrado"));
            
            if (estoque.getQuantidadeDisponivel().compareTo(itemVenda.getQuantidade()) < 0) {
                    throw new RuntimeException("Quantidade em estoque insuficiente");
                }
            itemVenda.setPrecoVenda(estoque.getPrecoDeVenda());
        }
        return itemVendaRepository.save(itemVenda);
    }

    public ItemVendas atualizar(Long id, ItemVendas itemVendaAtualizado) {
        Optional<ItemVendas> itemExistente = itemVendaRepository.findById(id);
        if (itemExistente.isPresent()) {
            ItemVendas item = itemExistente.get();
            item.setQuantidade(itemVendaAtualizado.getQuantidade());
            item.setPrecoVenda(itemVendaAtualizado.getPrecoVenda());
            return itemVendaRepository.save(item);
        }
        throw new RuntimeException("Item de venda não encontrado");
    }

    public Optional<ItemVendas> buscarPorId(Long id) {
        return itemVendaRepository.findById(id);
    }

    public List<ItemVendas> buscarPorVenda(Long vendaId) {
        return itemVendaRepository.findByVendaId(vendaId);
    }

    public List<ItemVendas> buscarPorEstoque(Long estoqueId) {
        return itemVendaRepository.findByEstoqueId(estoqueId);
    }

    public void deletar(Long id) {
        itemVendaRepository.deleteById(id);
    }
}
