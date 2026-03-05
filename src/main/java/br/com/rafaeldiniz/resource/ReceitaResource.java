package br.com.rafaeldiniz.resource;

import java.util.List;

import br.com.rafaeldiniz.dto.request.ReceitaRequest;
import br.com.rafaeldiniz.dto.response.ReceitaResponse;
import br.com.rafaeldiniz.model.entity.Receita;
import br.com.rafaeldiniz.service.ReceitaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/receitas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ReceitaResource {

    @Inject
    ReceitaService receitaService;

    @POST
    public Response criar(@Valid ReceitaRequest request) {
        receitaService.criarReceita(request);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, @Valid ReceitaRequest request) {
        receitaService.atualizarReceita(id, request);
        return Response.noContent().build();
    }

    @GET
    public List<ReceitaResponse> listar() {
        return receitaService.listar()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @GET
    @Path("/{id}")
    public ReceitaResponse buscarPorId(@PathParam("id") Long id) {
        return toResponse(receitaService.buscarPorId(id));
    }

    @GET
    @Path("/prontuario/{prontuarioId}")
    public ReceitaResponse buscarPorProntuario(@PathParam("prontuarioId") Long prontuarioId) {
        return toResponse(receitaService.buscarPorProntuarioId(prontuarioId));
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        receitaService.deletar(id);
        return Response.noContent().build();
    }

    private ReceitaResponse toResponse(Receita r) {
        return new ReceitaResponse(
                r.getId(),
                r.getProntuario().getId(),
                r.getCriadoEm(),
                r.getObservacoes()
        );
    }
}