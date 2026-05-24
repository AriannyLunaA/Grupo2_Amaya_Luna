package com.fixnow.ms_equipos.Client;

import com.fixnow.ms_equipos.DTO.PersonaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-persona")
public interface PersonaClient {

    @GetMapping("/api/v1/personas/{id}")
    PersonaDTO buscarPorId(@PathVariable("id") Long id);
}
