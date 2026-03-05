package br.com.rafaeldiniz.service;

import java.util.List;

import br.com.rafaeldiniz.dto.request.ProntuarioRequest;
import br.com.rafaeldiniz.model.entity.Consulta;
import br.com.rafaeldiniz.model.entity.Prontuario;
import br.com.rafaeldiniz.repository.ConsultaRepository;
import br.com.rafaeldiniz.repository.ProntuarioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class ProntuarioService {

    @Inject
    ProntuarioRepository prontuarioRepository;

    @Inject
    ConsultaRepository consultaRepository;

    @Transactional
    public void criarProntuario(ProntuarioRequest request) {

        Consulta consulta = consultaRepository.findById(request.consultaId());
        if (consulta == null) {
            throw new NotFoundException("Consulta não encontrada");
        }

        // Regra: 1 prontuário por consulta
        if (prontuarioRepository.buscarPorConsultaId(request.consultaId()).isPresent()) {
            throw new WebApplicationException("Já existe prontuário para esta consulta", Response.Status.CONFLICT);
        }

        Prontuario prontuario = new Prontuario();
        prontuario.setConsulta(consulta);

        // ✅ criadoEm: ignorar request e deixar @PrePersist setar
        // prontuario.setCriadoEm(request.criadoEm());  // ❌ não recomendado

        prontuario.setQueixaPrincipal(request.queixaPrincipal());
        prontuario.setAnamnese(request.anamnese());
        prontuario.setExameFisico(request.exameFisico());
        prontuario.setDiagnostico(request.diagnostico());
        prontuario.setConduta(request.conduta());
        prontuario.setObservacoes(request.observacoes());

        prontuarioRepository.persist(prontuario);
    }

    @Transactional
    public void atualizarProntuario(Long id, ProntuarioRequest request) {
        Prontuario prontuario = prontuarioRepository.findById(id);
        if (prontuario == null) {
            throw new NotFoundException("Prontuário não encontrado");
        }

        // ✅ não trocar consulta (evita bagunçar a regra 1:1)
        // se você quiser permitir troca, tem que validar conflito + buscar consulta
        // e alterar consulta. Aqui vou manter padrão seguro.

        prontuario.setQueixaPrincipal(request.queixaPrincipal());
        prontuario.setAnamnese(request.anamnese());
        prontuario.setExameFisico(request.exameFisico());
        prontuario.setDiagnostico(request.diagnostico());
        prontuario.setConduta(request.conduta());
        prontuario.setObservacoes(request.observacoes());
        // ✅ sem persist()
    }

    public Prontuario buscarPorId(Long id) {
        Prontuario prontuario = prontuarioRepository.findById(id);
        if (prontuario == null) {
            throw new NotFoundException("Prontuário não encontrado");
        }
        return prontuario;
    }

    public List<Prontuario> listar() {
        return prontuarioRepository.listAll();
    }

    public Prontuario buscarPorConsultaId(Long consultaId) {
        return prontuarioRepository.buscarPorConsultaId(consultaId)
                .orElseThrow(() -> new NotFoundException("Prontuário não encontrado para esta consulta"));
    }

    @Transactional
    public void deletar(Long id) {
        boolean removido = prontuarioRepository.deleteById(id);
        if (!removido) {
            throw new NotFoundException("Prontuário não encontrado");
        }
    }
}