package br.com.rafaeldiniz.repository;

import java.time.LocalDateTime;
import java.util.List;

import br.com.rafaeldiniz.model.entity.Consulta;
import br.com.rafaeldiniz.model.enums.StatusConsulta;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ConsultaRepository implements PanacheRepository<Consulta> {

    public List<Consulta> buscarPorPacienteId(Long pacienteId) {
        return list("paciente.id", pacienteId);
    }

    public List<Consulta> buscarPorMedicoId(Long medicoId) {
        return list("medico.id", medicoId);
    }

    public List<Consulta> buscarPorStatus(StatusConsulta status) {
        return list("status", status);
    }

    public List<Consulta> buscarEntreDatas(LocalDateTime inicio, LocalDateTime fim) {
        return list("dataHora between ?1 and ?2", inicio, fim);
    }

    // ===== Regras de conflito (create) =====
    public boolean existeConsultaParaMedicoNoHorario(Long medicoId, LocalDateTime dataHora) {
        return count("medico.id = ?1 and dataHora = ?2", medicoId, dataHora) > 0;
    }

    public boolean existeConsultaParaPacienteNoHorario(Long pacienteId, LocalDateTime dataHora) {
        return count("paciente.id = ?1 and dataHora = ?2", pacienteId, dataHora) > 0;
    }

    // ===== Regras de conflito (update: ignora a própria consulta) =====
    public boolean existeConsultaParaMedicoNoHorarioExceto(Long consultaId, Long medicoId, LocalDateTime dataHora) {
        return count("id <> ?1 and medico.id = ?2 and dataHora = ?3", consultaId, medicoId, dataHora) > 0;
    }

    public boolean existeConsultaParaPacienteNoHorarioExceto(Long consultaId, Long pacienteId, LocalDateTime dataHora) {
        return count("id <> ?1 and paciente.id = ?2 and dataHora = ?3", consultaId, pacienteId, dataHora) > 0;
    }
}