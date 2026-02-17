package com.example.TipsManagement.controller;


import com.example.TipsManagement.controller.dto.TipsterRequest;
import com.example.TipsManagement.service.TipsterService;
import org.hibernate.mapping.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tipster")
public class TipsterController {
    private final TipsterService tipsterService;

    public TipsterController(TipsterService tipsterService) {
        this.tipsterService = tipsterService;
    }


    @PostMapping
    public ResponseEntity<Object> saveTipster(@RequestBody TipsterRequest tipster){
        try{
            //Chama o service para salvar e após isso retorna os valores salvos
            tipsterService.save(tipster);
            return new ResponseEntity<>(tipster, HttpStatus.CREATED);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<Object> listAllTipster(){
        try{
            return new ResponseEntity<>(tipsterService.listAll(),HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
