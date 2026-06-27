package com.fixnow.msticket;

import com.fixnow.msticket.DTO.TicketDTO;
import com.fixnow.msticket.Service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class MsTicketApplicationTests {

    @Autowired
    private TicketService ticketService;

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Regla de Negocio: La descripción de la falla es obligatoria y debe contener detalles")
    void checkDescripcionFallaNoVacia() {
        TicketDTO ticket = ticketService.buscarPorId(1L);
        log.info("Revisando regla de descripcion de falla para el ticket ID 1: {}", ticket.getDescripcionFalla());

        // El técnico necesita saber qué le pasa al equipo, no podemos permitir descripciones vacías o nulas
        assertNotNull(ticket.getDescripcionFalla(), "Error: La descripcion de la falla es nula");
        assertFalse(ticket.getDescripcionFalla().trim().isEmpty(), "Error: La descripcion de la falla esta en blanco");
    }

    @Test
    @DisplayName("Regla de Negocio: Todo ticket debe estar asociado a un cliente y a un equipo válidos")
    void checkAsociacionClienteEquipo() {
        TicketDTO ticket = ticketService.buscarPorId(1L);
        log.info("Revisando asociacion de cliente y equipo para el ticket ID 1");

        // Un ticket fantasma no sirve. Debe tener IDs positivos apuntando a ms-persona y ms-equipo
        assertNotNull(ticket.getIdPersona(), "Error: El ticket no tiene un cliente asociado");
        assertTrue(ticket.getIdPersona() > 0, "Error: El ID del cliente debe ser un numero positivo");

        assertNotNull(ticket.getIdEquipo(), "Error: El ticket no tiene un equipo asociado");
        assertTrue(ticket.getIdEquipo() > 0, "Error: El ID del equipo debe ser un numero positivo");
    }

    @Test
    @DisplayName("Regla de Negocio: El estado del ticket debe ser coherente con el ciclo de soporte")
    void checkEstadoTicketValido() {
        TicketDTO ticket = ticketService.buscarPorId(2L);
        log.info("Revisando regla de estado para el ticket ID 2: {}", ticket.getEstado());

        // El estado debe ser uno de los permitidos por el modelo de negocio
        String estado = ticket.getEstado();
        boolean estadoValido = estado.equals("INGRESADO") || estado.equals("EN_REVISION") ||
                estado.equals("EN_REPARACION") || estado.equals("FINALIZADO");

        assertTrue(estadoValido, "Error: El estado del ticket no es reconocido en el flujo del sistema");
    }
}
