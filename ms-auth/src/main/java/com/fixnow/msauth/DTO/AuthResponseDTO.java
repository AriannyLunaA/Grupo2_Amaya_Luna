package com.fixnow.msauth.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDTO {
    private Long id;
    private String username;
    private String rol;
    private Long perfilId;
    private String mensaje;

    private String rut;
    private String nombres;
    private String apellidos;
    private String correo;
}