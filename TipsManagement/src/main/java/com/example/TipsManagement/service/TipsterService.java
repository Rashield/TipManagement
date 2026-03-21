package com.example.TipsManagement.service;

import com.example.TipsManagement.Exception.BadRequestException;
import com.example.TipsManagement.Exception.BusinessException;
import com.example.TipsManagement.Exception.NotFoundException;
import com.example.TipsManagement.controller.dto.TipsterRequest;
import com.example.TipsManagement.model.LoggedUser;
import com.example.TipsManagement.model.Tipster;
import com.example.TipsManagement.model.Usuario;
import com.example.TipsManagement.repository.ITipsterRepository;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class TipsterService {

    private final ITipsterRepository tipsterRepository;
    private final ObjectMapper mapper;

    public TipsterService(ITipsterRepository iTipsterRepository, ObjectMapper mapper) {
        this.tipsterRepository = iTipsterRepository;
        this.mapper = mapper;
    }

    public Tipster save(Long id, TipsterRequest tipsterRequest){
        if(tipsterRepository.existsByName(tipsterRequest.getName())){
            throw new BusinessException("Já existe um tipster com esse nome");
        }
        if(tipsterRequest.getName().length()<3){
            throw new BadRequestException("Número de caracteres insuficiente (min. 3)");
        }

        Tipster tipster = mapper.convertValue(tipsterRequest,Tipster.class);
        //Usa o ID recebido para salvar o usuario_id do tipster
        Usuario usuario = new Usuario();
        usuario.setId(id);
        tipster.setUsuario(usuario);
        return tipsterRepository.save(tipster);
    }

    public List<Tipster> listAll(Long id){
        List<Tipster> list = tipsterRepository.findAllByUsuarioId(id);
        if (list.isEmpty()){
            throw new NotFoundException("Não existe Tipster cadastrado.");
        }
        return list;
    }

    public Tipster edit(Long id,TipsterRequest tipsterRequest){
        if(tipsterRequest.getName().length()<3){
            throw new BadRequestException("Número de caracteres insuficiente (min. 3)");
        }
        Tipster tipster  = new Tipster();
        tipster.setId(id);
        tipster.setName(tipsterRequest.getName());

        return tipsterRepository.save(tipster);
    }

    public void delete(Long id){
        Tipster tipster = tipsterRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Tipster não encontrado"));

        tipsterRepository.delete(tipster);
    }
}
