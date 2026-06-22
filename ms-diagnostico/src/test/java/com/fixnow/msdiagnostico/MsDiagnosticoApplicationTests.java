package com.fixnow.msdiagnostico;

import com.fixnow.msdiagnostico.DTO.DiagnosticoDTO;
import com.fixnow.msdiagnostico.Service.DiagnosticoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class MsDiagnosticoApplicationTests {

    @Autowired
    private DiagnosticoService diagnosticoService;

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Regla de Negocio: Consistencia lógica en la asignación de repuestos")
    void CheckUsoCoherenteRepuestos() {
        DiagnosticoDTO diagnostico = diagnosticoService.buscarPorId(1L);
        log.info("Revisando necesidad de repuestos cruzada con los datos para el diagnostico ID 1");

        // Si el diagnóstico indica que NO necesita repuesto, no debería existir un ID o cantidad en el sistema
        if (!diagnostico.getNecesitaRepuesto()) {
            assertNull(diagnostico.getIdRepuesto(), "Error: Indica que no necesita repuesto, pero trae un ID asignado");
            assertNull(diagnostico.getCantidadRepuesto(), "Error: Indica que no necesita repuesto, pero trae una cantidad ingresada");
        }
    }

    @Test
    @DisplayName("Regla de Negocio: La estimación de costo y tiempo debe ser viable")
    void checkCostoTiempoViable() {
        DiagnosticoDTO diagnostico = diagnosticoService.buscarPorId(1L);
        log.info("Revisando viabilidad de la estimacion de diagnostico ID 1: Costo ${}", diagnostico.getCostoEstimado());

        // El área técnica no trabaja gratis ni el tiempo puede ser 0 o negativo
        assertTrue(diagnostico.getCostoEstimado() > 0, "Error: El costo estimado no puede ser gratuito o negativo");
        assertTrue(diagnostico.getTiempoEstimadoDias() >= 1, "Error: El diagnostico debe tomar al menos 1 dia habil");
    }

    @Test
    @DisplayName("Regla de Negocio: Un diagnóstico técnico debe pertenecer estrictamente a un Ticket activo")
    void checkAsociacionTicket() {
        DiagnosticoDTO diagnostico = diagnosticoService.buscarPorId(1L);
        log.info("Revisando origen del diagnostico ID 1");

        // Todo informe técnico debe estar vinculado obligatoriamente a una orden de soporte (Ticket) real y registrada
        assertNotNull(diagnostico.getIdTicket(), "Error: El diagnostico no posee un ticket asociado");
        assertTrue(diagnostico.getIdTicket() > 0, "Error: El ID del ticket asociado es invalido");
    }
}
