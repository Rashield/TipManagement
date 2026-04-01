package com.example.TipsManagement.service;

import com.example.TipsManagement.Exception.NotFoundException;
import com.example.TipsManagement.model.Banca;
import com.example.TipsManagement.model.BetHouse;
import com.example.TipsManagement.model.Tipster;
import com.example.TipsManagement.model.dto.Request.BancaRequest;
import com.example.TipsManagement.model.dto.Response.BancaResponse;
import com.example.TipsManagement.model.dto.Response.BetHouseResponse;
import com.example.TipsManagement.model.dto.Response.TipsterResponse;
import com.example.TipsManagement.repository.IBancaRepository;
import com.example.TipsManagement.repository.IBetHouseRepository;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

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

    public List<BancaResponse> listAll(Long userId){
        List<Banca> list = bancaRepository.findAllByUsuarioId(userId);
        if(list.isEmpty()){
            throw new NotFoundException("Não existe Banca cadastrada.");
        }
        //Retorna a lista convertida para BancaResponse, evitando passar dados sensíveis do usuario cadastrado (senha)
        return list.stream()
                .map(banca -> mapper.convertValue(banca, BancaResponse.class))
                .toList();
    }

    public BancaResponse list(Long userId, Long id){
        Banca banca = bancaRepository.findByIdAndUsuarioId(id, userId)
                .orElseThrow(()->
                        new NotFoundException("Não existe Banca com os parâmetros fornecidos."));
        return mapper.convertValue(banca, BancaResponse.class);
    }
}
