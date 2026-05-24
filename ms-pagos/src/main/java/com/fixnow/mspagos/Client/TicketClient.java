package com.fixnow.mspagos.Client;

import com.fixnow.mspagos.DTO.TicketDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Referencia al ID registrado en Eureka Server
@FeignClient(name = "ms-tickets")
public interface TicketClient {

    // Endpoint expuesto por ms-tickets
    @GetMapping("/api/v1/tickets/{id}")
    TicketDTO getTicketById(@PathVariable("id") Long id);
}
