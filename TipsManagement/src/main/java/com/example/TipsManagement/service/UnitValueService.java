package com.example.TipsManagement.service;

import com.example.TipsManagement.controller.dto.UnitValueRequest;
import com.example.TipsManagement.model.UnitValue;
import com.example.TipsManagement.repository.IUnitValueRepository;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.Optional;

@Service
public class UnitValueService {

    private final IUnitValueRepository unitValueRepository;


    public UnitValueService(IUnitValueRepository unitValueRepository) {
        this.unitValueRepository = unitValueRepository;
    }

    public UnitValue save(UnitValueRequest unitValueRequest){
//        if(unitValueRequest.getValue()){
//
//        }
        //Cria um objeto unitValue com a data atual e valor recebido no request
        UnitValue unitValue = new UnitValue();
        unitValue.setValue(unitValueRequest.getValue());
        unitValue.setStartDate(LocalDate.now());

        return unitValueRepository.save(unitValue);
    }
    public Optional<UnitValue> getCurrentUnit(){

        return unitValueRepository.findTopByStartDateLessThanEqualOrderByStartDateDesc(LocalDate.now());
    }


}
