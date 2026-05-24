package com.fixnow.ms_equipos.DTO;

import lombok.Data;
import java.util.Date;

@Data
public class PersonaDTO {
    private Long idPersona;
    private String rut;
    private String nombres;
    private String apellidos;
    private String correo;
    private Date fechaNacimiento;
}
