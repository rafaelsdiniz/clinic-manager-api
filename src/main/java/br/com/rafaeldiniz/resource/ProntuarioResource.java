package br.com.rafaeldiniz.resource;

import java.util.List;

import br.com.rafaeldiniz.dto.request.ProntuarioRequest;
import br.com.rafaeldiniz.dto.response.ProntuarioResponse;
import br.com.rafaeldiniz.model.entity.Prontuario;
import br.com.rafaeldiniz.service.ProntuarioService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/prontuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProntuarioResource {

    @Inject
    ProntuarioService prontuarioService;

    @POST
    public Response criar(@Valid ProntuarioRequest request) {
        prontuarioService.criarProntuario(request);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, @Valid ProntuarioRequest request) {
        prontuarioService.atualizarProntuario(id, request);
        return Response.noContent().build();
    }

    @GET
    public List<ProntuarioResponse> listar() {
        return prontuarioService.listar().stream()
                .map(this::toResponse)
                .toList();
    }

    @GET
    @Path("/{id}")
    public ProntuarioResponse buscarPorId(@PathParam("id") Long id) {
        return toResponse(prontuarioService.buscarPorId(id));
    }

    @GET
    @Path("/consulta/{consultaId}")
    public ProntuarioResponse buscarPorConsulta(@PathParam("consultaId") Long consultaId) {
        return toResponse(prontuarioService.buscarPorConsultaId(consultaId));
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        prontuarioService.deletar(id);
        return Response.noContent().build();
    }

    private ProntuarioResponse toResponse(Prontuario p) {
        return new ProntuarioResponse(
                p.getId(),
                p.getConsulta().getId(),
                p.getCriadoEm(),
                p.getQueixaPrincipal(),
                p.getAnamnese(),
                p.getExameFisico(),
                p.getDiagnostico(),
                p.getConduta(),
                p.getObservacoes()
        );
    }
}