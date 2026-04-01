package com.example.TipsManagement.mapper;

import com.example.TipsManagement.model.Banca;
import com.example.TipsManagement.model.dto.Response.BancaResponse;
import com.example.TipsManagement.model.dto.Response.BetHouseResponse;
import org.springframework.stereotype.Component;

@Component
public class BancaMapper {

    public BancaResponse toResponse(Banca banca) {
        BancaResponse response = new BancaResponse();
        response.setId(banca.getId());
        response.setBalance(banca.getBalance());

        BetHouseResponse betHouse = new BetHouseResponse();
        betHouse.setId(banca.getBetHouse().getId());
        betHouse.setName(banca.getBetHouse().getName());

        response.setBetHouseResponse(betHouse);

        return response;
    }
}