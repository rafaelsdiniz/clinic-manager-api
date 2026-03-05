package br.com.rafaeldiniz.dto.request;

import br.com.rafaeldiniz.model.enums.ViaAdministracao;
import jakarta.validation.constraints.NotNull;

public record ItemReceitaRequest(
        @NotNull Long receitaId,
        @NotNull String medicamento,
        @NotNull String dosagem,
        @NotNull String frequencia,
        @NotNull String duracao,
        @NotNull ViaAdministracao via,
        String instrucoes
) {}
