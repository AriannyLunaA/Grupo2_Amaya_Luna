package com.fixnow.mspago;

import com.fixnow.mspago.Model.Pago;
import com.fixnow.mspago.Service.PagoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class MsPagoApplicationTests {

    @Autowired
    private PagoService pagoService;

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("Regla de Negocio: El monto a pagar debe ser estrictamente mayor a cero")
    void checkMontoMinimo() {
        Pago pago = pagoService.buscarPagoPorId(1L);
        log.info("Revisando regla de monto mínimo para el pago ID 1, Monto actual: {}", pago.getMontoTotal());

        assertTrue(pago.getMontoTotal() > 0, "Error: El monto del pago no puede ser 0 o negativo");
    }

    @Test
    @DisplayName("Regla de Negocio: El método de pago es obligatorio y no puede estar vacío")
    void checkMetodoPagoNoVacio() {
        Pago pago = pagoService.buscarPagoPorId(1L);
        log.info("Revisando regla de obligatoriedad en método de pago ID 1: {}", pago.getMetodoPago());

        assertNotNull(pago.getMetodoPago(), "Error: El método de pago es nulo");
        assertFalse(pago.getMetodoPago().trim().isEmpty(), "Error: El método de pago está en blanco");
    }

    @Test
    @DisplayName("Regla de Negocio: El estado de la transacción debe ser consistente (PAGADO o PENDIENTE)")
    void checkEstadoValido() {
        Pago pago = pagoService.buscarPagoPorId(2L);
        log.info("Revisando regla de estados transaccionales para el pago ID 2: {}", pago.getEstado());

        boolean estadoValido = pago.getEstado().equals("PAGADO") || pago.getEstado().equals("PENDIENTE");
        assertTrue(estadoValido, "Error: Estado de pago no reconocido o inválido");
    }

    @Test
    @DisplayName("Regla de Negocio: Asociación obligatoria de un Ticket válido a la transacción")
    void checkAsociacionTicket() {
        Pago pago = pagoService.buscarPagoPorId(1L);
        log.info("Revisando regla de asociación de ticket para el pago ID 1");

        assertNotNull(pago.getIdTicket(), "Error: El pago no tiene un ticket asociado");
        assertTrue(pago.getIdTicket() > 0, "Error: El ID del ticket asociado no es válido (debe ser mayor a 0)");
    }
}
