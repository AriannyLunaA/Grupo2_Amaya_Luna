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


@Service
@Slf4j
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private TicketClient ticketClient;

    public List<Pago> listarPagos() {
        log.info("solicitando listado de pagos registrados.");
        return pagoRepository.findAll();
    }

    public Pago buscarPagoPorId(Integer idPago) {
        log.info("buscando pago con id: {}", idPago);
        return pagoRepository.findById(idPago).orElse(null);
    }

    public List<Pago> buscarPagosPorTicket(Long idTicket) {
        log.info("consultando historial de pagos para el Ticket id: {}", idTicket);
        return pagoRepository.findByIdTicket(idTicket);
    }

    public boolean registrarPago(Pago nuevoPago) {
        try {
            log.info("iniciando validación remota para el Ticket id: {}", nuevoPago.getIdTicket());
            TicketDTO ticketRemoto = ticketClient.getTicketById(nuevoPago.getIdTicket());

            if (ticketRemoto != null) {
                nuevoPago.setFechaPago(LocalDateTime.now());
                pagoRepository.save(nuevoPago);
                log.info("pago registrado exitosamente en base de datos local");
                return true;
            } else {
                log.warn("validación fallida, el ticket id {} no fue localizado en ms-ticket.", nuevoPago.getIdTicket());
                return false;
            }
        } catch (Exception e) {
            log.error("fallo crítico de comunicación con ms-tickets: {}", e.getMessage());
            return false;
        }
    }
}
