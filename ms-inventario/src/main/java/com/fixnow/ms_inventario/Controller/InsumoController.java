package com.fixnow.ms_inventario.Controller;

import com.fixnow.ms_inventario.Model.Insumo;
import com.fixnow.ms_inventario.Service.InsumoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventario/insumos")
public class InsumoController {
    private static final Logger log = LoggerFactory.getLogger(InsumoController.class);

    private final InsumoService insumoService;

    // Inyectamos el servicio por constructor
    public InsumoController(InsumoService insumoService) {
        this.insumoService = insumoService;
    }
//1.Listar
    @GetMapping
    public ResponseEntity<List<Insumo>> listar() {
        return new ResponseEntity<>(insumoService.listarTodos(), HttpStatus.OK);
    }
//2.Buacar
    @GetMapping("/{id}")
    public ResponseEntity<Insumo> buscar(@PathVariable Long id) {
        return new ResponseEntity<>(insumoService.buscarPorId(id), HttpStatus.OK);
    }
//3.Registrar
    @PostMapping
    public ResponseEntity<Insumo> registrar(@RequestBody Insumo insumo) {
        return new ResponseEntity<>(insumoService.registrarInsumo(insumo), HttpStatus.CREATED);
    }
//4.Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Insumo> actualizar(@PathVariable Long id, @RequestBody Insumo insumo) {
        return new ResponseEntity<>(insumoService.actualizarInsumo(id, insumo), HttpStatus.OK);
    }
//Eliminar
@DeleteMapping("/{id}")
public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
    // 1. Ejecutamos la lógica
    insumoService.eliminarInsumo(id);

    // 2. Registramos el evento en los logs (Esto es vital para auditoría)
    // Usamos {} como placeholder para inyectar el ID de forma limpia
    log.info("Insumo con ID {} eliminado exitosamente por el usuario", id);

    // 3. Devolvemos la respuesta amigable al Frontend
    Map<String, String> respuesta = new HashMap<>();
    respuesta.put("mensaje", "Insumo borrado con éxito");

    return new ResponseEntity<>(respuesta, HttpStatus.OK);
}
}//fin