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
import javax.persistence.Table;
import br.com.gestorx.api.model.Produto;
import br.com.gestorx.api.model.Estoque;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import br.com.gestorx.api.model.Entrada;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
/**
 *
 * @author celso
 */
@Entity
@Table(name="Fornecedores")
public class Fornecedor implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Fornecedor_id")
    private long Id;
    @Column(name = "RazaoSocial")
    private String RazaoSocial;
    @Column(name = "NomeFantasia")
    private String nomeFantasia;
    @Column(name = "CnpjCpf")
    private String cnpj;
    @Column(name = "insEstatual")
    private String InsEstadual;
    @Column(name = "DataDeCadastro")
    private String dataDeCadastro;
    @Column(name = "Telefone")
    private String telefone;
    @Column(name = "Email")
    private String email;
    @Column(name = "Cep")
    private String cep;
    @Column(name = "RuaLogradouro")
    private String ruaLogradouro;
    @Column(name = "Numero")
    private String numero;
    @Column(name = "Complemento")
    private String complemento;
    @Column(name = "Bairro")
    private String bairro;
    @Column(name = "Cidade")
    private String Cidade;
    @Column(name = "Estado")
    private String estado;
    @Column(name = "Pais")
    private String pais;
    @ManyToOne()
    @JoinColumn(name="produto_id", referencedColumnName = "produto_id")        
    private Produto produto;
    
    @ManyToOne()
    @JoinColumn(name="estoque_id", referencedColumnName = "estoque_id")        
    private Estoque estoque;
    
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Entrada_id", referencedColumnName = "Entrada_id")
    private Entrada entrada;
    //Construtores
    public Fornecedor(){}
    //Getters e Setters
    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Entrada getEntrada() {
        return entrada;
    }

    public void setEntrada(Entrada entrada) {
        this.entrada = entrada;
    }

    public long getId() {
        return Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }

    public String getRazaoSocial() {
        return RazaoSocial;
    }

    public void setRazaoSocial(String RazaoSocial) {
        this.RazaoSocial = RazaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getInsEstadual() {
        return InsEstadual;
    }

    public void setInsEstadual(String InsEstadual) {
        this.InsEstadual = InsEstadual;
    }

    public String getDataDeCadastro() {
        return dataDeCadastro;
    }

    public void setDataDeCadastro(String dataDeCadastro) {
        this.dataDeCadastro = dataDeCadastro;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getRuaLogradouro() {
        return ruaLogradouro;
    }

    public void setRuaLogradouro(String ruaLogradouro) {
        this.ruaLogradouro = ruaLogradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return Cidade;
    }

    public void setCidade(String Cidade) {
        this.Cidade = Cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (int) (this.Id ^ (this.Id >>> 32));
        hash = 89 * hash + Objects.hashCode(this.RazaoSocial);
        hash = 89 * hash + Objects.hashCode(this.nomeFantasia);
        hash = 89 * hash + Objects.hashCode(this.cnpj);
        hash = 89 * hash + Objects.hashCode(this.InsEstadual);
        hash = 89 * hash + Objects.hashCode(this.dataDeCadastro);
        hash = 89 * hash + Objects.hashCode(this.telefone);
        hash = 89 * hash + Objects.hashCode(this.email);
        hash = 89 * hash + Objects.hashCode(this.cep);
        hash = 89 * hash + Objects.hashCode(this.ruaLogradouro);
        hash = 89 * hash + Objects.hashCode(this.numero);
        hash = 89 * hash + Objects.hashCode(this.complemento);
        hash = 89 * hash + Objects.hashCode(this.bairro);
        hash = 89 * hash + Objects.hashCode(this.Cidade);
        hash = 89 * hash + Objects.hashCode(this.estado);
        hash = 89 * hash + Objects.hashCode(this.pais);
        hash = 89 * hash + Objects.hashCode(this.produto);
        hash = 89 * hash + Objects.hashCode(this.estoque);
        hash = 89 * hash + Objects.hashCode(this.entrada);
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
        final Fornecedor other = (Fornecedor) obj;
        if (this.Id != other.Id) {
            return false;
        }
        if (!Objects.equals(this.RazaoSocial, other.RazaoSocial)) {
            return false;
        }
        if (!Objects.equals(this.nomeFantasia, other.nomeFantasia)) {
            return false;
        }
        if (!Objects.equals(this.cnpj, other.cnpj)) {
            return false;
        }
        if (!Objects.equals(this.InsEstadual, other.InsEstadual)) {
            return false;
        }
        if (!Objects.equals(this.dataDeCadastro, other.dataDeCadastro)) {
            return false;
        }
        if (!Objects.equals(this.telefone, other.telefone)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.cep, other.cep)) {
            return false;
        }
        if (!Objects.equals(this.ruaLogradouro, other.ruaLogradouro)) {
            return false;
        }
        if (!Objects.equals(this.numero, other.numero)) {
            return false;
        }
        if (!Objects.equals(this.complemento, other.complemento)) {
            return false;
        }
        if (!Objects.equals(this.bairro, other.bairro)) {
            return false;
        }
        if (!Objects.equals(this.Cidade, other.Cidade)) {
            return false;
        }
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        if (!Objects.equals(this.pais, other.pais)) {
            return false;
        }
        if (!Objects.equals(this.produto, other.produto)) {
            return false;
        }
        if (!Objects.equals(this.estoque, other.estoque)) {
            return false;
        }
        return Objects.equals(this.entrada, other.entrada);
    }

    @Override
    public String toString() {
        return "Fornecedor{" + "Id=" + Id + ", RazaoSocial=" + RazaoSocial + ", nomeFantasia=" + nomeFantasia + ", cnpj=" + cnpj + ", InsEstadual=" + InsEstadual + ", dataDeCadastro=" + dataDeCadastro + ", telefone=" + telefone + ", email=" + email + ", cep=" + cep + ", ruaLogradouro=" + ruaLogradouro + ", numero=" + numero + ", complemento=" + complemento + ", bairro=" + bairro + ", Cidade=" + Cidade + ", estado=" + estado + ", pais=" + pais + ", produto=" + produto + ", estoque=" + estoque + ", entrada=" + entrada + '}';
    }

    
 
    
}
