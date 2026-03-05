package br.com.rafaeldiniz.repository;

import java.util.Optional;

import br.com.rafaeldiniz.model.entity.Receita;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ReceitaRepository implements PanacheRepository<Receita> {
    public Optional<Receita> buscarPorProntuarioId(Long prontuarioId) {
        return find("prontuario.id", prontuarioId).firstResultOptional();
    }
} 
