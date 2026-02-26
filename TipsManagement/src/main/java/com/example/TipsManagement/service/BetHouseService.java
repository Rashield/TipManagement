package com.example.TipsManagement.service;

import com.example.TipsManagement.Exception.BadRequestException;
import com.example.TipsManagement.Exception.BusinessException;
import com.example.TipsManagement.controller.dto.BetHouseRequest;
import com.example.TipsManagement.model.BetHouse;
import com.example.TipsManagement.model.Tipster;
import com.example.TipsManagement.repository.IBetHouseRepository;
import tools.jackson.databind.ObjectMapper;

public class BetHouseService {
    private final IBetHouseRepository betHouseRepository;
    private final ObjectMapper mapper;

    public BetHouseService(IBetHouseRepository betHouseRepository, ObjectMapper mapper) {
        this.betHouseRepository = betHouseRepository;
        this.mapper = mapper;
    }

    public BetHouse save(BetHouseRequest betHouseRequest){
        if(betHouseRepository.existsByName(betHouseRequest.getName())){
            throw new BusinessException("Já existe uma casa de apostas com esse nome.");
        }
        if(betHouseRequest.getName().length()<3){
            throw new BadRequestException("Número de caracteres insuficiente (min. 3)");
        }
        BetHouse betHouse = mapper.convertValue(betHouseRequest,BetHouse.class);
        return betHouseRepository.save(betHouse);
    }
}
