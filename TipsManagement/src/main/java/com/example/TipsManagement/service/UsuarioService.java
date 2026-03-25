package com.example.TipsManagement.service;

import com.example.TipsManagement.Exception.BadRequestException;
import com.example.TipsManagement.Exception.BusinessException;
import com.example.TipsManagement.model.dto.Request.UsuarioRequest;
import com.example.TipsManagement.model.dto.Response.UsuarioResponse;
import com.example.TipsManagement.model.Usuario;
import com.example.TipsManagement.repository.IUsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class UsuarioService {

    private final IUsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper mapper;

    public UsuarioService(IUsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, ObjectMapper mapper) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    public UsuarioResponse save(UsuarioRequest usuarioRequest){
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

        return mapper.convertValue(usuarioRepository.save(usuario), UsuarioResponse.class);
    }

    public List<UsuarioResponse> listAll(){
        return usuarioRepository.findAll()
                .stream()
                .map(usuario -> mapper.convertValue(usuario, UsuarioResponse.class))
                .toList();
    }

    public UsuarioResponse edit(Long id, UsuarioRequest usuarioRequest){
        if(usuarioRequest.getName().length()<3){
            throw new BadRequestException("Número de caracteres insuficiente (min. 3)");
        }
        Usuario usuario  = new Usuario();
        usuario.setId(id);
        usuario.setName(usuarioRequest.getName());
        usuario.setEmail(usuarioRequest.getEmail());
        usuario.setPassword(passwordEncoder.encode(usuarioRequest.getPassword()));
        return mapper.convertValue(usuarioRepository.save(usuario), UsuarioResponse.class);
    }
}
