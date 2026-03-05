package br.com.rafaeldiniz.repository;

import br.com.rafaeldiniz.model.entity.Medico;
import br.com.rafaeldiniz.model.valueObject.Cpf;
import br.com.rafaeldiniz.model.valueObject.Crm;
import br.com.rafaeldiniz.model.valueObject.Email;
import br.com.rafaeldiniz.model.valueObject.Telefone;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class MedicoRepository implements PanacheRepository<Medico> {

    public Optional<Medico> buscarPorCrm(Crm crm) {
        return find("crm", crm).firstResultOptional();
    }

    public Optional<Medico> buscarPorCpf(Cpf cpf) {
        return find("cpf", cpf).firstResultOptional();
    }

    public Optional<Medico> buscarPorEmail(Email email) {
        return find("email", email).firstResultOptional();
    }

    public List<Medico> buscarPorTelefone(Telefone telefone) {
        return find("telefone", telefone).list();
    }

    public List<Medico> buscarPorNome(String nome) {
        return find("LOWER(nome) like ?1", "%" + nome.toLowerCase() + "%").list();
    }
}