package br.com.rafaeldiniz.dto.response;

import br.com.rafaeldiniz.model.enums.EspecialidadeMedica;
import br.com.rafaeldiniz.model.valueObject.Cpf;
import br.com.rafaeldiniz.model.valueObject.Crm;
import br.com.rafaeldiniz.model.valueObject.Email;
import br.com.rafaeldiniz.model.valueObject.Endereco;
import br.com.rafaeldiniz.model.valueObject.Telefone;

public record MedicoResponse(
        Long id,
        String nome,
        Cpf cpf,
        Crm crm,
        EspecialidadeMedica especialidade,
        Telefone telefone,
        Email email,
        Endereco endereco,
        Boolean ativo
) {}
