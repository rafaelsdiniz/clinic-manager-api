package br.com.rafaeldiniz.dto.request;

import java.time.LocalDateTime;
import br.com.rafaeldiniz.model.enums.StatusConsulta;
import jakarta.validation.constraints.NotNull;

public record ConsultaRequest(
        @NotNull Long pacienteId,
        @NotNull Long medicoId,
        @NotNull LocalDateTime dataHora,
        String observacoes,
        @NotNull StatusConsulta status
) {}
