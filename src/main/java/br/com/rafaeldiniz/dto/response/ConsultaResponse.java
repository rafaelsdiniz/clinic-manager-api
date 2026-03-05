package br.com.rafaeldiniz.dto.response;

import java.time.LocalDateTime;
import br.com.rafaeldiniz.model.enums.StatusConsulta;

public record ConsultaResponse(
        Long id,
        Long pacienteId,
        Long medicoId,
        LocalDateTime dataHora,
        String observacoes,
        StatusConsulta status
) {}
