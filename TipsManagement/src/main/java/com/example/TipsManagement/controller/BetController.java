package com.example.TipsManagement.controller;

import com.example.TipsManagement.model.LoggedUser;
import com.example.TipsManagement.model.dto.Request.BetRequest;
import com.example.TipsManagement.model.dto.Response.BetResponse;
import com.example.TipsManagement.service.BetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bet")
public class BetController {
    private final BetService betService;

    public BetController(BetService betService) {
        this.betService = betService;
    }
    @PostMapping
    public ResponseEntity<BetResponse> saveBet(@AuthenticationPrincipal LoggedUser usuario, @RequestBody @Valid BetRequest betRequest){
        return ResponseEntity.status(HttpStatus.OK)
                .body(betService.save(usuario.getId(), betRequest));
    }
}
