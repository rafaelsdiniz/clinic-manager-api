package br.com.rafaeldiniz.model.entity;

import br.com.rafaeldiniz.model.enums.ViaAdministracao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "itens_receita")
public class ItemReceita extends DefaultEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "receita_id", nullable = false)
    @NotNull
    private Receita receita;

    @Column(nullable = false, length = 200)
    @NotNull
    private String medicamento;

    @Column(nullable = false, length = 120)
    @NotNull
    private String dosagem; // ex: "500mg"

    @Column(nullable = false, length = 120)
    @NotNull
    private String frequencia; // ex: "8/8h"

    @Column(nullable = false, length = 120)
    @NotNull
    private String duracao; // ex: "7 dias"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @NotNull
    private ViaAdministracao via; // ORAL, TOPICA, etc.

    @Column(length = 1000)
    private String instrucoes;

    public ItemReceita() {
    }

    public Receita getReceita() {
        return receita;
    }

    public void setReceita(Receita receita) {
        this.receita = receita;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public String getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(String frequencia) {
        this.frequencia = frequencia;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public ViaAdministracao getVia() {
        return via;
    }

    public void setVia(ViaAdministracao via) {
        this.via = via;
    }

    public String getInstrucoes() {
        return instrucoes;
    }

    public void setInstrucoes(String instrucoes) {
        this.instrucoes = instrucoes;
    }

    
}