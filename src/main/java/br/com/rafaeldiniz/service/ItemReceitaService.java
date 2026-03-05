package br.com.rafaeldiniz.service;

import java.util.List;

import br.com.rafaeldiniz.dto.request.ItemReceitaRequest;
import br.com.rafaeldiniz.model.entity.ItemReceita;
import br.com.rafaeldiniz.model.entity.Receita;
import br.com.rafaeldiniz.repository.ItemReceitaRepository;
import br.com.rafaeldiniz.repository.ReceitaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class ItemReceitaService {

    @Inject
    ItemReceitaRepository itemReceitaRepository;

    @Inject
    ReceitaRepository receitaRepository;

    @Transactional
    public void criarItem(ItemReceitaRequest request) {

        Receita receita = receitaRepository.findById(request.receitaId());
        if (receita == null) {
            throw new NotFoundException("Receita não encontrada");
        }

        // evitar medicamento duplicado na mesma receita
        if (itemReceitaRepository
                .buscarPorReceitaIdEMedicamento(request.receitaId(), request.medicamento())
                .isPresent()) {

            throw new WebApplicationException(
                "Este medicamento já foi adicionado à receita",
                Response.Status.CONFLICT
            );
        }

        ItemReceita item = new ItemReceita();
        item.setReceita(receita);
        item.setMedicamento(request.medicamento());
        item.setDosagem(request.dosagem());
        item.setFrequencia(request.frequencia());
        item.setDuracao(request.duracao());
        item.setVia(request.via());
        item.setInstrucoes(request.instrucoes());

        itemReceitaRepository.persist(item);
    }

    @Transactional
    public void atualizarItem(Long id, ItemReceitaRequest request) {

        ItemReceita item = itemReceitaRepository.findById(id);
        if (item == null) {
            throw new NotFoundException("Item da receita não encontrado");
        }

        // se trocar medicamento, verificar duplicidade
        itemReceitaRepository
            .buscarPorReceitaIdEMedicamento(item.getReceita().getId(), request.medicamento())
            .ifPresent(outro -> {
                if (!outro.getId().equals(id)) {
                    throw new WebApplicationException(
                        "Este medicamento já está na receita",
                        Response.Status.CONFLICT
                    );
                }
            });

        item.setMedicamento(request.medicamento());
        item.setDosagem(request.dosagem());
        item.setFrequencia(request.frequencia());
        item.setDuracao(request.duracao());
        item.setVia(request.via());
        item.setInstrucoes(request.instrucoes());
    }

    public ItemReceita buscarPorId(Long id) {
        ItemReceita item = itemReceitaRepository.findById(id);
        if (item == null) {
            throw new NotFoundException("Item da receita não encontrado");
        }
        return item;
    }

    public List<ItemReceita> listar() {
        return itemReceitaRepository.listAll();
    }

    public List<ItemReceita> buscarPorReceitaId(Long receitaId) {
        return itemReceitaRepository.buscarPorReceitaId(receitaId);
    }

    @Transactional
    public void deletar(Long id) {
        boolean removido = itemReceitaRepository.deleteById(id);
        if (!removido) {
            throw new NotFoundException("Item da receita não encontrado");
        }
    }
}