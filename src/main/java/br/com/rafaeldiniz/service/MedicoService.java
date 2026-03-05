package br.com.rafaeldiniz.service;

import java.util.List;

import br.com.rafaeldiniz.dto.request.MedicoRequest;
import br.com.rafaeldiniz.model.entity.Medico;
import br.com.rafaeldiniz.model.valueObject.Cpf;
import br.com.rafaeldiniz.model.valueObject.Crm;
import br.com.rafaeldiniz.model.valueObject.Email;
import br.com.rafaeldiniz.model.valueObject.Telefone;
import br.com.rafaeldiniz.repository.MedicoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class MedicoService {

    @Inject
    private MedicoRepository medicoRepository;

    @Transactional
    public void criarMedico(MedicoRequest request) {

        // ✅ evita estourar constraint no banco e virar erro 500
        if (medicoRepository.buscarPorCrm(request.crm()).isPresent()) {
            throw new WebApplicationException("CRM já cadastrado", Response.Status.CONFLICT);
        }
        if (medicoRepository.buscarPorCpf(request.cpf()).isPresent()) {
            throw new WebApplicationException("CPF já cadastrado", Response.Status.CONFLICT);
        }
        if (request.email() != null && medicoRepository.buscarPorEmail(request.email()).isPresent()) {
            throw new WebApplicationException("Email já cadastrado", Response.Status.CONFLICT);
        }

        Medico medico = new Medico();
        medico.setNome(request.nome());
        medico.setCpf(request.cpf());
        medico.setCrm(request.crm());
        medico.setEmail(request.email());
        medico.setTelefone(request.telefone());
        medico.setEspecialidade(request.especialidade());
        medico.setEndereco(request.endereco());
        medico.setAtivo(request.ativo());

        medicoRepository.persist(medico);
    }

    @Transactional
public void atualizarCadastroMedico(Long id, MedicoRequest request) {
    Medico medico = medicoRepository.findById(id);
    if (medico == null) {
        throw new NotFoundException("Médico não encontrado");
    }

    // ========= Regras de duplicidade (exclui o próprio id) =========

    // CRM (crítico)
    medicoRepository.buscarPorCrm(request.crm()).ifPresent(outro -> {
        if (!outro.getId().equals(id)) {
            throw new WebApplicationException("CRM já cadastrado", Response.Status.CONFLICT);
        }
    });

    // CPF (crítico)
    medicoRepository.buscarPorCpf(request.cpf()).ifPresent(outro -> {
        if (!outro.getId().equals(id)) {
            throw new WebApplicationException("CPF já cadastrado", Response.Status.CONFLICT);
        }
    });

    // Email (crítico, mas pode ser null)
    if (request.email() != null) {
        medicoRepository.buscarPorEmail(request.email()).ifPresent(outro -> {
            if (!outro.getId().equals(id)) {
                throw new WebApplicationException("Email já cadastrado", Response.Status.CONFLICT);
            }
        });
    }

    medico.setNome(request.nome());
    medico.setCpf(request.cpf());
    medico.setCrm(request.crm());
    medico.setEspecialidade(request.especialidade());
    medico.setTelefone(request.telefone());
    medico.setEmail(request.email());
    medico.setEndereco(request.endereco());
    medico.setAtivo(request.ativo());
}

    public List<Medico> listarMedicos() {
        return medicoRepository.listAll();
    }

    public Medico buscarPorCrm(Crm crm) {
        return medicoRepository.buscarPorCrm(crm)
                .orElseThrow(() -> new NotFoundException("Médico não encontrado"));
    }

    public Medico buscarPorCpf(Cpf cpf) {
        return medicoRepository.buscarPorCpf(cpf)
                .orElseThrow(() -> new NotFoundException("Médico não encontrado"));
    }

    public Medico buscarPorEmail(Email email) {
        return medicoRepository.buscarPorEmail(email)
                .orElseThrow(() -> new NotFoundException("Médico não encontrado"));
    }

    public List<Medico> buscarPorNome(String nome) {
        return medicoRepository.buscarPorNome(nome);
    }

    public List<Medico> buscarPorTelefone(Telefone telefone) {
        return medicoRepository.buscarPorTelefone(telefone);
    }

    @Transactional
    public void deletarMedico(Long id) {
        boolean removido = medicoRepository.deleteById(id);
        if (!removido) {
            throw new NotFoundException("Médico não encontrado");
        }
    }
}