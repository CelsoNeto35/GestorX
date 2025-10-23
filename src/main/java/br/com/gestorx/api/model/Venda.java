/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.gestorx.api.model;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.TemporalType;
import br.com.gestorx.api.model.ItemVendas;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
/**
 *
 * @author cneto
 */
@Entity
@Table(name = "vendas")
public class Venda implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "venda_id")
    private Long id;

    @Column(name = "data_venda", nullable = false)
    private LocalDateTime dataVenda;

    @ManyToOne
    @JoinColumn(name = "Cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemVendas> itens = new ArrayList<>();

    @Column(name = "preco_total", nullable = false)
    private BigDecimal precoTotal = BigDecimal.ZERO;

    @Column(name = "desconto")
    private BigDecimal desconto = BigDecimal.ZERO;

    @Column(name = "forma_pagamento", nullable = false)
    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    @Column(name = "condicao_pagamento", nullable = false)
    @Enumerated(EnumType.STRING)
    private CondicaoPagamento condicaoPagamento;

    @Column(name = "ativo")
    private Boolean ativo = true;
    
    @Column(name = "comissao_percentual", precision = 5, scale = 2)
    private BigDecimal comissaoPercentual = BigDecimal.ZERO;

    // Construtores
    public Venda() {
    }

    public Venda(Cliente cliente, FormaPagamento formaPagamento, CondicaoPagamento condicaoPagamento) {
        this.cliente = cliente;
        this.dataVenda = LocalDateTime.now();
        this.formaPagamento = formaPagamento;
        this.condicaoPagamento = condicaoPagamento;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDateTime dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemVendas> getItens() {
        return itens;
    }

    public void setItens(List<ItemVendas> itens) {
        this.itens = itens;
    }

    public BigDecimal getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(BigDecimal precoTotal) {
        this.precoTotal = precoTotal;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public CondicaoPagamento getCondicaoPagamento() {
        return condicaoPagamento;
    }

    public void setCondicaoPagamento(CondicaoPagamento condicaoPagamento) {
        this.condicaoPagamento = condicaoPagamento;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public void adicionarItem(ItemVendas item) {
        item.setVenda(this);
        this.itens.add(item);
    }

    public void removerItem(ItemVendas item) {
        this.itens.remove(item);
        item.setVenda(null);
    }

    public BigDecimal calcularTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemVendas item : itens) {
            total = total.add(item.getSubtotal());
        }
        return total.subtract(desconto != null ? desconto : BigDecimal.ZERO);
    }

    public BigDecimal getComissaoPercentual() {
        return comissaoPercentual;
    }

    public void setComissaoPercentual(BigDecimal comissaoPercentual) {
        this.comissaoPercentual = comissaoPercentual;
    }
    
}
