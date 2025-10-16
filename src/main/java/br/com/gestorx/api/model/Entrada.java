/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.gestorx.api.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author cneto
 */
@Entity
@Table
public class Entrada implements Serializable{
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Fornecedor_id", referencedColumnName = "Fornecedor_id")
    private Fornecedor fornecedor;
    
    @OneToMany(targetEntity = ItensDaEntrada.class, mappedBy = "entrada", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItensDaEntrada> itensdaentrada;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Entrada_id")
    private long Id;
    @Column(name = "Entrada_DataDeEntrada")
    private String DataDeEntrada;
    @Column(name = "Entrada_Valor")
    private String Valor;

    public long getId() {
        return Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }

    public String getDataDeEntrada() {
        return DataDeEntrada;
    }

    public void setDataDeEntrada(String DataDeEntrada) {
        this.DataDeEntrada = DataDeEntrada;
    }

    public String getValor() {
        return Valor;
    }

    public void setValor(String Valor) {
        this.Valor = Valor;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.fornecedor);
        hash = 11 * hash + Objects.hashCode(this.itensdaentrada);
        hash = 11 * hash + (int) (this.Id ^ (this.Id >>> 32));
        hash = 11 * hash + Objects.hashCode(this.DataDeEntrada);
        hash = 11 * hash + Objects.hashCode(this.Valor);
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
        final Entrada other = (Entrada) obj;
        if (this.Id != other.Id) {
            return false;
        }
        if (!Objects.equals(this.DataDeEntrada, other.DataDeEntrada)) {
            return false;
        }
        if (!Objects.equals(this.Valor, other.Valor)) {
            return false;
        }
        if (!Objects.equals(this.fornecedor, other.fornecedor)) {
            return false;
        }
        return Objects.equals(this.itensdaentrada, other.itensdaentrada);
    }

    @Override
    public String toString() {
        return "Entrada{" + "fornecedor=" + fornecedor + ", itensdaentrada=" + itensdaentrada + ", Id=" + Id + ", DataDeEntrada=" + DataDeEntrada + ", Valor=" + Valor + '}';
    }
    
}
