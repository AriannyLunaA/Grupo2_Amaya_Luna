package com.servicio.tecnico.mstickets.repository;

import com.servicio.tecnico.mstickets.model.Ticket;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    //métodos de busqueda

    //1-Búsqueda por estado (útil para filtrar y ver pendinetes)
    List<Ticket>findByEstado(String estado);

}//fin
