package br.com.rafaeldiniz.resource;

import java.util.List;

import br.com.rafaeldiniz.dto.request.ItemReceitaRequest;
import br.com.rafaeldiniz.dto.response.ItemReceitaResponse;
import br.com.rafaeldiniz.model.entity.ItemReceita;
import br.com.rafaeldiniz.service.ItemReceitaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/itens-receita")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ItemReceitaResource {

    @Inject
    ItemReceitaService itemReceitaService;

    @POST
    public Response criar(@Valid ItemReceitaRequest request) {
        itemReceitaService.criarItem(request);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, @Valid ItemReceitaRequest request) {
        itemReceitaService.atualizarItem(id, request);
        return Response.noContent().build();
    }

    @GET
    public List<ItemReceitaResponse> listar() {
        return itemReceitaService.listar()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @GET
    @Path("/{id}")
    public ItemReceitaResponse buscarPorId(@PathParam("id") Long id) {
        return toResponse(itemReceitaService.buscarPorId(id));
    }

    @GET
    @Path("/receita/{receitaId}")
    public List<ItemReceitaResponse> buscarPorReceita(@PathParam("receitaId") Long receitaId) {
        return itemReceitaService.buscarPorReceitaId(receitaId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        itemReceitaService.deletar(id);
        return Response.noContent().build();
    }

    private ItemReceitaResponse toResponse(ItemReceita i) {
        return new ItemReceitaResponse(
                i.getId(),
                i.getReceita().getId(),
                i.getMedicamento(),
                i.getDosagem(),
                i.getFrequencia(),
                i.getDuracao(),
                i.getVia(),
                i.getInstrucoes()
        );
    }
}