package com.example.TipsManagement.service;

import com.example.TipsManagement.Exception.BadRequestException;
import com.example.TipsManagement.Exception.BusinessException;
import com.example.TipsManagement.Exception.NotFoundException;
import com.example.TipsManagement.controller.dto.TipsterRequest;
import com.example.TipsManagement.model.Tipster;
import com.example.TipsManagement.repository.ITipsterRepository;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

@Service
public class TipsterService {

    private final ITipsterRepository iTipsterRepository;
    private final ObjectMapper mapper;

    public TipsterService(ITipsterRepository iTipsterRepository, ObjectMapper mapper) {
        this.iTipsterRepository = iTipsterRepository;
        this.mapper = mapper;
    }

    public Tipster save(TipsterRequest tipsterRequest){
        if(iTipsterRepository.existsByName(tipsterRequest.getName())){
            throw new BusinessException("Já existe um tipster com esse nome");
        }
        if(tipsterRequest.getName().length()<3){
            throw new BadRequestException("Número de caracteres insuficiente (min. 3)");
        }
        Tipster tipster = mapper.convertValue(tipsterRequest,Tipster.class);
        return iTipsterRepository.save(tipster);
    }

    public List<Tipster> listAll(){
        List<Tipster> list = iTipsterRepository.findAll();
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

        return iTipsterRepository.save(tipster);
    }

    public void delete(Long id){
        Tipster tipster = iTipsterRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Tipster não encontrado"));

        iTipsterRepository.delete(tipster);
    }
}
