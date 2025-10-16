package br.com.gestorx.api.Dto;

public class ProdutoDto {
    private Long id;
    private String descricao;
    private String categoria;
    private String unidadeDeMedida;
    private Long fornecedorId;
    private String fornecedorNomeFantasia; // <-- Novo campo

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getUnidadeDeMedida() {
        return unidadeDeMedida;
    }

    public void setUnidadeDeMedida(String unidadeDeMedida) {
        this.unidadeDeMedida = unidadeDeMedida;
    }

    public Long getFornecedorId() {
        return fornecedorId;
    }

    public void setFornecedorId(Long fornecedorId) {
        this.fornecedorId = fornecedorId;
    }

    public String getFornecedorNomeFantasia() {
        return fornecedorNomeFantasia;
    }

    public void setFornecedorNomeFantasia(String fornecedorNomeFantasia) {
        this.fornecedorNomeFantasia = fornecedorNomeFantasia;
    }

   
}
