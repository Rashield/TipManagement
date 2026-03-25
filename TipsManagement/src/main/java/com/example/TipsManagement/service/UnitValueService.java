package com.example.TipsManagement.service;

import com.example.TipsManagement.Exception.NotFoundException;
import com.example.TipsManagement.model.dto.Request.UnitValueRequest;
import com.example.TipsManagement.model.UnitValue;
import com.example.TipsManagement.model.Usuario;
import com.example.TipsManagement.model.dto.Response.UnitValueResponse;
import com.example.TipsManagement.repository.IUnitValueRepository;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.List;


@Service
public class UnitValueService {

    private final IUnitValueRepository unitValueRepository;
    private final ObjectMapper mapper;


    public UnitValueService(IUnitValueRepository unitValueRepository, ObjectMapper mapper) {
        this.unitValueRepository = unitValueRepository;
        this.mapper = mapper;
    }

    public UnitValueResponse save(Long userId, UnitValueRequest unitValueRequest){
        //Cria um objeto unitValue com a data atual, valor recebido no request e usuario logado
        UnitValue unitValue = new UnitValue();
        unitValue.setUnitValue(unitValueRequest.getUnitValue());
        unitValue.setStartDate(LocalDate.now());
        Usuario usuario = new Usuario();
        usuario.setId(userId);
        unitValue.setUsuario(usuario);
        return mapper.convertValue(unitValueRepository.save(unitValue), UnitValueResponse.class);
    }
    public UnitValueResponse getCurrentUnit(Long userId){
        UnitValue currentUnit = unitValueRepository.findTopByUsuarioIdAndStartDateLessThanEqualOrderByStartDateDescIdDesc(userId, LocalDate.now())
                .orElseThrow(() ->
                new NotFoundException("Não existe unidade cadastrada."));
        return mapper.convertValue(currentUnit, UnitValueResponse.class);
    }


    public List<UnitValueResponse> listAll(Long userId){
        List<UnitValue> list = unitValueRepository.findAllByUsuarioId(userId);
        if(list.isEmpty()){
            throw new NotFoundException("Não existe unidade cadastrada.");
        }
        return list.stream()
                .map(unit -> mapper.convertValue(unit, UnitValueResponse.class))
                .toList();
    }

}
