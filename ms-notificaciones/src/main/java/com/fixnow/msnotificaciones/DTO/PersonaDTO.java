package com.fixnow.msnotificaciones.DTO;

import lombok.Data;

/**
 * DTO para recepcionar la información del dueño del equipo.
 * Nos interesa principalmente el correo electrónico para despachar la notificación.
 */
@Data
public class PersonaDTO {
    private Long idPersona;
    private String nombre;
    private String correo;
}
