package br.com.rafaeldiniz.resource;

import java.time.LocalDateTime;
import java.util.List;

import br.com.rafaeldiniz.dto.request.ConsultaRequest;
import br.com.rafaeldiniz.dto.response.ConsultaResponse;
import br.com.rafaeldiniz.model.entity.Consulta;
import br.com.rafaeldiniz.model.enums.StatusConsulta;
import br.com.rafaeldiniz.service.ConsultaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/consultas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ConsultaResource {

    @Inject
    ConsultaService consultaService;

    @POST
    public Response criar(@Valid ConsultaRequest request) {
        consultaService.criarConsulta(request);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") Long id, @Valid ConsultaRequest request) {
        consultaService.atualizarConsulta(id, request);
        return Response.noContent().build();
    }

    @GET
    public List<ConsultaResponse> listar() {
        return consultaService.listarConsultas()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @GET
    @Path("/{id}")
    public ConsultaResponse buscarPorId(@PathParam("id") Long id) {
        return toResponse(consultaService.buscarPorId(id));
    }

    @GET
    @Path("/paciente/{pacienteId}")
    public List<ConsultaResponse> buscarPorPaciente(@PathParam("pacienteId") Long pacienteId) {
        return consultaService.buscarPorPacienteId(pacienteId).stream()
                .map(this::toResponse).toList();
    }

    @GET
    @Path("/medico/{medicoId}")
    public List<ConsultaResponse> buscarPorMedico(@PathParam("medicoId") Long medicoId) {
        return consultaService.buscarPorMedicoId(medicoId).stream()
                .map(this::toResponse).toList();
    }

    @GET
    @Path("/status/{status}")
    public List<ConsultaResponse> buscarPorStatus(@PathParam("status") StatusConsulta status) {
        return consultaService.buscarPorStatus(status).stream()
                .map(this::toResponse).toList();
    }

    // /consultas/periodo?inicio=2026-03-05T08:00:00&fim=2026-03-05T18:00:00
    @GET
    @Path("/periodo")
    public List<ConsultaResponse> buscarPorPeriodo(
            @QueryParam("inicio") LocalDateTime inicio,
            @QueryParam("fim") LocalDateTime fim
    ) {
        return consultaService.buscarEntreDatas(inicio, fim).stream()
                .map(this::toResponse).toList();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") Long id) {
        consultaService.deletarConsulta(id);
        return Response.noContent().build();
    }

    private ConsultaResponse toResponse(Consulta c) {
        return new ConsultaResponse(
                c.getId(),
                c.getPaciente().getId(),
                c.getMedico().getId(),
                c.getDataHora(),
                c.getObservacoes(),
                c.getStatus()
        );
    }
}