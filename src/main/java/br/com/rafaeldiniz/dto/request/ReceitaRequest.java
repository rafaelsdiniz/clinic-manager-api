package br.com.rafaeldiniz.dto.request;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;

public record ReceitaRequest(
        @NotNull Long prontuarioId,
        LocalDateTime criadoEm,
        String observacoes
) {}
