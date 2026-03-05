package br.com.rafaeldiniz.model.valueObject;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Crm {

    @Column(name = "crm", nullable = false, unique = true)
    private String numero;

    @Column(name = "crm_estado", nullable = false)
    private String estado;

    public Crm() {
    }

    public Crm(String numero, String estado) {
        if (numero == null || numero.isBlank()) {
            throw new IllegalArgumentException("CRM inválido");
        }

        if (estado == null || estado.length() != 2) {
            throw new IllegalArgumentException("Estado do CRM inválido");
        }

        this.numero = numero;
        this.estado = estado.toUpperCase();
    }

    public String getNumero() {
        return numero;
    }

    public String getEstado() {
        return estado;
    }
}