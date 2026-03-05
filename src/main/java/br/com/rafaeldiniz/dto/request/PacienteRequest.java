package br.com.rafaeldiniz.dto.request;

import java.time.LocalDate;

import br.com.rafaeldiniz.model.valueObject.Cpf;
import br.com.rafaeldiniz.model.valueObject.Email;
import br.com.rafaeldiniz.model.valueObject.Endereco;
import br.com.rafaeldiniz.model.valueObject.Telefone;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PacienteRequest(
        @NotBlank String nome,
        @NotNull @Valid Cpf cpf,
        @Valid Email email,
        @Valid Telefone telefone,
        LocalDate dataNascimento,
        @Valid Endereco endereco
) {}