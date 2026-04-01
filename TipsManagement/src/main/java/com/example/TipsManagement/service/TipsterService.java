package com.example.TipsManagement.service;

import com.example.TipsManagement.Exception.BadRequestException;
import com.example.TipsManagement.Exception.BusinessException;
import com.example.TipsManagement.Exception.NotFoundException;
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
    private final ObjectMapper mapper;

    public TipsterService(ITipsterRepository iTipsterRepository, ObjectMapper mapper) {
        this.tipsterRepository = iTipsterRepository;
        this.mapper = mapper;
    }

    public TipsterResponse save(Long userId, TipsterRequest tipsterRequest){
        if(tipsterRepository.existsByName(tipsterRequest.getName())){
            throw new BusinessException("Já existe um tipster com esse nome");
        }
        if(tipsterRequest.getName().length()<3){
            throw new BadRequestException("Número de caracteres insuficiente (min. 3)");
        }

        Tipster tipster = mapper.convertValue(tipsterRequest,Tipster.class);
        //Usa o ID recebido para salvar o usuario_id do tipster
        Usuario usuario = new Usuario();
        usuario.setId(userId);
        tipster.setUsuario(usuario);
        return mapper.convertValue(tipsterRepository.save(tipster), TipsterResponse.class);
    }

    public List<TipsterResponse> listAll(Long userId){
        List<Tipster> list = tipsterRepository.findAllByUsuarioId(userId);
        if(list.isEmpty()){
            throw new NotFoundException("Não existe Tipster cadastrado.");
        }
        //Retorna a lista convertida para TipsterResponse, evitando passar dados do usuario cadastrado
        return list.stream()
                .map(tipster -> mapper.convertValue(tipster, TipsterResponse.class))
                .toList();
    }

    public TipsterResponse edit(Long userId, Long id,TipsterRequest tipsterRequest){
        if(tipsterRequest.getName().length()<3){
            throw new BadRequestException("Número de caracteres insuficiente (min. 3)");
        }

        Tipster tipster = tipsterRepository.findByIdAndUsuarioId(id, userId)
                            .orElseThrow(() ->
                                    new NotFoundException("Tipster não encontrado"));

        tipster.setName(tipsterRequest.getName());
        return mapper.convertValue(tipsterRepository.save(tipster), TipsterResponse.class);
    }

    public void delete(Long userId, Long id){
        Tipster tipster = tipsterRepository.findByIdAndUsuarioId(userId, id)
                .orElseThrow(() ->
                        new NotFoundException("Tipster não encontrado"));

        tipsterRepository.delete(tipster);
    }
}
