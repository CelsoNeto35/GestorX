/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.gestorx.api.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import br.com.gestorx.api.model.Entrada;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import br.com.gestorx.api.model.Estoque;
import java.util.Objects;
/**
 *
 * @author celso
 */
@Entity
public class ItensDaEntrada implements Serializable{
    @ManyToOne()
    @JoinColumn(name="Entrada_id", referencedColumnName = "Entrada_id")        
    private Entrada entrada;
    
    @ManyToOne()
    @JoinColumn(name="Estoque_id", referencedColumnName = "Estoque_id")        
    private Estoque estoque;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ItensDaEntrada_id")
    private long Id;
    @Column(name = "ItensDaEntrada_Quantidade")
    private String Quantidade;
    @Column(name = "ItensDaEntrada_Produto")
    private String Protudo;
    @Column(name = "ItensDaEntrada_Lote")
    private String Lote;

    public long getId() {
        return Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }

    public String getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(String Quantidade) {
        this.Quantidade = Quantidade;
    }

    public String getProtudo() {
        return Protudo;
    }

    public void setProtudo(String Protudo) {
        this.Protudo = Protudo;
    }

    public String getLote() {
        return Lote;
    }

    public void setLote(String Lote) {
        this.Lote = Lote;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.entrada);
        hash = 97 * hash + Objects.hashCode(this.estoque);
        hash = 97 * hash + (int) (this.Id ^ (this.Id >>> 32));
        hash = 97 * hash + Objects.hashCode(this.Quantidade);
        hash = 97 * hash + Objects.hashCode(this.Protudo);
        hash = 97 * hash + Objects.hashCode(this.Lote);
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
        final ItensDaEntrada other = (ItensDaEntrada) obj;
        if (this.Id != other.Id) {
            return false;
        }
        if (!Objects.equals(this.Quantidade, other.Quantidade)) {
            return false;
        }
        if (!Objects.equals(this.Protudo, other.Protudo)) {
            return false;
        }
        if (!Objects.equals(this.Lote, other.Lote)) {
            return false;
        }
        if (!Objects.equals(this.entrada, other.entrada)) {
            return false;
        }
        return Objects.equals(this.estoque, other.estoque);
    }

    @Override
    public String toString() {
        return "ItensDaEntrada{" + "entrada=" + entrada + ", estoque=" + estoque + ", Id=" + Id + ", Quantidade=" + Quantidade + ", Protudo=" + Protudo + ", Lote=" + Lote + '}';
    }
    
    
}
