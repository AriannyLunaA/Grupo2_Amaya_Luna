package com.fixnow.msnotificaciones.Client;

import com.fixnow.msnotificaciones.DTO.PersonaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Referencia al ID registrado en Eureka Server para el servicio de personas/clientes
@FeignClient(name = "ms-persona")
public interface PersonaClient {

    // Endpoint expuesto por ms-persona para consultar sus datos
    @GetMapping("/api/v1/personas/{id}")
    PersonaDTO getPersonaById(@PathVariable("id") Long id);
}
