package com.example.TipsManagement.service;

import com.example.TipsManagement.Exception.BadRequestException;
import com.example.TipsManagement.Exception.BusinessException;
import com.example.TipsManagement.Exception.NotFoundException;
import com.example.TipsManagement.model.Usuario;
import com.example.TipsManagement.model.dto.Request.BetHouseRequest;
import com.example.TipsManagement.model.BetHouse;
import com.example.TipsManagement.model.dto.Response.BetHouseResponse;
import com.example.TipsManagement.model.dto.Response.TipsterResponse;
import com.example.TipsManagement.repository.IBetHouseRepository;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;
import java.util.List;

@Service
public class BetHouseService {
    private final IBetHouseRepository betHouseRepository;
    private final ObjectMapper mapper;

    public BetHouseService(IBetHouseRepository betHouseRepository, ObjectMapper mapper) {
        this.betHouseRepository = betHouseRepository;
        this.mapper = mapper;
    }

    public BetHouseResponse save(Long userId, BetHouseRequest betHouseRequest){
        if(betHouseRepository.existsByUsuarioIdAndName(userId, betHouseRequest.getName())){
            throw new BusinessException("Já existe uma casa de apostas com esse nome.");
        }
        if(betHouseRequest.getName().length()<3){
            throw new BadRequestException("Número de caracteres insuficiente (min. 3)");
        }
        BetHouse betHouse = new BetHouse();
        betHouse.setName(betHouseRequest.getName());
        Usuario usuario = new Usuario();
        usuario.setId(userId);
        betHouse.setUsuario(usuario);
        return mapper.convertValue(betHouseRepository.save(betHouse), BetHouseResponse.class);
    }

    public List<BetHouseResponse> listAll(Long userId){
        List<BetHouse> list = betHouseRepository.findAllByUsuarioId(userId);
        if(list.isEmpty()){
            throw new NotFoundException("Nenhuma casa de apostas cadastrada.");
        }
        return list.stream()
                .map(betHouse -> mapper.convertValue(betHouse, BetHouseResponse.class))
                .toList();
    }

    public BetHouseResponse edit(Long userId, Long id, BetHouseRequest betHouseRequest){
        if(betHouseRepository.existsByUsuarioIdAndName(userId, betHouseRequest.getName())){
            throw new BusinessException("Já existe uma casa de apostas com esse nome.");
        }
        if(betHouseRequest.getName().length()<3){
            throw new BadRequestException("Número de caracteres insuficiente (min. 3)");
        }
        BetHouse betHouse = betHouseRepository.findByIdAndUsuarioId(id, userId)
                .orElseThrow(() ->
                        new NotFoundException("Não existe casa de Aposta com esse Id."));
        betHouse.setName(betHouseRequest.getName());
        return mapper.convertValue(betHouseRepository.save(betHouse), BetHouseResponse.class);
    }

    public void delete(Long userId, Long id){
        BetHouse betHouse = betHouseRepository.findByIdAndUsuarioId(id, userId)
                .orElseThrow(() ->
                        new NotFoundException("Não existe casa de Aposta com esse Id."));
        betHouseRepository.delete(betHouse);
    }
}
