package br.com.rafaeldiniz.dto.request;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;

public record ProntuarioRequest(
        @NotNull Long consultaId,
        LocalDateTime criadoEm,
        String queixaPrincipal,
        String anamnese,
        String exameFisico,
        String diagnostico,
        String conduta,
        String observacoes
) {}
