package br.com.rafaeldiniz.resource;

import java.util.List;

import br.com.rafaeldiniz.dto.request.PacienteRequest;
import br.com.rafaeldiniz.model.entity.Paciente;
import br.com.rafaeldiniz.model.valueObject.Cpf;
import br.com.rafaeldiniz.model.valueObject.Email;
import br.com.rafaeldiniz.model.valueObject.Telefone;
import br.com.rafaeldiniz.service.PacienteService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/pacientes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PacienteResource {

    @Inject
    PacienteService pacienteService;

    @POST
    public Response criar(@Valid PacienteRequest request) {
        pacienteService.criarPaciente(request);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, @Valid PacienteRequest request) {
        pacienteService.atualizarCadastroPaciente(id, request);
        return Response.noContent().build();
    }

    @GET
    public List<Paciente> listar() {
        return pacienteService.listarPacientes();
    }

    @GET
    @Path("/nome/{nome}")
    public List<Paciente> buscarPorNome(@PathParam("nome") String nome) {
        return pacienteService.buscarPacientePorNome(nome);
    }

    @GET
    @Path("/cpf/{cpf}")
    public Paciente buscarPorCpf(@PathParam("cpf") String cpf) {
        return pacienteService.buscarPacientePorCpf(new Cpf(soDigitos(cpf)));
    }

    @GET
    @Path("/email/{email}")
    public Paciente buscarPorEmail(@PathParam("email") String email) {
        return pacienteService.buscarPacientePorEmail(new Email(email));
    }

    @GET
    @Path("/telefone/{telefone}")
    public List<Paciente> buscarPorTelefone(@PathParam("telefone") String telefone) {
        return pacienteService.buscarPacientePorTelefone(new Telefone(telefone));
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        pacienteService.deletarPaciente(id);
        return Response.noContent().build();
    }

    private String soDigitos(String s) {
        return s == null ? null : s.replaceAll("\\D", "");
    }
}