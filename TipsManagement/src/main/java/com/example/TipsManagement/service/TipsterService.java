package com.example.TipsManagement.service;

import com.example.TipsManagement.Exception.BadRequestException;
import com.example.TipsManagement.Exception.BusinessException;
import com.example.TipsManagement.controller.dto.TipsterRequest;
import com.example.TipsManagement.model.Tipster;
import com.example.TipsManagement.repository.ITipsterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

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
        return list;
    }
}
