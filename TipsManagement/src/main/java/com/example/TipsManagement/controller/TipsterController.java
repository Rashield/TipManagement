package com.example.TipsManagement.controller;


import com.example.TipsManagement.model.dto.Request.TipsterRequest;
import com.example.TipsManagement.model.LoggedUser;
import com.example.TipsManagement.model.dto.Response.TipsterResponse;
import com.example.TipsManagement.service.TipsterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tipster")
public class TipsterController {
    private final TipsterService tipsterService;

    public TipsterController(TipsterService tipsterService) {
        this.tipsterService = tipsterService;
    }


    @PostMapping
    public ResponseEntity<TipsterResponse> saveTipster(@AuthenticationPrincipal LoggedUser usuario, @RequestBody TipsterRequest tipster){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tipsterService.save(usuario.getId(), tipster));

    }

    @GetMapping
    public ResponseEntity<Object> listAllTipster(@AuthenticationPrincipal LoggedUser usuario){
        return ResponseEntity.status(HttpStatus.OK)
                .body(tipsterService.listAll(usuario.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> editTipster(@AuthenticationPrincipal LoggedUser usuario, @PathVariable Long id, @RequestBody TipsterRequest tipsterRequest){
        return ResponseEntity.status(HttpStatus.OK)
                .body(tipsterService.edit(usuario.getId(),id, tipsterRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipster(@AuthenticationPrincipal LoggedUser usuario, @PathVariable Long id){
        tipsterService.delete(usuario.getId(), id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
