package com.example.TipsManagement.controller;

import com.example.TipsManagement.model.dto.Request.UnitValueRequest;
import com.example.TipsManagement.model.LoggedUser;
import com.example.TipsManagement.model.dto.Response.UnitValueResponse;
import com.example.TipsManagement.service.UnitValueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/unitValue")
public class UnitValueController {

    private final UnitValueService unitValueService;

    public UnitValueController(UnitValueService unitValueService) {
        this.unitValueService = unitValueService;
    }

    @PostMapping
    public ResponseEntity<UnitValueResponse> saveUnitValue(@AuthenticationPrincipal LoggedUser usuario, @RequestBody UnitValueRequest unitValueRequest){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(unitValueService.save(usuario.getId(), unitValueRequest));
    }

    @GetMapping("/current")
    public ResponseEntity<Object> getUnit(@AuthenticationPrincipal LoggedUser usuario){
        return ResponseEntity.status(HttpStatus.OK)
                .body(unitValueService.getCurrentUnit(usuario.getId()));
    }

    @GetMapping
    public ResponseEntity<Object> listAllUnits(@AuthenticationPrincipal LoggedUser usuario){
        return ResponseEntity.status(HttpStatus.OK)
                .body(unitValueService.listAll(usuario.getId()));
    }
}
