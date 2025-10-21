package br.com.gestorx.api.Dto;

import java.math.BigDecimal;

public class ItemVendaFormDto {
    private Long estoqueId;
    private Integer quantidade;
    private BigDecimal precoVenda;

    // Getters e Setters
    public Long getEstoqueId() { return estoqueId; }
    public void setEstoqueId(Long estoqueId) { this.estoqueId = estoqueId; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public BigDecimal getPrecoVenda() { return precoVenda; }
    public void setPrecoVenda(BigDecimal precoVenda) { this.precoVenda = precoVenda; }
}