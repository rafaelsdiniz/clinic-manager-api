package br.com.rafaeldiniz.dto.response;

import java.time.LocalDateTime;

public record ReceitaResponse(
        Long id,
        Long prontuarioId,
        LocalDateTime criadoEm,
        String observacoes
) {}
