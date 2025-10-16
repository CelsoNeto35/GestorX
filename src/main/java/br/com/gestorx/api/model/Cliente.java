package br.com.gestorx.api.model;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import br.com.gestorx.api.model.Venda;
import java.util.Objects;

@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Cliente_id")
    private Long id;

    @Column(name = "nome_completo_razao_social")
    private String nomeCompletoRazaoSocial;

    @Column(name = "nome_fantasia")
    private String nomeFantasia;

    @Column(name = "cnpj_cpf")
    private String cnpjCpf;

    @Column(name = "rg_inscricao_estadual")
    private String rgInscricaoEstadual;

    @Column(name = "data_nascimento_cadastro")
    private String dataNascimentoCadastro;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "email")
    private String email;

    @Column(name = "cep")
    private String cep;

    @Column(name = "rua_logradouro")
    private String ruaLogradouro;

    @Column(name = "numero")
    private String numero;

    @Column(name = "complemento")
    private String complemento;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "cidade")
    private String cidade;

    @Column(name = "estado")
    private String estado;

    @Column(name = "pais")
    private String pais;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Venda> venda;

    // Construtores
    public Cliente() {}

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCompletoRazaoSocial() {
        return nomeCompletoRazaoSocial;
    }

    public void setNomeCompletoRazaoSocial(String nomeCompletoRazaoSocial) {
        this.nomeCompletoRazaoSocial = nomeCompletoRazaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getCnpjCpf() {
        return cnpjCpf;
    }

    public void setCnpjCpf(String cnpjCpf) {
        this.cnpjCpf = cnpjCpf;
    }

    public String getRgInscricaoEstadual() {
        return rgInscricaoEstadual;
    }

    public void setRgInscricaoEstadual(String rgInscricaoEstadual) {
        this.rgInscricaoEstadual = rgInscricaoEstadual;
    }

    public String getDataNascimentoCadastro() {
        return dataNascimentoCadastro;
    }

    public void setDataNascimentoCadastro(String dataNascimentoCadastro) {
        this.dataNascimentoCadastro = dataNascimentoCadastro;
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
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
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

    public List<Venda> getVendas() {
        return venda;
    }

    public void setVendas(List<Venda> vendas) {
        this.venda = vendas;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.nomeCompletoRazaoSocial);
        hash = 79 * hash + Objects.hashCode(this.nomeFantasia);
        hash = 79 * hash + Objects.hashCode(this.cnpjCpf);
        hash = 79 * hash + Objects.hashCode(this.rgInscricaoEstadual);
        hash = 79 * hash + Objects.hashCode(this.dataNascimentoCadastro);
        hash = 79 * hash + Objects.hashCode(this.telefone);
        hash = 79 * hash + Objects.hashCode(this.email);
        hash = 79 * hash + Objects.hashCode(this.cep);
        hash = 79 * hash + Objects.hashCode(this.ruaLogradouro);
        hash = 79 * hash + Objects.hashCode(this.numero);
        hash = 79 * hash + Objects.hashCode(this.complemento);
        hash = 79 * hash + Objects.hashCode(this.bairro);
        hash = 79 * hash + Objects.hashCode(this.cidade);
        hash = 79 * hash + Objects.hashCode(this.estado);
        hash = 79 * hash + Objects.hashCode(this.pais);
        hash = 79 * hash + Objects.hashCode(this.venda);
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
        final Cliente other = (Cliente) obj;
        if (!Objects.equals(this.nomeCompletoRazaoSocial, other.nomeCompletoRazaoSocial)) {
            return false;
        }
        if (!Objects.equals(this.nomeFantasia, other.nomeFantasia)) {
            return false;
        }
        if (!Objects.equals(this.cnpjCpf, other.cnpjCpf)) {
            return false;
        }
        if (!Objects.equals(this.rgInscricaoEstadual, other.rgInscricaoEstadual)) {
            return false;
        }
        if (!Objects.equals(this.dataNascimentoCadastro, other.dataNascimentoCadastro)) {
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
        if (!Objects.equals(this.cidade, other.cidade)) {
            return false;
        }
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        if (!Objects.equals(this.pais, other.pais)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.venda, other.venda);
    }

    @Override
    public String toString() {
        return "Cliente{" + "id=" + id + ", nomeCompletoRazaoSocial=" + nomeCompletoRazaoSocial + ", nomeFantasia=" + nomeFantasia + ", cnpjCpf=" + cnpjCpf + ", rgInscricaoEstadual=" + rgInscricaoEstadual + ", dataNascimentoCadastro=" + dataNascimentoCadastro + ", telefone=" + telefone + ", email=" + email + ", cep=" + cep + ", ruaLogradouro=" + ruaLogradouro + ", numero=" + numero + ", complemento=" + complemento + ", bairro=" + bairro + ", cidade=" + cidade + ", estado=" + estado + ", pais=" + pais + ", venda=" + venda + '}';
    }
    
    
}