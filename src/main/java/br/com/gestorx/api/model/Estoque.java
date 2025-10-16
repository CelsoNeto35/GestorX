/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.gestorx.api.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import br.com.gestorx.api.model.ItemVendas;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import br.com.gestorx.api.model.Produto;
import br.com.gestorx.api.model.Fornecedor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import org.springframework.format.annotation.DateTimeFormat;
/**
 *
 * @author celso
 */
@Entity
@Table
public class Estoque implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Estoque_Id")
    private long Id;
    @Column(name = "Estoque_Categoria")
    private String categoria;
    @Column(name = "Estoque_MarcaModelo")
    private String marcaModelo;
    @Column(name = "Estoque_QuantidadeDisponivel")
    private String quantidadeDisponivel;
    @Column(name = "Estoque_Lote")
    private String lote;
    @Column(name = "Estoque_Validade")
    private String validade;
    @Column(name = "Estoque_PrecoDeCusto")
    private String precoDeCusto;
    @Column(name = "Estoque_PrecoDeVenda")
    private String precoDeVenda;
    @Column(name = "Estoque_MargemDeLucro")
    private String margemDeLucro;
    
    @Transient
    private Long produtoId;
    @ManyToOne()
    @JoinColumn(name="Produto_id")        
    private Produto produto;
    
    @OneToMany(targetEntity = ItemVendas.class, mappedBy = "estoque", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemVendas> itemVendas;

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
    public List<ItemVendas> getItemVendas() { return itemVendas; }
    public void setItemVendas(List<ItemVendas> itemVendas) { this.itemVendas = itemVendas; }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public String toString() {
        return "Estoque{" + "Id=" + Id + ", categoria=" + categoria + ", marcaModelo=" + marcaModelo + ", quantidadeDisponivel=" + quantidadeDisponivel + ", lote=" + lote + ", validade=" + validade + ", precoDeCusto=" + precoDeCusto + ", precoDeVenda=" + precoDeVenda + ", margemDeLucro=" + margemDeLucro + ", produtoId=" + produtoId + ", produto=" + produto + ", itemVendas=" + itemVendas + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + (int) (this.Id ^ (this.Id >>> 32));
        hash = 43 * hash + Objects.hashCode(this.categoria);
        hash = 43 * hash + Objects.hashCode(this.marcaModelo);
        hash = 43 * hash + Objects.hashCode(this.quantidadeDisponivel);
        hash = 43 * hash + Objects.hashCode(this.lote);
        hash = 43 * hash + Objects.hashCode(this.validade);
        hash = 43 * hash + Objects.hashCode(this.precoDeCusto);
        hash = 43 * hash + Objects.hashCode(this.precoDeVenda);
        hash = 43 * hash + Objects.hashCode(this.margemDeLucro);
        hash = 43 * hash + Objects.hashCode(this.produtoId);
        hash = 43 * hash + Objects.hashCode(this.produto);
        hash = 43 * hash + Objects.hashCode(this.itemVendas);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Estoque other = (Estoque) obj;
        if (this.Id != other.Id) {
            return false;
        }
        if (!Objects.equals(this.categoria, other.categoria)) {
            return false;
        }
        if (!Objects.equals(this.marcaModelo, other.marcaModelo)) {
            return false;
        }
        if (!Objects.equals(this.quantidadeDisponivel, other.quantidadeDisponivel)) {
            return false;
        }
        if (!Objects.equals(this.lote, other.lote)) {
            return false;
        }
        if (!Objects.equals(this.validade, other.validade)) {
            return false;
        }
        if (!Objects.equals(this.precoDeCusto, other.precoDeCusto)) {
            return false;
        }
        if (!Objects.equals(this.precoDeVenda, other.precoDeVenda)) {
            return false;
        }
        if (!Objects.equals(this.margemDeLucro, other.margemDeLucro)) {
            return false;
        }
        if (!Objects.equals(this.produtoId, other.produtoId)) {
            return false;
        }
        if (!Objects.equals(this.produto, other.produto)) {
            return false;
        }
        return Objects.equals(this.itemVendas, other.itemVendas);
    }
    
    
    
}
