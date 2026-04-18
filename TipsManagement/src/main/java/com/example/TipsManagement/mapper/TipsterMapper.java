package com.example.TipsManagement.mapper;

import com.example.TipsManagement.model.Tipster;
import com.example.TipsManagement.model.dto.Response.TipsterResponse;
import org.springframework.stereotype.Component;

@Component
public class TipsterMapper {
    public TipsterResponse toResponse(Tipster tipster){
        TipsterResponse tipsterResponse = new TipsterResponse();
        tipsterResponse.setId(tipster.getId());
        tipsterResponse.setName(tipster.getName());
        return tipsterResponse;
    }
}
