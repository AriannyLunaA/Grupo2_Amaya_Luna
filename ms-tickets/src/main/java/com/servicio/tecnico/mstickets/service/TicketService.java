package com.servicio.tecnico.mstickets.service;


import com.servicio.tecnico.mstickets.client.ClienteClient;
import com.servicio.tecnico.mstickets.model.Ticket;
import com.servicio.tecnico.mstickets.repository.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.servicio.tecnico.mstickets.dto.ClienteDTO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;



@Service
@Slf4j // Trazabilidad de operaciones
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository; // Persistencia real

    @Autowired
    private ClienteClient clienteClient; // Comunicación remota Feign

    // 1. OBTENER TODOS (READ)
    public List<Ticket> listarTodos() {
        log.info("Consultando lista completa de tickets registrados.");
        return ticketRepository.findAll();
    }

    // 2. BUSCAR POR ID (READ)
    public Optional<Ticket> obtenerPorId(Long id) {
        log.info("Buscando ticket con ID: {}", id);
        return ticketRepository.findById(id);
    }

    // 3. BUSCAR POR ESTADO (READ)
    public List<Ticket> buscarPorEstado(String estado) {
        log.info("Filtrando tickets por estado: {}", estado);
        return ticketRepository.findByEstado(estado);
    }

    // 4. CREAR TICKET (CREATE) con Validación Remota y Fecha Manual
    public Ticket crearTicket(@NonNull Ticket ticket) {
        log.info("Iniciando creación de ticket. Validando cliente RUT: {}", ticket.getRut());

        try {
            // Validación mediante Feign Client
            ClienteDTO cliente = clienteClient.buscarPorRut(ticket.getRut());
            log.info("Cliente verificado: {}. Procediendo con la apertura del ticket.", cliente.getNombre());

            // Seteo Manual de Reglas de Negocio
            ticket.setFechaCreacion(LocalDateTime.now());
            if (ticket.getEstado() == null) {
                ticket.setEstado("pendiente");
            }

            return ticketRepository.save(ticket);

        } catch (Exception e) {
            log.error("Fallo en la creación: El cliente con RUT {} no existe en el sistema.", ticket.getRut());
            // Manejo de excepciones
            throw new RuntimeException("Error: No se puede crear el ticket. El cliente no está registrado.");
        }
    }

    // 5. ACTUALIZAR TICKET (UPDATE)
    public Ticket actualizarTicket(Long id, Ticket ticketActualizado) {
        log.info("Actualizando datos del ticket ID: {}", id);

        return ticketRepository.findById(id).map(ticket -> {
            ticket.setDescripcion(ticketActualizado.getDescripcion());
            ticket.setEstado(ticketActualizado.getEstado());
            // No actualizamos la fecha de creación para mantener la integridad
            return ticketRepository.save(ticket);
        }).orElseThrow(() -> {
            log.error("No se pudo actualizar. Ticket ID {} no encontrado.", id);
            return new RuntimeException("Ticket no encontrado.");
        });
    }

    // 6. ELIMINAR TICKET (DELETE)
    public void eliminarTicket(Long id) {
        log.info("Solicitud de eliminación para el ticket ID: {}", id);
        if (!ticketRepository.existsById(id)) {
            log.error("Error al eliminar. Ticket ID {} no existe.", id);
            throw new RuntimeException("No existe el ticket que desea eliminar.");
        }
        ticketRepository.deleteById(id);
    }

    // 7. FINALIZAR TICKET (Lógica de Dominio Específica)
    public Ticket finalizarTicket(Long id) {
        log.info("Finalizando ticket ID: {}", id);

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el ticket con Id: " + id));

        ticket.setEstado("finalizado");
        ticket.setFechaCierre(LocalDateTime.now()); // Seteo manual de cierre

        return ticketRepository.save(ticket);
    }

}//fin
