package com.fixnow.msequipo.DTO;

import lombok.Data;

@Data
public class EquipoDTO {
    private Long idEquipo;
    private Long idPersona;
    private String tipo;
    private String marca;
    private String modelo;
    private String procesador;
    private String memoriaRam;
    private String almacenamiento;
    private String tarjetaGrafica;
    private String numeroSerie;
    private String observacionesFisicas;
}
