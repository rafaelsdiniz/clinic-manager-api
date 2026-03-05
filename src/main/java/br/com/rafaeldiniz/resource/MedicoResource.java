package br.com.rafaeldiniz.resource;

import java.util.List;

import br.com.rafaeldiniz.dto.request.MedicoRequest;
import br.com.rafaeldiniz.model.entity.Medico;
import br.com.rafaeldiniz.model.valueObject.Cpf;
import br.com.rafaeldiniz.model.valueObject.Crm;
import br.com.rafaeldiniz.model.valueObject.Email;
import br.com.rafaeldiniz.model.valueObject.Telefone;
import br.com.rafaeldiniz.service.MedicoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/medicos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MedicoResource {

    @Inject
    MedicoService medicoService;

    @POST
    public Response criar(@Valid MedicoRequest request) {
        medicoService.criarMedico(request);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, @Valid MedicoRequest request) {
        medicoService.atualizarCadastroMedico(id, request);
        return Response.noContent().build();
    }

    @GET
    public List<Medico> listar() {
        return medicoService.listarMedicos();
    }

    @GET
    @Path("/nome/{nome}")
    public List<Medico> buscarPorNome(@PathParam("nome") String nome) {
        return medicoService.buscarPorNome(nome);
    }

    // Exemplo: /medicos/crm/12345/TO
    @GET
    @Path("/crm/{numero}/{uf}")
    public Medico buscarPorCrm(@PathParam("numero") String numero, @PathParam("uf") String uf) {
        return medicoService.buscarPorCrm(new Crm(numero, uf));
    }

    @GET
    @Path("/cpf/{cpf}")
    public Medico buscarPorCpf(@PathParam("cpf") String cpf) {
        return medicoService.buscarPorCpf(new Cpf(soDigitos(cpf)));
    }

    @GET
    @Path("/email/{email}")
    public Medico buscarPorEmail(@PathParam("email") String email) {
        return medicoService.buscarPorEmail(new Email(email));
    }

    @GET
    @Path("/telefone/{telefone}")
    public List<Medico> buscarPorTelefone(@PathParam("telefone") String telefone) {
        return medicoService.buscarPorTelefone(new Telefone(telefone));
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        medicoService.deletarMedico(id);
        return Response.noContent().build();
    }

    private String soDigitos(String s) {
        return s == null ? null : s.replaceAll("\\D", "");
    }
}