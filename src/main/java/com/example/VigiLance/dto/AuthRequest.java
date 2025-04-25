package com.example.VigiLance.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(
    @NotBlank(message = "Le nom d'utilisateur ne peut pas être vide")
    String username,

    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    String password
) {}