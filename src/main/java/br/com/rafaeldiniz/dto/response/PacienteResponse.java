package br.com.rafaeldiniz.dto.response;

import java.time.LocalDate;
import br.com.rafaeldiniz.model.valueObject.Cpf;
import br.com.rafaeldiniz.model.valueObject.Email;
import br.com.rafaeldiniz.model.valueObject.Endereco;
import br.com.rafaeldiniz.model.valueObject.Telefone;

public record PacienteResponse(
        Long id,
        String nome,
        Cpf cpf,
        Email email,
        Telefone telefone,
        LocalDate dataNascimento,
        Endereco endereco
) {}
