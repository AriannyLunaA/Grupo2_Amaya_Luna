package com.fixnow.msticket.Service;


import com.fixnow.msticket.Client.EquipoClient;
import com.fixnow.msticket.Client.PersonaClient;
import com.fixnow.msticket.DTO.EquipoDTO;
import com.fixnow.msticket.DTO.PersonaDTO;
import com.fixnow.msticket.DTO.TicketDTO;
import com.fixnow.msticket.Model.Ticket;
import com.fixnow.msticket.Repository.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TicketService {

    @Autowired
    private TicketRepository repository;

    @Autowired
    private PersonaClient personaClient;

    @Autowired
    private EquipoClient equipoClient;

    public List<TicketDTO> listarTodos() {
        log.info("obteniendo todos los tickets");
        List<Ticket> tickets = repository.findAll();
        List<TicketDTO> resultado = new ArrayList<>();

        for (Ticket ticket : tickets) {
            resultado.add(convertirAEntityDTO(ticket));
        }
        return resultado;
    }

    public TicketDTO buscarPorId(Long id) {
        log.info("buscando ticket con id: {}", id);
        Optional<Ticket> ticketOpt = repository.findById(id);

        if (ticketOpt.isPresent()) {
            return convertirAEntityDTO(ticketOpt.get());
        }
        return null;
    }

    public TicketDTO guardar(TicketDTO dto) {
        log.info("validando negocio para el nuevo ticket");


        try {
            PersonaDTO persona = personaClient.buscarPorId(dto.getIdPersona());
            if (persona == null) {
                log.warn("la persona id {} no existe.", dto.getIdPersona());
                return null;
            }
        } catch (Exception e) {
            log.error("falla al contactar ms-persona: {}", e.getMessage());
            return null;
        }


        try {
            EquipoDTO equipo = equipoClient.obtenerPorId(dto.getIdEquipo());
            if (equipo == null) {
                log.warn("el equipo id {} no existe.", dto.getIdEquipo());
                return null;
            }


            if (!equipo.getIdPersona().equals(dto.getIdPersona())) {
                log.warn("el equipo no corresponde al cliente indicado.");
                return null;
            }
        } catch (Exception e) {
            log.error("falla al contactar ms-equipo: {}", e.getMessage());
            return null;
        }



        log.info("ambos servicios externos confirmados. Creando ticket");
        Ticket ticket = new Ticket();
        ticket.setIdPersona(dto.getIdPersona());
        ticket.setIdEquipo(dto.getIdEquipo());
        ticket.setFechaIngreso(dto.getFechaIngreso());
        ticket.setEstado(dto.getEstado());
        ticket.setDescripcionFalla(dto.getDescripcionFalla());

        Ticket guardado = repository.save(ticket);
        return convertirAEntityDTO(guardado);
    }

    private TicketDTO convertirAEntityDTO(Ticket ticket) {
        TicketDTO dto = new TicketDTO();
        dto.setIdTicket(ticket.getIdTicket());
        dto.setIdPersona(ticket.getIdPersona());
        dto.setIdEquipo(ticket.getIdEquipo());
        dto.setFechaIngreso(ticket.getFechaIngreso());
        dto.setEstado(ticket.getEstado());
        dto.setDescripcionFalla(ticket.getDescripcionFalla());
        return dto;
    }
}
