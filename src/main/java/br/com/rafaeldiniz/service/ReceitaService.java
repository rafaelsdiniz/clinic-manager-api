package br.com.rafaeldiniz.service;

import java.util.List;

import br.com.rafaeldiniz.dto.request.ReceitaRequest;
import br.com.rafaeldiniz.model.entity.Prontuario;
import br.com.rafaeldiniz.model.entity.Receita;
import br.com.rafaeldiniz.repository.ProntuarioRepository;
import br.com.rafaeldiniz.repository.ReceitaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class ReceitaService {

    @Inject
    ReceitaRepository receitaRepository;

    @Inject
    ProntuarioRepository prontuarioRepository;

    @Transactional
    public void criarReceita(ReceitaRequest request) {

        Prontuario prontuario = prontuarioRepository.findById(request.prontuarioId());
        if (prontuario == null) {
            throw new NotFoundException("Prontuário não encontrado");
        }

        // regra 1:1 prontuário -> receita
        if (receitaRepository.buscarPorProntuarioId(request.prontuarioId()).isPresent()) {
            throw new WebApplicationException(
                "Já existe receita para este prontuário",
                Response.Status.CONFLICT
            );
        }

        Receita receita = new Receita();
        receita.setProntuario(prontuario);

        // criadoEm deixamos o @PrePersist cuidar
        receita.setObservacoes(request.observacoes());

        receitaRepository.persist(receita);
    }

    @Transactional
    public void atualizarReceita(Long id, ReceitaRequest request) {

        Receita receita = receitaRepository.findById(id);
        if (receita == null) {
            throw new NotFoundException("Receita não encontrada");
        }

        // não trocamos prontuário para manter integridade
        receita.setObservacoes(request.observacoes());
    }

    public Receita buscarPorId(Long id) {
        Receita receita = receitaRepository.findById(id);
        if (receita == null) {
            throw new NotFoundException("Receita não encontrada");
        }
        return receita;
    }

    public Receita buscarPorProntuarioId(Long prontuarioId) {
        return receitaRepository.buscarPorProntuarioId(prontuarioId)
                .orElseThrow(() -> new NotFoundException("Receita não encontrada para este prontuário"));
    }

    public List<Receita> listar() {
        return receitaRepository.listAll();
    }

    @Transactional
    public void deletar(Long id) {
        boolean removido = receitaRepository.deleteById(id);
        if (!removido) {
            throw new NotFoundException("Receita não encontrada");
        }
    }
}