package com.fixnow.msnotificaciones.Service;

import com.fixnow.msnotificaciones.Client.PersonaClient;
import com.fixnow.msnotificaciones.Client.TicketClient;
import com.fixnow.msnotificaciones.DTO.PersonaDTO;
import com.fixnow.msnotificaciones.DTO.TicketDTO;
import com.fixnow.msnotificaciones.Model.Notificacion;
import com.fixnow.msnotificaciones.Repository.NotificacionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio encargado de orquestar la creación de notificaciones.
 * Actúa como consumidor de información de ms-tickets y ms-persona para
 * enriquecer los datos de contacto antes de persistir el registro.
 */
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

    public Notificacion buscarPorId(Integer id) {
        return notificacionRepository.findById(id).orElse(null);
    }

    public List<Notificacion> buscarPorTicket(Integer idTicket) {
        return notificacionRepository.findByIdTicket(idTicket);
    }

    /**
     * Procesa y registra una nueva notificación.
     * Recupera asíncronamente el correo del cliente basándose en el ID del ticket.
     */
    public boolean enviarNotificacion(Notificacion notificacion) {
        try {
            log.info("Iniciando proceso de notificación para el Ticket ID: {}", notificacion.getIdTicket());

            // 1. Buscamos el ticket para saber a quién le pertenece el equipo
            TicketDTO ticket = ticketClient.getTicketById(Long.valueOf(notificacion.getIdTicket()));
            if (ticket == null || ticket.getIdPersona() == null) {
                log.warn("El ticket ID {} no existe o no tiene un cliente asociado.", notificacion.getIdTicket());
                return false;
            }

            // 2. Buscamos a la persona para obtener su correo electrónico
            PersonaDTO persona = personaClient.getPersonaById(ticket.getIdPersona());
            if (persona == null || persona.getCorreo() == null) {
                log.warn("No se encontró el correo para la Persona ID: {}", ticket.getIdPersona());
                return false;
            }

            // 3. Enriquecemos nuestro objeto Notificacion con los datos remotos y la fecha
            notificacion.setCorreoDestino(persona.getCorreo());
            notificacion.setFechaEnvio(LocalDateTime.now());

            // 4. Guardamos el registro
            notificacionRepository.save(notificacion);
            log.info("Notificación guardada y enviada con éxito al correo: {}", persona.getCorreo());
            return true;

        } catch (Exception e) {
            log.error("Fallo de orquestación al conectar con ms-tickets o ms-persona: {}", e.getMessage());
            return false;
        }
    }
}
