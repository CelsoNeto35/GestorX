/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.gestorx.api.model;

/**
 *
 * @author cneto
 */
public enum CondicaoPagamento {
    VISTA("À Vista"),
    PARCELADO_2X("2x"),
    PARCELADO_3X("3x"),
    PARCELADO_6X("6x"),
    PARCELADO_12X("12x");

    private String descricao;

    CondicaoPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
