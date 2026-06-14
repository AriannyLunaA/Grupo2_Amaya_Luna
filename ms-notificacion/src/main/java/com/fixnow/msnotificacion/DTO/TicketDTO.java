package com.fixnow.msnotificacion.DTO;

import lombok.Data;


@Data
public class TicketDTO {
    private Long idTicket;
    private Long idPersona;
    private String estado;
}
