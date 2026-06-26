package com.fixnow.msnotificacion.Client;

import com.fixnow.msnotificacion.DTO.PersonaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-persona")
public interface PersonaClient {


    @GetMapping("/api/v1/personas/{id}")
    PersonaDTO getPersonaById(@PathVariable("id") Long id);
}
