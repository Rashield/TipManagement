package com.example.TipsManagement.controller;

import com.example.TipsManagement.controller.dto.UnitValueRequest;
import com.example.TipsManagement.service.UnitValueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/unitValue")
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

    @GetMapping("/current")
    public ResponseEntity<Object> getUnit(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(unitValueService.getCurrentUnit());
    }

    @GetMapping
    public ResponseEntity<Object> listALLUnits(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(unitValueService.listAll());
    }
}
