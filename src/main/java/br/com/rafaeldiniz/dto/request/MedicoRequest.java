package br.com.rafaeldiniz.dto.request;

import br.com.rafaeldiniz.model.enums.EspecialidadeMedica;
import br.com.rafaeldiniz.model.valueObject.Cpf;
import br.com.rafaeldiniz.model.valueObject.Crm;
import br.com.rafaeldiniz.model.valueObject.Email;
import br.com.rafaeldiniz.model.valueObject.Endereco;
import br.com.rafaeldiniz.model.valueObject.Telefone;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MedicoRequest(
        @NotBlank String nome,
        @NotNull @Valid Cpf cpf,
        @NotNull @Valid Crm crm,
        @NotNull EspecialidadeMedica especialidade,
        @Valid Telefone telefone,
        @Valid Email email,
        @Valid Endereco endereco,
        @NotNull Boolean ativo
) {}