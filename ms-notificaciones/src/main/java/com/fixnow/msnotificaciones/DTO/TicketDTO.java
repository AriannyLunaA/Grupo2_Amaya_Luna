package com.fixnow.msnotificaciones.DTO;

import lombok.Data;

/**
 * DTO para recepcionar la información del ticket.
 * Solo extraemos el idPersona para saber a quién pertenece el equipo y poder notificarle.
 */
@Data
public class TicketDTO {
    private Long idTicket;
    private Long idPersona;
    private String estado;
}
