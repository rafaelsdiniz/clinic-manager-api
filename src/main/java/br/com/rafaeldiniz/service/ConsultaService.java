package br.com.rafaeldiniz.service;

import java.time.LocalDateTime;
import java.util.List;

import br.com.rafaeldiniz.dto.request.ConsultaRequest;
import br.com.rafaeldiniz.model.entity.Consulta;
import br.com.rafaeldiniz.model.entity.Medico;
import br.com.rafaeldiniz.model.entity.Paciente;
import br.com.rafaeldiniz.model.enums.StatusConsulta;
import br.com.rafaeldiniz.repository.ConsultaRepository;
import br.com.rafaeldiniz.repository.MedicoRepository;
import br.com.rafaeldiniz.repository.PacienteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class ConsultaService {

    @Inject
    ConsultaRepository consultaRepository;

    @Inject
    PacienteRepository pacienteRepository;

    @Inject
    MedicoRepository medicoRepository;

    @Transactional
    public void criarConsulta(ConsultaRequest request) {

        Paciente paciente = pacienteRepository.findById(request.pacienteId());
        if (paciente == null) {
            throw new NotFoundException("Paciente não encontrado");
        }

        Medico medico = medicoRepository.findById(request.medicoId());
        if (medico == null) {
            throw new NotFoundException("Médico não encontrado");
        }
   
        if (request.dataHora().isBefore(LocalDateTime.now())) {
            throw new WebApplicationException("Não é permitido agendar consulta no passado", Response.Status.BAD_REQUEST);
        }

        if (consultaRepository.existeConsultaParaMedicoNoHorario(request.medicoId(), request.dataHora())) {
            throw new WebApplicationException("Médico já possui consulta nesse horário", Response.Status.CONFLICT);
        }

        if (consultaRepository.existeConsultaParaPacienteNoHorario(request.pacienteId(), request.dataHora())) {
            throw new WebApplicationException("Paciente já possui consulta nesse horário", Response.Status.CONFLICT);
        }

        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente);
        consulta.setMedico(medico);
        consulta.setDataHora(request.dataHora());
        consulta.setObservacoes(request.observacoes());
        consulta.setStatus(request.status());

        consultaRepository.persist(consulta);
    }

    @Transactional
    public void atualizarConsulta(Long id, ConsultaRequest request) {

        Consulta consulta = consultaRepository.findById(id);
        if (consulta == null) {
            throw new NotFoundException("Consulta não encontrada");
        }

        Paciente paciente = pacienteRepository.findById(request.pacienteId());
        if (paciente == null) {
            throw new NotFoundException("Paciente não encontrado");
        }

        Medico medico = medicoRepository.findById(request.medicoId());
        if (medico == null) {
            throw new NotFoundException("Médico não encontrado");
        }

        if (request.dataHora().isBefore(LocalDateTime.now())) {
            throw new WebApplicationException("Não é permitido agendar consulta no passado", Response.Status.BAD_REQUEST);
        }

        if (consultaRepository.existeConsultaParaMedicoNoHorarioExceto(id, request.medicoId(), request.dataHora())) {
            throw new WebApplicationException("Médico já possui consulta nesse horário", Response.Status.CONFLICT);
        }

        if (consultaRepository.existeConsultaParaPacienteNoHorarioExceto(id, request.pacienteId(), request.dataHora())) {
            throw new WebApplicationException("Paciente já possui consulta nesse horário", Response.Status.CONFLICT);
        }

        consulta.setPaciente(paciente);
        consulta.setMedico(medico);
        consulta.setDataHora(request.dataHora());
        consulta.setObservacoes(request.observacoes());
        consulta.setStatus(request.status());

    }

    public Consulta buscarPorId(Long id) {
        Consulta consulta = consultaRepository.findById(id);
        if (consulta == null) {
            throw new NotFoundException("Consulta não encontrada");
        }
        return consulta;
    }

    public List<Consulta> listarConsultas() {
        return consultaRepository.listAll();
    }

    @Transactional
    public void deletarConsulta(Long id) {
        boolean removido = consultaRepository.deleteById(id);
        if (!removido) {
            throw new NotFoundException("Consulta não encontrada");
        }
    }

    // ========= Buscas =========

    public List<Consulta> buscarPorPacienteId(Long pacienteId) {
        return consultaRepository.buscarPorPacienteId(pacienteId);
    }

    public List<Consulta> buscarPorMedicoId(Long medicoId) {
        return consultaRepository.buscarPorMedicoId(medicoId);
    }

    public List<Consulta> buscarPorStatus(StatusConsulta status) {
        return consultaRepository.buscarPorStatus(status);
    }

    public List<Consulta> buscarEntreDatas(LocalDateTime inicio, LocalDateTime fim) {
        if (inicio.isAfter(fim)) {
            throw new WebApplicationException("Data inicial não pode ser maior que a data final", Response.Status.BAD_REQUEST);
        }
        return consultaRepository.buscarEntreDatas(inicio, fim);
    }
}