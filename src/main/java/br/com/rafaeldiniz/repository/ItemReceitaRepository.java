package br.com.rafaeldiniz.repository;

import java.util.List;
import java.util.Optional;

import br.com.rafaeldiniz.model.entity.ItemReceita;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemReceitaRepository implements PanacheRepository<ItemReceita> {
    public List<ItemReceita> buscarPorReceitaId(Long receitaId) {
        return list("receita.id", receitaId);
    }

    public Optional<ItemReceita> buscarPorReceitaIdEMedicamento(Long receitaId, String medicamento) {
        return find("receita.id = ?1 and medicamento = ?2", receitaId, medicamento)
                .firstResultOptional();
    }
} 
