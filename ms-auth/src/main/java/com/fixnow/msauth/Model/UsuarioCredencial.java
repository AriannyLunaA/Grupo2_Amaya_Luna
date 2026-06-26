package com.fixnow.msauth.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="usuarios_credenciales")
public class UsuarioCredencial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Lunes 25-05: Defensa Técnica Evaluación 2 - Requerimiento del docente (AriannyLunaA): Agregar un mínimo de 5 caracteres permitidos al nombre de usuario.
    @NotNull(message = "el nombre de usuario es obligatorio")
    @Size(max = 255, min = 5)
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @NotNull(message = "la contraseña es obligatoria")
    @Size(max = 50, min = 25)
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull(message = "el rol es obligatorio. Ejemplo: Tecnico, Admin, Persona")
    @Size(max = 50)
    @Column(name = "rol", nullable = false, length = 50)
    private String rol;

    @NotNull(message = "el id del perfil es obligatorio")
    @Column(name = "perfil_id", nullable = false)
    private Long perfilId;
}
