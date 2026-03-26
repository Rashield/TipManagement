package com.example.TipsManagement.service;

import com.example.TipsManagement.Exception.NotFoundException;
import com.example.TipsManagement.model.Banca;
import com.example.TipsManagement.model.BetHouse;
import com.example.TipsManagement.model.dto.Request.BancaRequest;
import com.example.TipsManagement.model.dto.Response.BancaResponse;
import com.example.TipsManagement.model.dto.Response.BetHouseResponse;
import com.example.TipsManagement.repository.IBancaRepository;
import com.example.TipsManagement.repository.IBetHouseRepository;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class BancaService {
    private final IBancaRepository bancaRepository;
    private final ObjectMapper mapper;
    private final IBetHouseRepository betHouseRepository;

    public BancaService(IBancaRepository bancaRepository, ObjectMapper mapper, IBetHouseRepository betHouseRepository) {
        this.bancaRepository = bancaRepository;
        this.mapper = mapper;
        this.betHouseRepository = betHouseRepository;
    }

    public BancaResponse save(Long userId, Long betHouseId, BancaRequest bancaRequest) {

        BetHouse betHouse = betHouseRepository
                .findByIdAndUsuarioId(betHouseId, userId)
                .orElseThrow(() -> new NotFoundException("Casa de aposta não encontrada"));

        Banca banca = new Banca();
        banca.setBalance(bancaRequest.getAmount());
        banca.setBetHouse(betHouse);
        Banca savedBanca = bancaRepository.save(banca);
        BancaResponse bancaResponse = new BancaResponse();
        bancaResponse.setBetHouseResponse(mapper.convertValue(savedBanca.getBetHouse(), BetHouseResponse.class));
        bancaResponse.setBalance(savedBanca.getBalance());
        bancaResponse.setId(savedBanca.getId());
        return bancaResponse;
    }
}
