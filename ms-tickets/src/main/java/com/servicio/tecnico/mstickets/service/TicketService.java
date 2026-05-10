package com.servicio.tecnico.mstickets.service;


import com.servicio.tecnico.mstickets.model.Ticket;
import com.servicio.tecnico.mstickets.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    //1- Obtener todos los tickets
    public List<Ticket> obtenerTodos() {
        return ticketRepository.findAll();
    }

    //2-Buscar por id(nro ticket) me da solo el ticket del ide ingresado
    public Optional<Ticket> obtenerPorId(Long id) {
        return ticketRepository.findById(id);
    }

    //3-Buscar por estado
    public List<Ticket> obtenerPorEstado(String estado) {
        return ticketRepository.findByEstado(estado);
    }

    //4-Crear nuevo tickiet
    public Ticket crearTicket(Ticket ticket) {
        //fecha de creacion automatica con @createdDate
        return ticketRepository.save(ticket);
    }
    //5- Finalizar ticket
    public Ticket finalizarTicket(Long id){
        Ticket ticket = ticketRepository.findById(id).orElseThrow(()->new RuntimeException("No se encontrado el ticket con Id: "+id));//no se encunetra el ticket
    ticket.setEstado("finalizado");
    //Se debe dar fecha manual de finalizado
        ticket.setFechaCierre(LocalDateTime.now());
        return ticketRepository.save(ticket);
    }




}//fin
