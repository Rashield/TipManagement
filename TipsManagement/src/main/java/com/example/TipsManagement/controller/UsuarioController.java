package com.example.TipsManagement.controller;

import com.example.TipsManagement.controller.dto.UsuarioRequest;
import com.example.TipsManagement.model.Usuario;
import com.example.TipsManagement.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Usuario> saveUsuario(@RequestBody UsuarioRequest usuarioRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.save(usuarioRequest));
    }

    @GetMapping
    public ResponseEntity<Object> listUsuario(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(usuarioService.listAll());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> editUsuario(@PathVariable Long id,@RequestBody UsuarioRequest usuarioRequest){
        return ResponseEntity.status(HttpStatus.OK)
                .body(usuarioService.edit(id, usuarioRequest));
    }
}
