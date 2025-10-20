package br.com.gestorx.api.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import br.com.gestorx.api.model.Venda;

@Entity
@Table(name = "item_vendas")
public class ItemVendas implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venda_id", nullable = false)
    private Venda venda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estoque_id", nullable = false)
    private Estoque estoque;

    @Column(name = "quantidade", nullable = false)
    private BigDecimal quantidade;

    @Column(name = "preco_venda", nullable = false)
    private BigDecimal precoVenda;

    // Construtores
    public ItemVendas() {
    }

    public ItemVendas(Estoque estoque, BigDecimal quantidade) {
        this.estoque = estoque;
        this.quantidade = quantidade;
        this.precoVenda = estoque.getPrecoDeVenda();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Estoque getEstoque() {
        return estoque;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(BigDecimal precoVenda) {
        this.precoVenda = precoVenda;
    }

    public BigDecimal getSubtotal() {
        return precoVenda.multiply (quantidade);
    }
}

