package com.servicio.tecnico.mstickets.dto;

import lombok.Data;

@Data
public class ClienteDTO {
    //lo que usaremos del cliente
    private Long id;
    private String rut;
    private String nombre;
    private String apellidoPaterno;
    private String email;

}//Fin
