package com.fixnow.msnotificacion.Service;

import com.fixnow.msnotificacion.Client.PersonaClient;
import com.fixnow.msnotificacion.Client.TicketClient;
import com.fixnow.msnotificacion.DTO.PersonaDTO;
import com.fixnow.msnotificacion.DTO.TicketDTO;
import com.fixnow.msnotificacion.Model.Notificacion;
import com.fixnow.msnotificacion.Repository.NotificacionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
public class NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private TicketClient ticketClient;

    @Autowired
    private PersonaClient personaClient;

    public List<Notificacion> listarNotificaciones() {
        return notificacionRepository.findAll();
    }

    public Notificacion buscarPorId(Long id) {
        return notificacionRepository.findById(id).orElse(null);
    }

    public List<Notificacion> buscarPorTicket(Long idTicket) {
        return notificacionRepository.findByIdTicket(idTicket);
    }

    public boolean enviarNotificacion(Notificacion notificacion) {
        try {
            log.info("Iniciando proceso de notificación para el Ticket ID: {}", notificacion.getIdTicket());

            TicketDTO ticket = ticketClient.getTicketById(notificacion.getIdTicket());
            if (ticket == null || ticket.getIdPersona() == null) {
                log.warn("El ticket ID {} no existe o no tiene un cliente asociado.", notificacion.getIdTicket());
                return false;
            }

            PersonaDTO persona = personaClient.getPersonaById(ticket.getIdPersona());
            if (persona == null || persona.getCorreo() == null) {
                log.warn("No se encontró el correo para la Persona ID: {}", ticket.getIdPersona());
                return false;
            }

            notificacion.setCorreoDestino(persona.getCorreo());
            notificacion.setFechaEnvio(LocalDateTime.now());

            notificacionRepository.save(notificacion);
            log.info("Notificación guardada y enviada con éxito al correo: {}", persona.getCorreo());
            return true;

        } catch (Exception e) {
            log.error("Fallo de orquestación al conectar con ms-tickets o ms-persona: {}", e.getMessage());
            return false;
        }
    }
}
