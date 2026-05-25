package com.fixnow.msticket.DTO;

import lombok.Data;
import java.util.Date;

@Data
public class TicketDTO {
    private Long idTicket;
    private Long idPersona;
    private Long idEquipo;
    private Date fechaIngreso;
    private String estado;
    private String descripcionFalla;
}
