package com.fixnow.msticket.DTO;

import lombok.Data;

@Data
public class EquipoDTO {
    private Long idEquipo;
    private Long idPersona;
    private String tipo;
    private String marca;
    private String modelo;
}
