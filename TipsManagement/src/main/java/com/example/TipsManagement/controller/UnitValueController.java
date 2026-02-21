package com.example.TipsManagement.controller;

import com.example.TipsManagement.controller.dto.UnitValueRequest;
import com.example.TipsManagement.service.UnitValueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/UnitValue")
public class UnitValueController {

    private final UnitValueService unitValueService;

    public UnitValueController(UnitValueService unitValueService) {
        this.unitValueService = unitValueService;
    }

    @PostMapping
    public ResponseEntity<Object> saveUnitValue(@RequestBody UnitValueRequest unitValueRequest){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(unitValueService.save(unitValueRequest));
    }

    @
}
