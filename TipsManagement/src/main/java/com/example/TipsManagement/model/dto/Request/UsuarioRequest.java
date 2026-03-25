package com.example.TipsManagement.model.dto.Request;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UsuarioRequest {
    private String name;

    @Email
    private String email;

    private String password;
}
