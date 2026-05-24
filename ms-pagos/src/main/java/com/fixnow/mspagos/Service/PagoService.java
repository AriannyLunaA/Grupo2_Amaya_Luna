package com.fixnow.mspagos.Service;

import com.fixnow.mspagos.Client.TicketClient;
import com.fixnow.mspagos.DTO.TicketDTO;
import com.fixnow.mspagos.Model.Pago;
import com.fixnow.mspagos.Repository.PagoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio encargado de la lógica de negocio de los pagos.
 * Centraliza la comunicación con ms-tickets para asegurar la consistencia eventual
 * del sistema antes de persistir datos financieros.
 */
@Service
@Slf4j
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private TicketClient ticketClient;

    public List<Pago> listarPagos() {
        log.info("Solicitando listado global de pagos registrados.");
        return pagoRepository.findAll();
    }

    public Pago buscarPagoPorId(Integer idPago) {
        log.info("Buscando pago con ID: {}", idPago);
        return pagoRepository.findById(idPago).orElse(null);
    }

    public List<Pago> buscarPagosPorTicket(Integer idTicket) {
        log.info("Consultando historial de pagos para el Ticket ID: {}", idTicket);
        return pagoRepository.findByIdTicket(idTicket);
    }

    /**
     * Registra un pago nuevo.
     * Regla de negocio: No se puede procesar un pago sin verificar sincrónicamente (vía Feign)
     * que el ticket asociado existe en el microservicio de origen.
     */
    public boolean registrarPago(Pago nuevoPago) {
        try {
            log.info("Iniciando validación remota para el Ticket ID: {}", nuevoPago.getIdTicket());
            TicketDTO ticketRemoto = ticketClient.getTicketById(Long.valueOf(nuevoPago.getIdTicket()));

            if (ticketRemoto != null) {
                nuevoPago.setFechaPago(LocalDateTime.now());
                pagoRepository.save(nuevoPago);
                log.info("Pago registrado exitosamente en base de datos local.");
                return true;
            } else {
                log.warn("Validación fallida: El ticket ID {} no fue localizado en ms-tickets.", nuevoPago.getIdTicket());
                return false;
            }
        } catch (Exception e) {
            // Manejo de fallos en la red o si ms-tickets está caído
            log.error("Fallo crítico de comunicación con ms-tickets: {}", e.getMessage());
            return false;
        }
    }
}
