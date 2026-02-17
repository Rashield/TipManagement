package com.example.TipsManagement.service;

import com.example.TipsManagement.controller.dto.TipsterRequest;
import com.example.TipsManagement.model.Tipster;
import com.example.TipsManagement.repository.ITipsterRepository;
import org.springframework.stereotype.Service;
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
        Tipster tipster = mapper.convertValue(tipsterRequest,Tipster.class);
        return iTipsterRepository.save(tipster);
    }

    public List<Tipster> listAll(){
        List<Tipster> list = iTipsterRepository.findAll();
        for (Tipster tipster : list){
            System.out.println(tipster.);
        }

        return list;
    }
}
