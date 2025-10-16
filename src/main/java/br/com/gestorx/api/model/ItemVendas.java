package br.com.gestorx.api.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "ItemVendas")
public class ItemVendas implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ItemVendas_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Venda_id", referencedColumnName = "Venda_id")
    private Venda venda;

    @ManyToOne
    @JoinColumn(name = "Estoque_id", referencedColumnName = "Estoque_id")
    private Estoque estoque;

    @Column(name = "ItemVendas_Quantidade")
    private String quantidade;

    @Column(name = "ItemVendas_PrecoUnitario")
    private String precoUnitario;

    @Column(name = "ItemVendas_Lote")
    private String lote;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Venda getVenda() { return venda; }
    public void setVenda(Venda venda) { this.venda = venda; }

    public Estoque getEstoque() { return estoque; }
    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
        if (estoque != null) {
            this.lote = estoque.getLote(); // ← puxa automaticamente o lote
            this.precoUnitario = estoque.getPrecoDeVenda(); // opcional: já puxa preço
        }
    }

    public String getQuantidade() { return quantidade; }
    public void setQuantidade(String quantidade) { this.quantidade = quantidade; }

    public String getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(String precoUnitario) { this.precoUnitario = precoUnitario; }

    public String getLote() { return lote; }
    public void setLote(String lote) { this.lote = lote; }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.id);
        hash = 13 * hash + Objects.hashCode(this.venda);
        hash = 13 * hash + Objects.hashCode(this.estoque);
        hash = 13 * hash + Objects.hashCode(this.quantidade);
        hash = 13 * hash + Objects.hashCode(this.precoUnitario);
        hash = 13 * hash + Objects.hashCode(this.lote);
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
        final ItemVendas other = (ItemVendas) obj;
        if (!Objects.equals(this.quantidade, other.quantidade)) {
            return false;
        }
        if (!Objects.equals(this.precoUnitario, other.precoUnitario)) {
            return false;
        }
        if (!Objects.equals(this.lote, other.lote)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.venda, other.venda)) {
            return false;
        }
        return Objects.equals(this.estoque, other.estoque);
    }

    @Override
    public String toString() {
        return "ItemVendas{" + "id=" + id + ", venda=" + venda + ", estoque=" + estoque + ", quantidade=" + quantidade + ", precoUnitario=" + precoUnitario + ", lote=" + lote + '}';
    }

   
}
