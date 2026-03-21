package com.example.TipsManagement.service;

import com.example.TipsManagement.Exception.BadRequestException;
import com.example.TipsManagement.Exception.BusinessException;
import com.example.TipsManagement.controller.dto.UsuarioRequest;
import com.example.TipsManagement.model.Usuario;
import com.example.TipsManagement.repository.IUsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(IUsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario save(UsuarioRequest usuarioRequest){
        if(usuarioRequest.getName().length()<3){
            throw new BadRequestException("Número de caracteres insuficiente (min. 3)");
        }
        if(usuarioRepository.existsByEmail(usuarioRequest.getEmail())) {
            throw new BusinessException("Email já cadastrado.");
        }
        Usuario usuario = new Usuario();
        usuario.setName(usuarioRequest.getName());
        usuario.setEmail(usuarioRequest.getEmail());
        usuario.setPassword(passwordEncoder.encode(usuarioRequest.getPassword()));

        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listAll(){
        return usuarioRepository.findAll();
    }

}
