package com.example.TipsManagement.controller;

import com.example.TipsManagement.model.LoggedUser;
import com.example.TipsManagement.model.dto.Request.BetHouseRequest;
import com.example.TipsManagement.model.dto.Response.BetHouseResponse;
import com.example.TipsManagement.service.BetHouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bethouse")
public class BetHouseController {
    private final BetHouseService betHouseService;

    public BetHouseController(BetHouseService betHouseService) {
        this.betHouseService = betHouseService;
    }

    @PostMapping
    public ResponseEntity<BetHouseResponse> saveBetHouse(@AuthenticationPrincipal LoggedUser usuario, @RequestBody BetHouseRequest betHouseRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(betHouseService.save(usuario.getId(), betHouseRequest));
    }

    @GetMapping
    public ResponseEntity<Object> listAllBetHouse(@AuthenticationPrincipal LoggedUser usuario){
        return ResponseEntity.status(HttpStatus.OK)
                .body(betHouseService.listAll(usuario.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BetHouseResponse> editBetHouse(@AuthenticationPrincipal LoggedUser usuario, @PathVariable Long id, @RequestBody BetHouseRequest betHouseRequest){
        return ResponseEntity. status(HttpStatus.OK)
                .body(betHouseService.edit(usuario.getId(),id, betHouseRequest));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBetHouse(@AuthenticationPrincipal LoggedUser usuario, @PathVariable Long id){
        betHouseService.delete(usuario.getId(), id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
