package com.fixnow.msticket;

import com.fixnow.msticket.Model.Ticket;
import com.fixnow.msticket.Repository.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class TicketRepositoryTest {

    @Autowired
    TicketRepository ticketRepository;

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("test 1: persistencia exitosa de un ticket con todos los campos obligatorios")
    void checkGuardarTicket() {
        log.info("Validando inserción de ticket en esquema de pruebas");

        Ticket ticket = new Ticket();
        ticket.setIdPersona(100L);
        ticket.setIdEquipo(50L);
        ticket.setFechaIngreso(new Date());
        ticket.setEstado("INGRESADO");
        ticket.setDescripcionFalla("Fallo en la placa base, no enciende.");

        Ticket ticketGuardado = ticketRepository.save(ticket);

        assertNotNull(ticketGuardado.getIdTicket(), "El ID debe ser autogenerado por la base de datos");
        assertEquals("INGRESADO", ticketGuardado.getEstado());

        Optional<Ticket> recuperado = ticketRepository.findById(ticketGuardado.getIdTicket());
        assertTrue(recuperado.isPresent(), "El ticket debe existir tras la inserción");
    }

    @Test
    @DisplayName("test 2: la descripción de la falla es obligatoria (no nula)")
    void checkValidacionDescripcionNula() {
        log.info("Verificando rechazo de persistencia por violación de @NotNull en descripcionFalla");

        Ticket ticketInvalido = new Ticket();
        ticketInvalido.setIdPersona(101L);
        ticketInvalido.setIdEquipo(51L);
        ticketInvalido.setFechaIngreso(new Date());
        ticketInvalido.setEstado("EN_REVISION");
        ticketInvalido.setDescripcionFalla(null); // Violación de regla

        assertThrows(Exception.class, () -> ticketRepository.save(ticketInvalido),
                "La transacción debe fallar al intentar insertar una descripción nula");
    }

    @Test
    @DisplayName("test 3: el estado no debe superar los 50 caracteres")
    void checkValidacionLongitudEstado() {
        log.info("Verificando rechazo de persistencia por violación de @Size en estado");

        Ticket ticketInvalido = new Ticket();
        ticketInvalido.setIdPersona(102L);
        ticketInvalido.setIdEquipo(52L);
        ticketInvalido.setFechaIngreso(new Date());
        ticketInvalido.setDescripcionFalla("Pantalla rota");
        // String de 51 caracteres para forzar el fallo de @Size(max = 50)
        ticketInvalido.setEstado("ESTE_ESTADO_ES_EXTREMADAMENTE_LARGO_Y_DEBERIA_FALLAR");

        assertThrows(Exception.class, () -> ticketRepository.save(ticketInvalido),
                "La transacción debe fallar por exceder el VARCHAR(50) del estado");
    }
}