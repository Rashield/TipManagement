package com.example.TipsManagement.controller.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UsuarioRequest {
    private String name;

    @Email
    private String email;

    private String password;
}
