package com.fixnow.msnotificaciones.Client;

import com.fixnow.msnotificaciones.DTO.TicketDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Referencia al ID registrado en Eureka Server para el servicio de tickets
@FeignClient(name = "ms-tickets")
public interface TicketClient {

    // Endpoint expuesto por ms-tickets para consultar por ID
    @GetMapping("/api/v1/tickets/{id}")
    TicketDTO getTicketById(@PathVariable("id") Long id);
}
