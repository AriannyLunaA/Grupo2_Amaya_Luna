package com.fixnow.msticket.Client;

import com.fixnow.msticket.DTO.EquipoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-equipo")
public interface EquipoClient {

    @GetMapping("/api/v1/equipo/{id}")
    EquipoDTO obtenerPorId(@PathVariable("id") Long id);
}
