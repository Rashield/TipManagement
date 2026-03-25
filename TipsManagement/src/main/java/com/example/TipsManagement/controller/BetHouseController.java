package com.example.TipsManagement.controller;

import com.example.TipsManagement.model.dto.Request.BetHouseRequest;
import com.example.TipsManagement.model.BetHouse;
import com.example.TipsManagement.service.BetHouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bethouse")
public class BetHouseController {
    private final BetHouseService betHouseService;

    public BetHouseController(BetHouseService betHouseService) {
        this.betHouseService = betHouseService;
    }

    @PostMapping
    public ResponseEntity<BetHouse> saveBetHouse(@RequestBody BetHouseRequest betHouseRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(betHouseService.save(betHouseRequest));
    }

    @GetMapping
    public ResponseEntity<Object> listAllBetHouse(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(betHouseService.listAll());
    }
}
