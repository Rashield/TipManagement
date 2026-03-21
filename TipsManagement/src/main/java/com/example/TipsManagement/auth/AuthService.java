package com.example.TipsManagement.auth;

import com.example.TipsManagement.Exception.NotFoundException;
import com.example.TipsManagement.model.Usuario;
import com.example.TipsManagement.repository.IUsuarioRepository;
import com.example.TipsManagement.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final IUsuarioRepository userRepository;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService, IUsuarioRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }



    public AuthResponse login(LoginRequest request){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Usuario user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        String token = jwtService.generateToken(user);

        return new AuthResponse(token);
    }
}

