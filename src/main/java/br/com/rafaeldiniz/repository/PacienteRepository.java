package br.com.rafaeldiniz.repository;

import java.util.List;
import java.util.Optional;

import br.com.rafaeldiniz.model.entity.Paciente;
import br.com.rafaeldiniz.model.valueObject.Cpf;
import br.com.rafaeldiniz.model.valueObject.Email;
import br.com.rafaeldiniz.model.valueObject.Telefone;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PacienteRepository implements PanacheRepository<Paciente> {

    public Optional<Paciente> buscarPorCpf(Cpf cpf) {
        return find("cpf", cpf).firstResultOptional();
    }

    public Optional<Paciente> buscarPorEmail(Email email) {
        return find("email", email).firstResultOptional();
    }

    public List<Paciente> buscarPorTelefone(Telefone telefone) {
        return find("telefone", telefone).list();
    }

    public List<Paciente> buscarPorNome(String nome) {
        return find("LOWER(nome) like ?1", "%" + nome.toLowerCase() + "%").list();
    }
}