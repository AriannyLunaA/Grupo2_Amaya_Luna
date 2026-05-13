package com.servicio.tecnico.mstickets.client;

import com.servicio.tecnico.mstickets.dto.ClienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//apunta al puerto 8082 de ms-cliente
@FeignClient(name= "ms-cliente",url = "http://localhost:8082/api/clientes")
public interface ClienteClient {
    @GetMapping("/buscar-rut")
    ClienteDTO buscarPorRut(@RequestParam("rut")  String rut);
}
