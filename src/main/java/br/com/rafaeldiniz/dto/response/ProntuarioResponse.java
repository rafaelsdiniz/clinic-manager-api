package br.com.rafaeldiniz.dto.response;

import java.time.LocalDateTime;

public record ProntuarioResponse(
        Long id,
        Long consultaId,
        LocalDateTime criadoEm,
        String queixaPrincipal,
        String anamnese,
        String exameFisico,
        String diagnostico,
        String conduta,
        String observacoes
) {}