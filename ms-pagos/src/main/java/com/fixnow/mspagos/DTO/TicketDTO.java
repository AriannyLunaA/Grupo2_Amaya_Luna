package com.fixnow.mspagos.DTO;

import lombok.Data;
import java.util.Date;

/**
 * Patrón DTO utilizado para recepcionar estrictamente los datos necesarios
 * desde el microservicio de tickets, evitando exponer la entidad original.
 */
@Data
public class TicketDTO {
    private Long idTicket;
    private Long idCliente;
    private Long idPersona;
    private Long idEquipo;
    private Long idDiagnostico;
    private Date fechaIngreso;
    private String estado;
    private Double costoTotal;
}
