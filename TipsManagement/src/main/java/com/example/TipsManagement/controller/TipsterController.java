package com.example.TipsManagement.controller;


import com.example.TipsManagement.controller.dto.TipsterRequest;
import com.example.TipsManagement.service.TipsterService;
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
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tipsterService.save(tipster));

    }

    @GetMapping
    public ResponseEntity<Object> listAllTipster(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(tipsterService.listAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> editTipster(@PathVariable Long id, @RequestBody TipsterRequest tipsterRequest){
        return ResponseEntity.status(HttpStatus.OK)
                .body(tipsterService.edit(id, tipsterRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipster(@PathVariable Long id){
        tipsterService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
