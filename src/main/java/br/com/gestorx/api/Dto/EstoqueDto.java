/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.gestorx.api.Dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author cneto
 */
public class EstoqueDto {
    private long Id;
    private String categoria;
    private String marcaModelo;
    private String quantidadeDisponivel;
    private String lote;
    private String validade;
    private String precoDeCusto;
    private String precoDeVenda;
    private String margemDeLucro;
    private Long produtoId;
    private String produtoDescricao;

    public long getId() {
        return Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMarcaModelo() {
        return marcaModelo;
    }

    public void setMarcaModelo(String marcaModelo) {
        this.marcaModelo = marcaModelo;
    }

    public String getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(String quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public String getPrecoDeCusto() {
        return precoDeCusto;
    }

    public void setPrecoDeCusto(String precoDeCusto) {
        this.precoDeCusto = precoDeCusto;
    }

    public String getPrecoDeVenda() {
        return precoDeVenda;
    }

    public void setPrecoDeVenda(String precoDeVenda) {
        this.precoDeVenda = precoDeVenda;
    }

    public String getMargemDeLucro() {
        return margemDeLucro;
    }

    public void setMargemDeLucro(String margemDeLucro) {
        this.margemDeLucro = margemDeLucro;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public String getProdutoDescricao() {
        return produtoDescricao;
    }

    public void setProdutoDescricao(String produtoDescricao) {
        this.produtoDescricao = produtoDescricao;
    }
    
    
}
