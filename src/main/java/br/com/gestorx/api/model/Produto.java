package br.com.gestorx.api.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "Produto")
public class Produto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Produto_id")
    private Long id;

    @Column(name = "Produto_Descricao")
    private String descricao;

    @Column(name = "Produto_Categoria")
    private String categoria;

    @Column(name = "Produto_UnidadeDeMedida")
    private String unidadeDeMedida;

    // Campo auxiliar para receber o fornecedorId do front
    @Transient
    private Long fornecedorId;

    // Relacionamento real com Fornecedor
    @ManyToOne
    @JoinColumn(name = "Fornecedor_id") // nome da coluna FK no banco
    private Fornecedor fornecedor;

    // Relacionamento com Estoque
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Estoque> estoque;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getUnidadeDeMedida() { return unidadeDeMedida; }
    public void setUnidadeDeMedida(String unidadeDeMedida) { this.unidadeDeMedida = unidadeDeMedida; }

    public Long getFornecedorId() { return fornecedorId; }
    public void setFornecedorId(Long fornecedorId) { this.fornecedorId = fornecedorId; }

    public Fornecedor getFornecedor() { return fornecedor; }
    public void setFornecedor(Fornecedor fornecedor) { this.fornecedor = fornecedor; }

    public List<Estoque> getEstoque() { return estoque; }
    public void setEstoque(List<Estoque> estoque) { this.estoque = estoque; }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.descricao);
        hash = 89 * hash + Objects.hashCode(this.categoria);
        hash = 89 * hash + Objects.hashCode(this.unidadeDeMedida);
        hash = 89 * hash + Objects.hashCode(this.fornecedorId);
        hash = 89 * hash + Objects.hashCode(this.fornecedor);
        hash = 89 * hash + Objects.hashCode(this.estoque);
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
        final Produto other = (Produto) obj;
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.categoria, other.categoria)) {
            return false;
        }
        if (!Objects.equals(this.unidadeDeMedida, other.unidadeDeMedida)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.fornecedorId, other.fornecedorId)) {
            return false;
        }
        if (!Objects.equals(this.fornecedor, other.fornecedor)) {
            return false;
        }
        return Objects.equals(this.estoque, other.estoque);
    }

    @Override
    public String toString() {
        return "Produto{" + "id=" + id + ", descricao=" + descricao + ", categoria=" + categoria + ", unidadeDeMedida=" + unidadeDeMedida + ", fornecedorId=" + fornecedorId + ", fornecedor=" + fornecedor + ", estoque=" + estoque + '}';
    }
    
    
}

