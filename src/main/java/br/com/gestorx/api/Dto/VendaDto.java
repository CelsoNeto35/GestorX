package br.com.gestorx.api.Dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class VendaDto {

    private Long id;
    private Date dataDaVenda;
    private Date horaDaVenda;
    private BigDecimal precoTotal;
    private int descontoAplicado;
    private String formaDePagamento;
    private String condicaoDePagamento;

    private Long clienteId;
    private String clienteNome;

    private List<ItemVendasDto> itens; // Lista de itens da venda

    // ===== GETTERS E SETTERS =====
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataDaVenda() {
        return dataDaVenda;
    }

    public void setDataDaVenda(Date dataDaVenda) {
        this.dataDaVenda = dataDaVenda;
    }

    public Date getHoraDaVenda() {
        return horaDaVenda;
    }

    public void setHoraDaVenda(Date horaDaVenda) {
        this.horaDaVenda = horaDaVenda;
    }

    public BigDecimal getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(BigDecimal precoTotal) {
        this.precoTotal = precoTotal;
    }

    public int getDescontoAplicado() {
        return descontoAplicado;
    }

    public void setDescontoAplicado(int descontoAplicado) {
        this.descontoAplicado = descontoAplicado;
    }

    public String getFormaDePagamento() {
        return formaDePagamento;
    }

    public void setFormaDePagamento(String formaDePagamento) {
        this.formaDePagamento = formaDePagamento;
    }

    public String getCondicaoDePagamento() {
        return condicaoDePagamento;
    }

    public void setCondicaoDePagamento(String condicaoDePagamento) {
        this.condicaoDePagamento = condicaoDePagamento;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public List<ItemVendasDto> getItens() {
        return itens;
    }

    public void setItens(List<ItemVendasDto> itens) {
        this.itens = itens;
    }
}
