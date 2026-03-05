package br.com.rafaeldiniz.model.entity;

import java.time.LocalDate;

import br.com.rafaeldiniz.model.valueObject.Cpf;
import br.com.rafaeldiniz.model.valueObject.Email;
import br.com.rafaeldiniz.model.valueObject.Endereco;
import br.com.rafaeldiniz.model.valueObject.Telefone;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "pacientes")
public class Paciente extends DefaultEntity {

    @Column(nullable = false)
    private String nome;

    @Embedded
    private Cpf cpf;

    @Embedded
    private Email email;

    @Embedded
    private Telefone telefone;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Embedded
    private Endereco endereco;

    public Paciente() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Cpf getCpf() {
        return cpf;
    }

    public void setCpf(Cpf cpf) {
        this.cpf = cpf;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}