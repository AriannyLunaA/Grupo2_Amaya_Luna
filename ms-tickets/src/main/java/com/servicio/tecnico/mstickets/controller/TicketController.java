package com.servicio.tecnico.mstickets.controller;

import com.servicio.tecnico.mstickets.model.Ticket;
import com.servicio.tecnico.mstickets.repository.TicketRepository;
import com.servicio.tecnico.mstickets.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets") //ruta de los en endpoints
public class TicketController {
    @Autowired
    private TicketService ticketService;
    //1- Obtener todos los tickets (URL: GET http: 8081/api/tickets
    @GetMapping

    public List<Ticket> listarTodos(){
        return ticketService.obtenerTodos();
    }

    //2- Obtener por id(nro ticket) r(URL: GET http://localhost:8081/api/tickets/1)
    @GetMapping("/{id}")
    public ResponseEntity<Ticket> obtenerPorId(@PathVariable Long id){
        return ticketService.obtenerPorId(id)
                .map(ResponseEntity :: ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //3 Buccar por estado r(URL: GET http://localhost:8081/api/tickets/estado/PENDIENTE)
    @GetMapping("/estado/{estado}")
    public List<Ticket>buscarPorEstado(@PathVariable String estado){
        return ticketService.obtenerPorEstado(estado);
    }

    //4-Crear nuevo ticket r: URL: POST http://localhost:8081/api/tickets
    @PostMapping
    public ResponseEntity<Ticket> crear(@Valid @RequestBody Ticket ticket){
        Ticket nuevoTicket = ticketService.crearTicket(ticket);
        return new ResponseEntity<>(nuevoTicket, HttpStatus.CREATED);
    }

    //5 finalizar /cerrar ticket  r: URL: PUT http://localhost:8081/api/tickets/1/finalizar
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<Ticket> finalizar(@PathVariable Long id){
        try {
            Ticket ticketFinalizado = ticketService.finalizarTicket(id);
            return ResponseEntity.ok(ticketFinalizado);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();


        }
    }







}//fin
