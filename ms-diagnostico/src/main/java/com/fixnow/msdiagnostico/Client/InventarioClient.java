package com.fixnow.msdiagnostico.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ms-inventario")
public interface InventarioClient {

    @PutMapping("/api/v1/inventario/{id}/descontar")
    String descontar(@PathVariable("id") Integer id, @RequestParam("cantidad") Integer cantidad);
}
