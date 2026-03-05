package br.com.rafaeldiniz.dto.response;

import br.com.rafaeldiniz.model.enums.ViaAdministracao;

public record ItemReceitaResponse(
        Long id,
        Long receitaId,
        String medicamento,
        String dosagem,
        String frequencia,
        String duracao,
        ViaAdministracao via,
        String instrucoes
) {}
