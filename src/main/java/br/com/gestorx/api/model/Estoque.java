package br.com.gestorx.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import br.com.gestorx.api.model.ItemVendas;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Estoque")
public class Estoque implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Estoque_Id")
    private Long id;
    @Column(name = "Estoque_Categoria")
    private String categoria;
    @Column(name = "Estoque_MarcaModelo")
    private String marcaModelo;
    @Column(name = "Estoque_QuantidadeDisponivel", precision = 15, scale = 3)
    private BigDecimal quantidadeDisponivel;
    @Column(name = "Estoque_Lote")
    private String lote;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "Estoque_Validade")
    private Date validade;
    @Column(name = "Estoque_PrecoDeCusto", precision = 15, scale = 2)
    private BigDecimal precoDeCusto;
    @Column(name = "Estoque_PrecoDeVenda", precision = 15, scale = 2)
    private BigDecimal precoDeVenda;
    @Column(name = "Estoque_MargemDeLucro", precision = 5, scale = 2)
    private BigDecimal margemDeLucro;

    @Transient
    private Long produtoId;

    @ManyToOne
    @JoinColumn(name = "Produto_id")
    private Produto produto;

    @OneToMany(
        targetEntity = ItemVendas.class,
        mappedBy = "estoque",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<ItemVendas> itemVendas;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public void setQuantidadeDisponivel(BigDecimal quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Date getValidade() {
        return validade;
    }

    public void setValidade(Date validade) {
        this.validade = validade;
    }

    public BigDecimal getPrecoDeCusto() {
        return precoDeCusto;
    }

    public void setPrecoDeCusto(BigDecimal precoDeCusto) {
        this.precoDeCusto = precoDeCusto;
    }

    public BigDecimal getPrecoDeVenda() {
        return precoDeVenda;
    }

    public void setPrecoDeVenda(BigDecimal precoDeVenda) {
        this.precoDeVenda = precoDeVenda;
    }

    public BigDecimal getMargemDeLucro() {
        return margemDeLucro;
    }

    public void setMargemDeLucro(BigDecimal margemDeLucro) {
        this.margemDeLucro = margemDeLucro;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public List<ItemVendas> getItemVendas() {
        return itemVendas;
    }

    public void setItemVendas(List<ItemVendas> itemVendas) {
        this.itemVendas = itemVendas;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public String toString() {
        return "Estoque{" +
                "id=" + id +
                ", categoria='" + categoria + '\'' +
                ", marcaModelo='" + marcaModelo + '\'' +
                ", quantidadeDisponivel=" + quantidadeDisponivel +
                ", lote='" + lote + '\'' +
                ", validade='" + validade + '\'' +
                ", precoDeCusto=" + precoDeCusto +
                ", precoDeVenda=" + precoDeVenda +
                ", margemDeLucro=" + margemDeLucro +
                ", produtoId=" + produtoId +
                ", produto=" + produto +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, categoria, marcaModelo, quantidadeDisponivel, lote,
                validade, precoDeCusto, precoDeVenda, margemDeLucro, produtoId, produto);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Estoque)) return false;
        Estoque other = (Estoque) obj;
        return Objects.equals(id, other.id)
                && Objects.equals(categoria, other.categoria)
                && Objects.equals(marcaModelo, other.marcaModelo)
                && Objects.equals(quantidadeDisponivel, other.quantidadeDisponivel)
                && Objects.equals(lote, other.lote)
                && Objects.equals(validade, other.validade)
                && Objects.equals(precoDeCusto, other.precoDeCusto)
                && Objects.equals(precoDeVenda, other.precoDeVenda)
                && Objects.equals(margemDeLucro, other.margemDeLucro)
                && Objects.equals(produto, other.produto);
    }
}
