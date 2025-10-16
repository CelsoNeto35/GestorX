package br.com.gestorx.api.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "venda")
public class Venda implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Venda_id")
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "Venda_DataDaVenda", nullable = false)
    private Date dataDaVenda;

    @Temporal(TemporalType.TIME)
    @Column(name = "Venda_HoraDaVenda", nullable = false)
    private Date horaDaVenda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Cliente_id", referencedColumnName = "Cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemVendas> itensVendas;

    @Column(name = "Venda_PrecoTotal", precision = 10, scale = 2)
    private BigDecimal precoTotal;

    @Column(name = "Venda_DescontoAplicado")
    private int descontoAplicado;

    @Column(name = "Venda_FormaDePagamento", length = 50)
    private String formaDePagamento;

    @Column(name = "Venda_CondicaoDePagamento", length = 50)
    private String condicaoDePagamento;
    public Venda() {
    }

    public Venda(Date dataDaVenda, Date horaDaVenda, Cliente cliente,
                 List<ItemVendas> itens, BigDecimal precoTotal,
                 int descontoAplicado, String formaDePagamento,
                 String condicaoDePagamento) {
        this.dataDaVenda = dataDaVenda;
        this.horaDaVenda = horaDaVenda;
        this.cliente = cliente;
        this.setItens(itens);
        this.precoTotal = precoTotal;
        this.descontoAplicado = descontoAplicado;
        this.formaDePagamento = formaDePagamento;
        this.condicaoDePagamento = condicaoDePagamento;
    }
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemVendas> getItens() {
        return itensVendas;
    }

    public void setItens(List<ItemVendas> itens) {
        this.itensVendas = itens;
        if (itens != null) {
            itens.forEach(item -> item.setVenda(this)); // garante a relação bidirecional
        }
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
    @Override
    public String toString() {
        return "Venda{" +
                "id=" + id +
                ", dataDaVenda=" + dataDaVenda +
                ", horaDaVenda=" + horaDaVenda +
                ", cliente=" + (cliente != null ? cliente.getId() : null) +
                ", precoTotal=" + precoTotal +
                ", descontoAplicado=" + descontoAplicado +
                ", formaDePagamento='" + formaDePagamento + '\'' +
                ", condicaoDePagamento='" + condicaoDePagamento + '\'' +
                '}';
    }
}
