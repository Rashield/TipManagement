package com.example.TipsManagement.service;

import com.example.TipsManagement.Exception.BadRequestException;
import com.example.TipsManagement.Exception.BusinessException;
import com.example.TipsManagement.Exception.NotFoundException;
import com.example.TipsManagement.mapper.TipsterMapper;
import com.example.TipsManagement.model.dto.Request.TipsterRequest;
import com.example.TipsManagement.model.Tipster;
import com.example.TipsManagement.model.Usuario;
import com.example.TipsManagement.model.dto.Response.TipsterResponse;
import com.example.TipsManagement.repository.ITipsterRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipsterService {

    private final ITipsterRepository tipsterRepository;
    private final TipsterMapper tipsterMapper;

    public TipsterService(ITipsterRepository iTipsterRepository, ObjectMapper mapper, TipsterMapper tipsterMapper) {
        this.tipsterRepository = iTipsterRepository;
        this.tipsterMapper = tipsterMapper;
    }

    public TipsterResponse save(Long userId, TipsterRequest tipsterRequest){
        if(tipsterRepository.existsByName(tipsterRequest.getName())){
            throw new BusinessException("Já existe um tipster com esse nome");
        }
        validateLength(tipsterRequest.getName());

        Tipster tipster = new Tipster();
        tipster.setName(tipsterRequest.getName());
        //Usa o ID recebido para salvar o usuario_id do tipster
        Usuario usuario = new Usuario();
        usuario.setId(userId);
        tipster.setUsuario(usuario);
        return tipsterMapper.toResponse(tipsterRepository.save(tipster));
    }

    public List<TipsterResponse> listAll(Long userId){
        List<Tipster> list = tipsterRepository.findAllByUsuarioId(userId);
        if(list.isEmpty()){
            throw new NotFoundException("Não existe Tipster cadastrado.");
        }
        //Retorna a lista convertida para TipsterResponse, evitando passar dados do usuario cadastrado
        return list.stream()
                .map(tipster -> tipsterMapper.toResponse(tipster))
                .toList();
    }

    public TipsterResponse edit(Long userId, Long id,TipsterRequest tipsterRequest){
        validateLength(tipsterRequest.getName());

        Tipster tipster = tipsterRepository.findByIdAndUsuarioId(id, userId)
                            .orElseThrow(() ->
                                    new NotFoundException("Tipster não encontrado"));

        tipster.setName(tipsterRequest.getName());
        return tipsterMapper.toResponse(tipster);
    }

    public void delete(Long userId, Long id){
        Tipster tipster = getOwnedTipster(id, userId);

        tipsterRepository.delete(tipster);
    }

    public Tipster getOwnedTipster(Long userId, Long id){
       return tipsterRepository.findByIdAndUsuarioId(id, userId)
                .orElseThrow(() ->
                        new NotFoundException("Tipster não encontrado"));
    }
    public void validateLength(String name){
        if (name == null || name.trim().length() < 3) {
            throw new BadRequestException("Nome deve ter no mínimo 3 caracteres");
        }
    }
}
