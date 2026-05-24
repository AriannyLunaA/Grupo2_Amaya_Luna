package com.fixnow.ms_auth.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthRequestDTO {
    @NotNull(message = "el username no puede ser nulo")
    private String username;

    @NotNull(message = "la contraseña no puede ser nula")
    private String password;
}
