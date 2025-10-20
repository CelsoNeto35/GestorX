package br.com.gestorx.api.Dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class VendaFormDto {
    private Long id;
    private LocalDateTime dataVenda;
    private Long clienteId;
    private String formaPagamento;
    private String condicaoPagamento;
    private BigDecimal desconto;
    private List<ItemVendaFormDto> itens;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getDataVenda() { return dataVenda; }
    public void setDataVenda(LocalDateTime dataVenda) { this.dataVenda = dataVenda; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public String getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(String formaPagamento) { this.formaPagamento = formaPagamento; }

    public String getCondicaoPagamento() { return condicaoPagamento; }
    public void setCondicaoPagamento(String condicaoPagamento) { this.condicaoPagamento = condicaoPagamento; }

    public BigDecimal getDesconto() { return desconto; }
    public void setDesconto(BigDecimal desconto) { this.desconto = desconto; }

    public List<ItemVendaFormDto> getItens() { return itens; }
    public void setItens(List<ItemVendaFormDto> itens) { this.itens = itens; }
}
