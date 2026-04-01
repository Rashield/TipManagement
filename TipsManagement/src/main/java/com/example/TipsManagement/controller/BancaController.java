package com.example.TipsManagement.controller;

import com.example.TipsManagement.model.LoggedUser;
import com.example.TipsManagement.model.dto.Request.BancaRequest;
import com.example.TipsManagement.model.dto.Response.BancaResponse;
import com.example.TipsManagement.service.BancaService;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bethouse")
public class BancaController {
    private final BancaService bancaService;

    public BancaController(BancaService bancaService) {
        this.bancaService = bancaService;
    }

    @PostMapping("/{betHouseId}/banca")
    public ResponseEntity<BancaResponse> saveBanca(@AuthenticationPrincipal LoggedUser usuario, @PathVariable Long betHouseId, @Valid @RequestBody BancaRequest bancaRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bancaService.save(usuario.getId(), betHouseId, bancaRequest));
    }

    @GetMapping("/banca")
    public ResponseEntity<Object> listAllBanca(@AuthenticationPrincipal LoggedUser usuario){
        return ResponseEntity.status(HttpStatus.OK)
                .body(bancaService.listAll(usuario.getId()));
    }

    @GetMapping("/{betHouseId}/banca")
    public ResponseEntity<BancaResponse> listBanca(@AuthenticationPrincipal LoggedUser usuario, @PathVariable Long betHouseId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(bancaService.list(usuario.getId(),betHouseId));
    }
}

