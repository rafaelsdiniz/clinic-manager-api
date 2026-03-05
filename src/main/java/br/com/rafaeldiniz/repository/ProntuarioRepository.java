package br.com.rafaeldiniz.repository;

import java.util.Optional;

import br.com.rafaeldiniz.model.entity.Prontuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProntuarioRepository implements PanacheRepository<Prontuario> {
    public Optional<Prontuario> buscarPorConsultaId(Long consultaId) {
        return find("consulta.id", consultaId).firstResultOptional();
    }
} 
