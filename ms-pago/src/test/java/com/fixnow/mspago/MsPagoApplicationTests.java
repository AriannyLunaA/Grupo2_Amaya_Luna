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
    void verificarMontoMinimo() {
        Pago pago = pagoService.buscarPagoPorId(1);
        log.info("Revisando regla de monto mínimo para el pago ID 1, Monto actual: {}", pago.getMontoTotal());

        // Verifica que el valor exista y sea positivo (regla de oro en pagos)
        assertTrue(pago.getMontoTotal() > 0, "Error: El monto del pago no puede ser 0 o negativo");
    }

    @Test
    @DisplayName("Regla de Negocio: El método de pago es obligatorio y no puede estar vacío")
    void verificarMetodoPagoNoVacio() {
        Pago pago = pagoService.buscarPagoPorId(1);
        log.info("Revisando regla de obligatoriedad en método de pago ID 1: {}", pago.getMetodoPago());

        // Verifica que no sea nulo y que al quitar los espacios no quede vacío
        assertNotNull(pago.getMetodoPago(), "Error: El método de pago es nulo");
        assertFalse(pago.getMetodoPago().trim().isEmpty(), "Error: El método de pago está en blanco");
    }

    @Test
    @DisplayName("Regla de Negocio: El estado de la transacción debe ser consistente (PAGADO o PENDIENTE)")
    void verificarEstadoValido() {
        Pago pago = pagoService.buscarPagoPorId(2);
        log.info("Revisando regla de estados transaccionales para el pago ID 2: {}", pago.getEstado());

        // Regla lógica: Un pago de nuestra BD solo puede tener estos dos estados iniciales
        boolean estadoValido = pago.getEstado().equals("PAGADO") || pago.getEstado().equals("PENDIENTE");
        assertTrue(estadoValido, "Error: Estado de pago no reconocido o inválido");
    }

    @Test
    @DisplayName("Regla de Negocio: Asociación obligatoria de un Ticket válido a la transacción")
    void verificarAsociacionTicket() {
        Pago pago = pagoService.buscarPagoPorId(1);
        log.info("Revisando regla de asociación de ticket para el pago ID 1");

        // Verifica que el pago tenga una referencia válida hacia un Ticket (ID mayor a 0)
        assertNotNull(pago.getIdTicket(), "Error: El pago no tiene un ticket asociado");
        assertTrue(pago.getIdTicket() > 0, "Error: El ID del ticket asociado no es válido (debe ser mayor a 0)");
    }
}
