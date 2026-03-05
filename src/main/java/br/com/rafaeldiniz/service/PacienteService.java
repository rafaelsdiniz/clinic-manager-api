package br.com.rafaeldiniz.service;

import java.util.List;

import br.com.rafaeldiniz.dto.request.PacienteRequest;
import br.com.rafaeldiniz.model.entity.Paciente;
import br.com.rafaeldiniz.model.valueObject.Cpf;
import br.com.rafaeldiniz.model.valueObject.Email;
import br.com.rafaeldiniz.model.valueObject.Telefone;
import br.com.rafaeldiniz.repository.PacienteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class PacienteService {

    @Inject
    private PacienteRepository pacienteRepository;

    @Transactional
    public void criarPaciente(PacienteRequest request) {

        if (pacienteRepository.buscarPorCpf(request.cpf()).isPresent()) {
            throw new WebApplicationException("CPF já cadastrado", Response.Status.CONFLICT);
        }

        if (request.email() != null && pacienteRepository.buscarPorEmail(request.email()).isPresent()) {
            throw new WebApplicationException("Email já cadastrado", Response.Status.CONFLICT);
        }

        Paciente paciente = new Paciente();
        paciente.setNome(request.nome());
        paciente.setCpf(request.cpf());
        paciente.setEmail(request.email());
        paciente.setTelefone(request.telefone());
        paciente.setDataNascimento(request.dataNascimento());
        paciente.setEndereco(request.endereco());

        pacienteRepository.persist(paciente);
    }

    @Transactional
    public void atualizarCadastroPaciente(Long id, PacienteRequest request) {
        Paciente paciente = pacienteRepository.findById(id);
        if (paciente == null) {
            throw new NotFoundException("Paciente não encontrado");
        }

        pacienteRepository.buscarPorCpf(request.cpf()).ifPresent(outro -> {
            if (!outro.getId().equals(id)) {
                throw new WebApplicationException("CPF já cadastrado", Response.Status.CONFLICT);
            }
        });

        if (request.email() != null) {
            pacienteRepository.buscarPorEmail(request.email()).ifPresent(outro -> {
                if (!outro.getId().equals(id)) {
                    throw new WebApplicationException("Email já cadastrado", Response.Status.CONFLICT);
                }
            });
        }

        paciente.setNome(request.nome());
        paciente.setCpf(request.cpf());
        paciente.setEmail(request.email());
        paciente.setTelefone(request.telefone());
        paciente.setDataNascimento(request.dataNascimento());
        paciente.setEndereco(request.endereco());

    }

    public List<Paciente> listarPacientes() {
        return pacienteRepository.listAll();
    }

    public List<Paciente> buscarPacientePorNome(String nome) {
        return pacienteRepository.buscarPorNome(nome);
    }

    public Paciente buscarPacientePorCpf(Cpf cpf) {
        return pacienteRepository.buscarPorCpf(cpf)
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado"));
    }

    public Paciente buscarPacientePorEmail(Email email) {
        return pacienteRepository.buscarPorEmail(email)
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado"));
    }

    public List<Paciente> buscarPacientePorTelefone(Telefone telefone) {
        return pacienteRepository.buscarPorTelefone(telefone);
    }

    @Transactional
    public void deletarPaciente(Long id) {
        boolean removido = pacienteRepository.deleteById(id);
        if (!removido) {
            throw new NotFoundException("Paciente não encontrado");
        }
    }
}