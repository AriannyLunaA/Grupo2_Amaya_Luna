package com.fixnow.msnotificacion.Client;

import com.fixnow.msnotificacion.DTO.TicketDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "ms-ticket")
public interface TicketClient {

    @GetMapping("/api/v1/ticket/{id}")
    TicketDTO getTicketById(@PathVariable("id") Long id);
}
