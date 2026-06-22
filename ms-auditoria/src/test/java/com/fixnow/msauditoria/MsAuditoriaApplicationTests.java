package com.fixnow.msauditoria;

import com.fixnow.msauditoria.Model.Auditoria;
import com.fixnow.msauditoria.Service.AuditoriaService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class MsAuditoriaApplicationTests {

    @Autowired
    private AuditoriaService auditoriaService;

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Regla de Negocio: Todo registro debe referenciar al menos a una entidad (Ticket, Pago o Persona)")
    void checkAsociacionEntidad() {
        Auditoria auditoria = auditoriaService.buscarPorId(1L);
        log.info("Revisando referencias de entidades para la auditoria ID 1");

        // Un log sin IDs no sirve para trazabilidad. Exigimos que al menos uno venga con datos.
        boolean tieneReferencia = (auditoria.getIdTicket() != null) ||
                (auditoria.getIdPago() != null) ||
                (auditoria.getIdPersona() != null);

        assertTrue(tieneReferencia, "Error: El registro de auditoria no esta asociado a ninguna entidad del sistema");
    }

    @Test
    @DisplayName("Regla de Negocio: Los detalles del evento deben tener una longitud mínima descriptiva")
    void checkDetallesDescriptivosMinimos() {
        Auditoria auditoria = auditoriaService.buscarPorId(1L);
        log.info("Revisando longitud y calidad de los detalles para auditoria ID 1");

        // Evitamos registros basura como "ok" o ".". Exigimos un mínimo de 15 caracteres de contexto.
        assertTrue(auditoria.getDetalles().length() >= 15, "Error: Los detalles son muy cortos y carecen de contexto explicativo");
    }

    @Test
    @DisplayName("Regla de Negocio: Consistencia entre la acción PAGO_PROCESADO y su ID asociado")
    void checkContextoPago() {
        Auditoria auditoria = auditoriaService.buscarPorId(2L);
        log.info("Revisando consistencia contextual para la auditoria ID 2 con accion: {}", auditoria.getAccion());

        // Si la acción registrada indica que se procesó un pago, el idPago no puede ser nulo bajo ninguna circunstancia
        if (auditoria.getAccion().equals("PAGO_PROCESADO")) {
            assertNotNull(auditoria.getIdPago(), "Error: La accion es PAGO_PROCESADO pero no hay un ID de pago asociado");
        }
    }
}
